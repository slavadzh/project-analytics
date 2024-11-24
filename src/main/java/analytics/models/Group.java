package analytics.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group{
    private String name;
    private List<Student> students;

}
