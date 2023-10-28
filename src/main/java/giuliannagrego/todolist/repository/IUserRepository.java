package giuliannagrego.todolist.repository;
/*blablabla morreu a /user*/
import giuliannagrego.todolist.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);

}
