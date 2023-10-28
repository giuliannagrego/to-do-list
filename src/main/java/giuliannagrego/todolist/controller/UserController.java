package giuliannagrego.todolist.controller;
/*package giuliannagrego.todolist.user.controller;
        A NÃO BICHO, ELA CRIOU UMA OUTRA PASTA AGORA PRA USER, THERE IS A FUCK
*/

import giuliannagrego.todolist.entities.UserModel;
import giuliannagrego.todolist.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        /*Preciso falar ?*/
        var user = this.userRepository.findByUsername(userModel.getUsername());/* tem formas mehores,
        mas funciona tb*/
        if(user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) // preciso falar?
                    .body("Usuário já existe");
        }

        var passwordHashred = BCrypt.withDefaults()
                .hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashred); // Boa preocupação

        var userCreated = this.userRepository.save(userModel); /* o cara acabou de mandar o user, nao precisa
        mandar de volta o user pra ele se voce nao vai alterar nada, de novo so manda a mensagem de criado e da-lhe*/
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }
/* cade o resto? criei o usuario e voce mantem pra sempre ? */
}
