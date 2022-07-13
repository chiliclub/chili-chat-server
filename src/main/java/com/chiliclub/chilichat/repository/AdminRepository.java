package com.chiliclub.chilichat.repository;

import com.chiliclub.chilichat.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
}
