package giuliannagrego.todolist.user.controller;

import giuliannagrego.todolist.user.entities.UserModel;
import giuliannagrego.todolist.user.repository.IUserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @AUTO
    private IUserRepository userRepository;

    @PostMapping("/")
    public void createUser(@RequestBody UserModel userModel) {
        System.out.println(userModel.getName());
    }
}
