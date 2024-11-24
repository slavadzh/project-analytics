//package entity;
//
//import jakarta.persistence.*;
//import jdk.jfr.Name;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "groups")
//public class GroupEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name; // Название группы
//
//    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<StudentEntity> students;
//}
