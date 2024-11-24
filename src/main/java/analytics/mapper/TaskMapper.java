package analytics.mapper;

import analytics.entity.TaskEntity;
import analytics.models.Task;

public class TaskMapper {
    public static TaskEntity toEntity(Task task) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setName(task.getName());
        taskEntity.setScore(task.getScore());
        taskEntity.setType(task.getType());
        return taskEntity;
    }
}