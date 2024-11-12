package util;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import models.Chapter;
import models.Student;
import models.Task;
import models.TaskType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parser {

    public static List<String[]> readCSVFile(String file){
        var parser = new CSVParserBuilder()
                .withSeparator(';')
                .build();
        try (var reader = new CSVReaderBuilder(
                new InputStreamReader(new FileInputStream(file), "windows-1251"))
                .withCSVParser(parser)
                .build()) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Student> parseStudents(List<String[]> lines) {
        List<Student> students = new ArrayList<>();

        for(int i = 3; i < lines.size(); i++) {
            String[] line = lines.get(i);
            String name = line[0];
            String id = line[1];
            String group = line[2];
            int finalScore = Integer.parseInt(line[3]) + Integer.parseInt(line[4]) + Integer.parseInt(line[5]);
            var scoreForChapters = parseChapters(lines, i);
            Student student = new Student(name, id, group, finalScore, scoreForChapters);
            students.add(student);
        }

        return students;
    }

    private static List<Chapter> parseChapters(List<String[]> lines, int indexStudent) {
        List<Chapter> chapters = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        String[] lineChapters = lines.getFirst();
        String[] lineTasks = lines.get(1);
        String nameChapter = lineChapters[6];

        for(int i = 6; i < lines.getFirst().length; i++) {
            if(!Objects.equals(lineChapters[i], "") && i != 6) {
                Chapter chapter = new Chapter(nameChapter, tasks);
                chapters.add(chapter);
                nameChapter = lineChapters[i];
                tasks = new ArrayList<>();
            }
            if(lineTasks[i].startsWith("КВ:")) {
                TaskType taskType = TaskType.CONTROL_QUESTION;
                int score = Integer.parseInt(lines.get(indexStudent)[i]);
                Task task = new Task(lineTasks[i], score, taskType);
                tasks.add(task);
            }
            if(lineTasks[i].startsWith("УПР:")) {
                TaskType taskType = TaskType.EXERCISE;
                int score = Integer.parseInt(lines.get(indexStudent)[i]);
                Task task = new Task(lineTasks[i], score, taskType);
                tasks.add(task);
            }
            if(lineTasks[i].startsWith("ДЗ:")) {
                TaskType taskType = TaskType.HOMEWORK;
                int score = Integer.parseInt(lines.get(indexStudent)[i]);
                Task task = new Task(lineTasks[i], score, taskType);
                tasks.add(task);
            }
        }
        return chapters;
    }
}
