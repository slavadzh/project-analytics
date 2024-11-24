package analytics.service;

import analytics.entity.GroupEntity;
import analytics.entity.StudentEntity;
import analytics.mapper.ChapterMapper;
import analytics.mapper.StudentMapper;
import analytics.models.Student;
import analytics.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Optional<GroupEntity> findByName(String name) {
        return groupRepository.findByName(name);
    }

    public GroupEntity save(GroupEntity group) {
        return groupRepository.save(group);
    }

    public List<GroupEntity> findAll() {
        return groupRepository.findAll();
    }

    @Transactional
    public void saveStudentsWithGroups(List<Student> students) {
        for (Student student : students) {
            GroupEntity group = findOrCreateGroup(student.getGroup());

            StudentEntity studentEntity = StudentMapper.toEntity(student);

            group.getStudents().add(studentEntity);
            studentEntity.setGroup(group);
            this.save(group);
        }
    }

    public GroupEntity findOrCreateGroup(String groupName) {
        return this.findByName(groupName)
                .orElseGet(() -> {
                    GroupEntity group = new GroupEntity();
                    group.setName(groupName);
                    return this.save(group);
                });
    }
}
