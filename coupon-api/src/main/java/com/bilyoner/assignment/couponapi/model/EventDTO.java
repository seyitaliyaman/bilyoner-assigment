package com.bilyoner.assignment.couponapi.model;

import com.bilyoner.assignment.couponapi.persistence.entity.EventEntity;
import com.bilyoner.assignment.couponapi.model.enums.EventTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO implements Serializable {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Size(min = 1, message = "MBS must be at least 1.")
    private Integer mbs;

    @NotNull
    private EventTypeEnum type;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime eventDate;

    public static EventDTO mapToEventDTO(EventEntity eventEntity) {
        return EventDTO.builder()
                .id(eventEntity.getId())
                .name(eventEntity.getName())
                .mbs(eventEntity.getMbs())
                .type(eventEntity.getType())
                .eventDate(eventEntity.getEventDate())
                .build();
    }
}
