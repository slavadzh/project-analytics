package models;

import lombok.*;

@Builder
public record Student(String name, String id, String group, Integer scoreForFinalProject){
}