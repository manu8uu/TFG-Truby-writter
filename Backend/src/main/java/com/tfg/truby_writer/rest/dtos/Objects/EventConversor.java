package com.tfg.truby_writer.backend.rest.dtos.Objects;

import com.tfg.truby_writer.model.entities.Event;
import com.tfg.truby_writer.model.services.Block;
import java.util.List;
import java.util.stream.Collectors;

public final class EventConversor {

    private EventConversor() {}

    public static EventDto toEventDto(Event event) {
        if (event == null) return null;
        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle()) 
                .content(event.getContent()) 
                .chapterNumber(event.getChapterNumber())
                .chronoOrder(event.getChronoOrder())
                .timelineId(event.getLineTime() != null ? event.getLineTime().getId() : null)
                .build();
    }

    public static List<EventDto> toEventDtos(List<Event> events) {
        if (events == null) return List.of();
        return events.stream().map(EventConversor::toEventDto).collect(Collectors.toList());
    }

    public static Block<EventDto> toEventBlockDto(Block<Event> eventBlock) {
        List<EventDto> items = toEventDtos(eventBlock.getItems());
        return new Block<>(items, eventBlock.getExistMoreItems());
    }
}