package analytics.controller;

import analytics.entity.GroupEntity;
import analytics.entity.StudentEntity;
import analytics.models.Group;
import analytics.models.Student;
import analytics.service.GroupService;
import analytics.util.Parser;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    public final ModelMapper modelMapper;

    public GroupController(GroupService groupService, ModelMapper modelMapper) {
        this.groupService = groupService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Group> getStudents() {
        return groupService.findAll().stream().map(this::convertToGroup).toList();
    }

    public Group convertToGroup(GroupEntity groupEntity) {
        Group group = modelMapper.map(groupEntity, Group.class);
        group.getStudents().forEach(student -> student.setGroup(group.getName()));
        return group;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> saveStudents() {
        List<Student> students = Parser.readCSVFile("java-rtf.csv");
        groupService.saveStudentsWithGroups(students);
        return ResponseEntity.ok("Students saved successfully");
    }
}
