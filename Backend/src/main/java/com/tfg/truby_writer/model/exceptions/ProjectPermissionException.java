package com.tfg.truby_writer.model.exceptions;

public class ProjectPermissionException extends Exception {
    private final Long userId;
    private final Long projectId;

    public ProjectPermissionException(Long userId, Long projectId) {
        super("User with ID " + userId + " does not have ownership or access privileges to Project ID " + projectId);
        this.userId = userId;
        this.projectId = projectId;
    }

    public Long getUserId() { return userId; }
    public Long getProjectId() { return projectId; }
}