package com.truongiang.ecommerceweb.controller;

import com.truongiang.ecommerceweb.common.configuration.jwt.JwtUtils;
import com.truongiang.ecommerceweb.common.configuration.mail.SendMailService;
import com.truongiang.ecommerceweb.common.configuration.security.UserDetailsImpl;
import com.truongiang.ecommerceweb.dto.JwtResponse;
import com.truongiang.ecommerceweb.dto.LoginRequest;
import com.truongiang.ecommerceweb.dto.MessageResponse;
import com.truongiang.ecommerceweb.dto.SignupRequest;
import com.truongiang.ecommerceweb.model.AppRole;
import com.truongiang.ecommerceweb.model.Cart;
import com.truongiang.ecommerceweb.model.User;
import com.truongiang.ecommerceweb.repository.UserRepository;
import com.truongiang.ecommerceweb.service.CartService;
import com.truongiang.ecommerceweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("api/auth")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;


	@Autowired
	private CartService cartService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private SendMailService sendMailService;

	@GetMapping
	public ResponseEntity<List<User>> getAll() {

		return ResponseEntity.ok(this.userService.findUsersByStatusTrue());

	}

	@GetMapping("{id}")
	public ResponseEntity<User> getOne(@PathVariable("id") Long id) {

		if (!this.userService.checkExistedUserById(id)) {

			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(this.userService.findUserById(id).get());

	}

	@GetMapping("email/{email}")
	public ResponseEntity<User> getOneByEmail(@PathVariable("email") String email) {

		if (this.userService.checkExistedUserByEmail(email)) {

			return ResponseEntity.ok(this.userService.getUserByEmail(email).get());
		}

		return ResponseEntity.notFound().build();

	}

	@PostMapping
	public ResponseEntity<User> post(@RequestBody User user) {

		if (this.userService.checkExistedUserByEmail(user.getEmail())) {

			return ResponseEntity.notFound().build();
		}

		if (this.userService.checkExistedUserById(user.getUserId())) {

			return ResponseEntity.badRequest().build();
		}

		Set<AppRole> roles = new HashSet<>();
		roles.add(new AppRole(1, null));
		user.setRoles(roles);

		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		user.setToken(this.jwtUtils.doGenerateToken(user.getEmail()));
		User existedUser = this.userRepository.save(user);
		Cart cart = new Cart(0L, 0.0, existedUser.getAddress(), existedUser.getPhone(), existedUser);
		this.cartService.saveCart(cart);

		return ResponseEntity.ok(existedUser);

	}

	@PutMapping("{id}")
	public ResponseEntity<User> put(@PathVariable("id") Long id, @RequestBody User user) {

		if (!this.userService.checkExistedUserById(id)) {

			return ResponseEntity.notFound().build();
		}

		if (!id.equals(user.getUserId())) {

			return ResponseEntity.badRequest().build();
		}

		User tempUser = this.userService.findUserById(id).get();

		if (!user.getPassword().equals(tempUser.getPassword())) {

			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		}

		Set<AppRole> roles = new HashSet<>();
		roles.add(new AppRole(1, null));
		user.setRoles(roles);

		return ResponseEntity.ok(this.userService.save(user));

	}

	@PutMapping("admin/{id}")
	public ResponseEntity<User> putAdmin(@PathVariable("id") Long id, @RequestBody User user) {

		if (!this.userService.checkExistedUserById(id)) {

			return ResponseEntity.notFound().build();
		}

		if (!id.equals(user.getUserId())) {

			return ResponseEntity.badRequest().build();
		}

		Set<AppRole> roles = new HashSet<>();
		roles.add(new AppRole(2, null));
		user.setRoles(roles);

		return ResponseEntity.ok(this.userService.save(user));

	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		if (!this.userService.checkExistedUserById(id)) {

			return ResponseEntity.notFound().build();
		}

		User existedUser = this.userService.findUserById(id).get();
		existedUser.setStatus(false);
		this.userService.save(existedUser);

		return ResponseEntity.ok().build();

	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = this.jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getName(),
				userDetails.getEmail(), userDetails.getPassword(), userDetails.getPhone(), userDetails.getAddress(),
				userDetails.getGender(), userDetails.getStatus(), userDetails.getImage(), userDetails.getRegisterDate(),
				roles));

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest) {

		if (this.userService.checkExistedUserByEmail(signupRequest.getEmail())) {

			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
		}

		if (this.userService.checkExistedUserByEmail(signupRequest.getEmail())) {

			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is alreadv in use!"));
		}

		User user = new User(signupRequest.getName(), signupRequest.getEmail(),
				this.passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getPhone(),
				signupRequest.getAddress(), signupRequest.getGender(), signupRequest.getStatus(),
				signupRequest.getImage(), signupRequest.getRegisterDate(),
				this.jwtUtils.doGenerateToken(signupRequest.getEmail()));

		Set<AppRole> roles = new HashSet<>();
		roles.add(new AppRole(1, null));
		user.setRoles(roles);
		this.userService.save(user);

		Cart cart = new Cart(0L, 0.0, user.getAddress(), user.getPhone(), user);
		this.cartService.saveCart(cart);

		return ResponseEntity.ok(new MessageResponse("Đăng kí thành công"));

	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout() {

		return ResponseEntity.ok().build();

	}

	@PostMapping("send-mail-forgot-password-token")
	public ResponseEntity<String> sendToken(@RequestBody String email) {

		if (!this.userService.checkExistedUserByEmail(email)) {

			return ResponseEntity.notFound().build();
		}

		User user = this.userService.getUserByEmail(email).get();
		String token = user.getToken();
		sendMaiToken(email, token, "Reset mật khẩu");

		return ResponseEntity.ok().build();

	}

	public void sendMaiToken(String email, String token, String title) {

		String body = "\r\n" + "    <h2>Hãy nhấp vào link để thay đổi mật khẩu của bạn</h2>\r\n"
				+ "    <a href=\"http://localhost:8080/forgot-password/" + token + "\">Đổi mật khẩu</a>";
		this.sendMailService.queue(email, title, body);

	}

}
