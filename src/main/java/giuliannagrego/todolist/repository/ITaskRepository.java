package giuliannagrego.todolist.repository;
//TCHAAAU pasta task

import giuliannagrego.todolist.entities.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByIdUser(UUID idUser); // iti malia usando findBy
}
