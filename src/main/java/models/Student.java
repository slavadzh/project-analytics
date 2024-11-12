package models;

import lombok.*;

import java.util.List;

@Builder
public record Student(String name, String id, String group, Integer finalScore, List<Chapter> scoreForChapters){

}