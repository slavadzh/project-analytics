package analytics.mapper;

import analytics.entity.GroupEntity;
import analytics.entity.StudentEntity;
import analytics.models.Student;

public class StudentMapper {

    public static StudentEntity toEntity(Student student) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(student.getId());
        studentEntity.setName(student.getName());
        studentEntity.setFinalScore(student.getFinalScore());
        studentEntity.setScoreForChapters(
                student.getScoreForChapters().stream()
                        .map(ChapterMapper::toEntity)
                        .toList()
        );

        return studentEntity;
    }
}

