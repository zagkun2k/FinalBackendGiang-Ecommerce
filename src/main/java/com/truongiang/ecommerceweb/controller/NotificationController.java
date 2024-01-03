package com.truongiang.ecommerceweb.controller;

import com.truongiang.ecommerceweb.model.Notification;
import com.truongiang.ecommerceweb.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping
	public ResponseEntity<List<Notification>> getAll() {

		return ResponseEntity.ok(this.notificationService.findNotificationsByOrderByIdDesc());
	}

	@PostMapping
	public ResponseEntity<Notification> post(@RequestBody Notification notification) {

		if (this.notificationService.checkExistedNotificationById(notification.getId())) {

			return ResponseEntity.badRequest().build();
		}

		notification.setTime(new Date());
		notification.setStatus(false);

		return ResponseEntity.ok(this.notificationService.saveNotification(notification));

	}

	@GetMapping("/readed/{id}")
	public ResponseEntity<Notification> put(@PathVariable("id") Long id) {

		if (!this.notificationService.checkExistedNotificationById(id)) {

			return ResponseEntity.notFound().build();
		}

		Notification notify = this.notificationService.getNotificationById(id).get();
		notify.setStatus(true);

		return ResponseEntity.ok(this.notificationService.saveNotification(notify));

	}

	@GetMapping("/read-all")
	public ResponseEntity<Void> readAll() {

		this.notificationService.readAllNotification();
		return ResponseEntity.ok().build();

	}

}
