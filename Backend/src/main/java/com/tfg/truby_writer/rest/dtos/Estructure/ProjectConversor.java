package com.tfg.truby_writer.backend.rest.dtos.Estructure;

import com.tfg.truby_writer.model.entities.Project;

public final class ProjectConversor {

    private ProjectConversor() {}

    public static ProjectDto toProjectDto(Project project) {
        if (project == null) return null;
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .modifiedAt(project.getModifiedAt())
                .build();
    }
}