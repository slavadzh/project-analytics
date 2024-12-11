//package drawer;
//
//import analytics.mapper.ChartDataMapper;
//import analytics.models.Group;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.CategoryDataset;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.List;
//
//import static org.jfree.chart.ChartFactory.createBarChart;
//
//public class BarChartDrawer extends JFrame {
//    public BarChartDrawer(String title, List<Group> groups) {
//        super(title);
//        setContentPane(createScoresByGroupsPanel(groups));
//        setSize(800, 600); // Устанавливаем размер окна
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null); // Центруем окно
//    }
//
//    private static JPanel createScoresByGroupsPanel(List<Group> groups) {
//        JFreeChart chart = createBarChart(ChartDataMapper.createScoresByGroups(groups));
//
//       
//        return new ChartPanel(chart);
//    }
//
//    private static JFreeChart createBarChart(CategoryDataset dataset) {
//        return ChartFactory.createBarChart(
//                "Scores by Group",   // Заголовок графика
//                "Group",             // Ось X
//                "Score",             // Ось Y
//                dataset,             // Данные
//                PlotOrientation.VERTICAL,
//                true,                // Легенда
//                true,                // Уведомления
//                false                // URL
//        );
//    }
//}

package drawer;

import analytics.mapper.ChartDataMapper;
import analytics.models.Group;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BarChartDrawer {
    private String title;

    public BarChartDrawer(String title) {
        this.title = title;
    }

    public File createChartFile(List<Group> groups, String filePath) throws IOException {
        CategoryDataset dataset = ChartDataMapper.createScoresByGroups(groups);
        JFreeChart chart = ChartFactory.createBarChart(
                title,
                "Group",
                "Score",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        File chartFile = new File(filePath);
        ChartUtils.saveChartAsPNG(chartFile, chart, 1920, 1080);
        return chartFile;
    }

    public File createChartFile(List<Group> groups, String chapterNumber, String filePath) throws IOException {
        CategoryDataset dataset = ChartDataMapper.createScoresChapterByGroups(groups, chapterNumber);
        JFreeChart chart = ChartFactory.createBarChart(
                title,
                "Group",
                "Score",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        File chartFile = new File(filePath);
        ChartUtils.saveChartAsPNG(chartFile, chart, 1920, 1080);
        return chartFile;
    }
}

