package com.tfg.truby_writer.backend.rest.dtos.Objects;

import com.tfg.truby_writer.model.entities.Character;
import com.tfg.truby_writer.model.services.Block;
import java.util.List;
import java.util.stream.Collectors;

public final class CharacterConversor {

    private CharacterConversor() {}

    public static CharacterDto toCharacterDto(Character character) {
        if (character == null) return null;
        return CharacterDto.builder()
                .id(character.getId())
                .name(character.getName())
                .bio(character.getBio())
                .mainImageUrl(character.getMainImageUrl())
                .projectId(character.getProject() != null ? character.getProject().getId() : null)
                .build();
    }

    public static List<CharacterDto> toCharacterDtos(List<Character> characters) {
    if (characters == null) return List.of();
    return characters.stream()
            .filter(c -> c != null) 
            .map(c -> toCharacterDto(c)) 
            .collect(Collectors.toList());
}

    public static Block<CharacterDto> toCharacterBlockDto(Block<Character> characterBlock) {
        List<CharacterDto> items = toCharacterDtos(characterBlock.getItems());
        return new Block<>(items, characterBlock.getExistMoreItems());
    }
}