package analytics.mapper;

import analytics.entity.ChapterEntity;
import analytics.models.Chapter;

public class ChapterMapper {
    public static ChapterEntity toEntity(Chapter chapter) {
        ChapterEntity chapterEntity = new ChapterEntity();
        chapterEntity.setName(chapter.getName());
        chapterEntity.setTasks(
                chapter.getTasks().stream()
                        .map(TaskMapper::toEntity)
                        .toList()
        );
        return chapterEntity;
    }
}