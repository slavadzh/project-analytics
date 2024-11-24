package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class StudentEntity {
    @Id
    private String id;

    private String name;

//    @ManyToOne
//    @JoinColumn(name = "group_id")
//    private GroupEntity group;

    private Integer finalScore;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id") // Связывает Chapters с конкретным студентом
    private List<ChapterEntity> scoreForChapters;
    // Геттеры, сеттеры, конструкторы
}
