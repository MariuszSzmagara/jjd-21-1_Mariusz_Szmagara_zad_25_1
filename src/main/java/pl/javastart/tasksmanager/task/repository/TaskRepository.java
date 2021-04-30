package pl.javastart.tasksmanager.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.javastart.tasksmanager.task.model.Category;
import pl.javastart.tasksmanager.task.model.Task;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByCompletedOrderByDeadlineDate(Boolean completed);

    List<Task> findByOrderByDeadlineDate();

    List<Task> findByCategoryOrderByDeadlineDate(Category category);

    @Transactional
    @Modifying
    @Query("UPDATE Task task SET task.completed = :completed WHERE task.id = :id")
    void updateTaskCompletedById(@Param("completed") boolean completed, @Param("id") Long id);


}
