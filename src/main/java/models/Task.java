package models;

import lombok.Builder;

@Builder
public record Task(Integer score, TaskType type, Student student, Chapter chapter) {
}
