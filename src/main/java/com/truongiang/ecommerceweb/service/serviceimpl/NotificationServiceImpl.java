package com.truongiang.ecommerceweb.service.serviceimpl;

import com.truongiang.ecommerceweb.model.Notification;
import com.truongiang.ecommerceweb.repository.NotificationRepository;
import com.truongiang.ecommerceweb.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> findNotificationsByOrderByIdDesc() {

        return this.notificationRepository.findByOrderByIdDesc();

    }

    @Override
    public boolean checkExistedNotificationById(Long id) {

        return this.notificationRepository.existsById(id);

    }

    @Override
    public Notification saveNotification(Notification notification) {

        this.notificationRepository.save(notification);
        return notification;

    }

    @Override
    public Optional<Notification> getNotificationById(Long id) {

        return this.notificationRepository.findById(id);

    }

    @Override
    public void readAllNotification() {

        this.notificationRepository.readAll();

    }

}
