package analytics.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String name;
    private Integer score;
    private TaskType type;
}
