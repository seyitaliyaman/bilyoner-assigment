package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.model.EventDTO;
import com.bilyoner.assignment.couponapi.persistence.entity.EventEntity;
import com.bilyoner.assignment.couponapi.persistence.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;


    @Test
    public void shouldGetAllEvents(){
        EventEntity eventEntity = EventEntity.builder().id(1L).build();

        when(eventRepository.findAll()).thenReturn(Collections.singletonList(eventEntity));

        List<EventDTO> result = eventService.getAllEvents();

        assertEquals(eventEntity.getId(),result.get(0).getId());
    }

}
