package com.truongiang.ecommerceweb.service;

import com.truongiang.ecommerceweb.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    List<Notification> findNotificationsByOrderByIdDesc();

    boolean checkExistedNotificationById(Long id);

    Notification saveNotification(Notification notification);

    Optional<Notification> getNotificationById(Long id);

    void readAllNotification();

}
