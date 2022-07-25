package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.model.EventDTO;
import com.bilyoner.assignment.couponapi.persistence.entity.EventEntity;
import com.bilyoner.assignment.couponapi.persistence.repository.EventRepository;
import com.bilyoner.assignment.couponapi.validation.EventValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;
    private final EventValidator eventValidator;

    @Cacheable(value = "events",unless="#result != null")
    public List<EventDTO> getAllEvents() {
        log.info("get all evets without cache");
        return repository.findAll().stream().map(EventDTO::mapToEventDTO).collect(Collectors.toList());
    }

    @CacheEvict(value = "events",allEntries = true)
    public EventDTO createEvent(EventDTO eventRequest) {
        final var eventEntity = EventEntity.builder()
                .name(eventRequest.getName())
                .mbs(eventRequest.getMbs())
                .type(eventRequest.getType())
                .eventDate(eventRequest.getEventDate())
                .build();

        return EventDTO.mapToEventDTO(repository.save(eventEntity));
    }

    public void checkEventIds(List<Long> eventIdList){

        var events = repository.findAllByIdIn(eventIdList);
        eventValidator.validate(events);
    }
}
