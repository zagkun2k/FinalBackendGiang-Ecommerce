package com.truongiang.ecommerceweb.controller;

import com.truongiang.ecommerceweb.common.configuration.mail.SendMailService;
import com.truongiang.ecommerceweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/send-mail")
public class SendMailController {

	@Autowired
	private SendMailService sendMail;

	@Autowired
	private UserService userService;

	@PostMapping("/otp")
	public ResponseEntity<Integer> sendOTP(@RequestBody String email) {

		int randomOTP = (int) Math.floor(Math.random() * (999999 - 100000 + 1) + 100000);
		if (this.userService.checkExistedUserByEmail(email)) {

			return ResponseEntity.notFound().build();
		}

		sendMailOTP(email, randomOTP, "Xác nhận tài khoản!");
		return ResponseEntity.ok(randomOTP);

	}

	public void sendMailOTP(String email, int randomOTP, String title) {

		String body = "<div>\r\n" + "        <h3>Mã OTP của bạn là: <span style=\"color:red; font-weight: bold;\">"
				+ randomOTP + "</span></h3>\r\n" + "    </div>";
		sendMail.queue(email, title, body);

	}

}