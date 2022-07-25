package com.bilyoner.assignment.couponapi.persistence.repository;

import com.bilyoner.assignment.couponapi.persistence.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Set<EventEntity> findAllByIdIn(List<Long> idList);
}
