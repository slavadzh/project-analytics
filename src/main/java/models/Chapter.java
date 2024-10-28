package models;

import lombok.Builder;

import java.util.List;

@Builder
public record Chapter(String name, List<Task> tasks){
}
