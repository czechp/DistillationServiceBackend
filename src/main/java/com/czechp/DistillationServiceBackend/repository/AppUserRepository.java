package com.czechp.DistillationServiceBackend.repository;

import com.czechp.DistillationServiceBackend.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
