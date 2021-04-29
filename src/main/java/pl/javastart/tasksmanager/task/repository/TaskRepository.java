package pl.javastart.tasksmanager.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.javastart.tasksmanager.task.model.Category;
import pl.javastart.tasksmanager.task.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByCompletedOrderByDeadlineDate(Boolean completed);

    List<Task> findByOrderByDeadlineDate();

    List<Task> findByCategoryOrderByDeadlineDate(Category category);
}
