import models.Student;
import util.Parser;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        var values = Parser.readCSVFile("java-rtf.csv");

        try {
            OutputStream out = System.out;
            PrintStream ps = new PrintStream(out, true, StandardCharsets.UTF_8);
            System.setOut(ps);
            List<Student> students = Parser.parseStudents(values);
            for (var student : students) {
                System.out.println(student);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
