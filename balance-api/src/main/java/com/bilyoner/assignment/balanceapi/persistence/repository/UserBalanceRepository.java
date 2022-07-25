package com.bilyoner.assignment.balanceapi.persistence.repository;

import com.bilyoner.assignment.balanceapi.persistence.entity.UserBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface UserBalanceRepository extends JpaRepository<UserBalanceEntity, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<UserBalanceEntity> findByUserId(Long userId);
}
