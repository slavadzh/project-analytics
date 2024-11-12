package models;

import lombok.Builder;

@Builder
public record Task(String name ,Integer score, TaskType type) {
}
