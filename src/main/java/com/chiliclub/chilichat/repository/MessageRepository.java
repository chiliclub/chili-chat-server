package com.chiliclub.chilichat.repository;

import com.chiliclub.chilichat.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
}
