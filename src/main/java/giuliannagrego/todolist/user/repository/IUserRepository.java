package giuliannagrego.todolist.user.repository;

import giuliannagrego.todolist.user.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);

}
