package analytics.mapper;

import analytics.entity.GroupEntity;
import analytics.models.Chapter;
import analytics.models.Group;
import analytics.models.Student;
import analytics.models.Task;
import jdk.jfr.Category;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.PieDataset;

import java.util.HashMap;
import java.util.List;

public class ChartDataMapper {

    public static CategoryDataset createScoresByGroups(List<Group> groups) {

        HashMap<String, Double> scores = new HashMap<>();
        for (Group group : groups) {
            double score = 0;
            for(Student student : group.getStudents()) {
                for(Chapter chapter : student.getScoreForChapters())
                    for (Task task : chapter.getTasks()) {
                        score += task.getScore();
                    }
            }
            scores.put(group.getName(), score);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        scores.forEach((name, score) -> dataset.addValue(score, "Score", name));
        return dataset;
    }

    public static CategoryDataset createScoresChapterByGroups(List<Group> groups, String chapterNumber) {

        HashMap<String, Double> scoresChapter = new HashMap<>();
        Chapter currentChapter = null;
        for (Group group : groups) {
            double score = 0;
            for(Student student : group.getStudents()) {
                for(Chapter chapter : student.getScoreForChapters())
                    if(chapter.getName().startsWith(chapterNumber)) {
                        for (Task task : chapter.getTasks()) {
                            score += task.getScore();
                        }
                        if(currentChapter == null) {
                            currentChapter = chapter;
                        }
                    }
            }
            if(currentChapter == null) {
                throw new RuntimeException("No chapter found for chapter number " + chapterNumber);
            }
            scoresChapter.put(group.getName(), score);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Chapter finalCurrentChapter = currentChapter;
        scoresChapter.forEach((name, score) -> dataset.addValue(score, "Score by " + finalCurrentChapter.getName(), name));
        return dataset;
    }
}
