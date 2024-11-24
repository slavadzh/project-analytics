package analytics.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chapter{
    private String name;
    private List<Task> tasks;
}
