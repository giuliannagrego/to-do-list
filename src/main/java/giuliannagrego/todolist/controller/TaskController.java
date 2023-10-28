package giuliannagrego.todolist.controller;
/*package giuliannagrego.todolist.task.controller;
 a ideia de organizar as pastas é válida, mas não faça isso de novo não, respeite o Spring hehe
 os pacotes podem ser bem definidos e deixar a controller que é sua porta de entrada de requisição em
 outra pasta vai fazer o projeto ficar bem bagunçado caso voce cedida aumenta-lo */

import giuliannagrego.todolist.entities.TaskModel;
import giuliannagrego.todolist.repository.ITaskRepository;
import giuliannagrego.todolist.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel,
                                     HttpServletRequest request) {

        //create o que? vou ter que ler o metodo pra saber
        var idUser = request.getAttribute("idUser"); // e se o idUser não existir? cheirinho de nullpointer logo abaixo
        taskModel.setIdUser((UUID) idUser);
        var currentDate = LocalDateTime.now();
        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início/término deve ser maior que a data atual");
        }
        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início deve ser menor que a data de término");

        }
        /* L35 à L41:Existe um conceito chamado dominio rico. Que é onde sua propria model possui funções que servem
        para lidar com os dados que ela vai armazenar. Não há necessidade de manter esse pedaço de código aqui
        ainda mais por ser a controller. Como esse endpoint não tem associação com uma service aplicar as regras
        dentro do dominio rico vai deixar seu codigo mais kawai desu* */

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/") //AAAAAAAAAAAAAAAAAA PELAMORDEDEUS PAREM DE USAR O MESMO PATCH PRA ENDPOINTS DIFERENTES,
    //Ainda mais por que um é a 'criate?????' o outro é o 'list?????'
    public List<TaskModel> list(HttpServletRequest request) {
        /*List oq ? */
        var idUser = request.getAttribute("idUser");
        /* Existe uma briga de: manda string no body ou manda no patch?,
        pelo visto voce ja escolheu o seu lado */
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);

        return tasks;
        /*  E o status de ok para saber que deu bom? satanas que vai mandar ?
        Se minha api comunica com a sua pra puxar informação
        o problema de validar se a requisição deu ok e não teve retorno ou
        deu algum erro vai ser meu? maldade :(
        */
    }

    @PutMapping("/{id}") //custava criar um /update/{id} pra nao confundir com os outros?
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
        /*update oq? isso e uma descoberta para os proximos 10min de leitura */
        var task = this.taskRepository.findById(id).orElse(null); // ma que nome generico esse tal de task
        if(task == null) { /* sems like a problem, a depender do banco isso aqui pode nao ser nulo, mas ser vazio, que são
            bem diferentes */
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    /*?????? voce nao achou e eu que mandei errado? isso é um caso do famoso 404 Not found, nao um bad request.
                    se minha api comunica com a sua e eu recebo um 400 na boca eu vou ficar uma semana me perguntando
                    onde foi que eu errei*/
                    .body("Tarefa não encontrada");
        }

        var idUser = request.getAttribute("idUser"); /* essa variavel podia ser preenchida logo no começo do método
        pra voce ja saber com quem voce tá lidando antes de fazer a pesquisa no banco */
        if(!task.getIdUser().equals(idUser)) { /*Ue so agora? Inimiga da performace, primeiro eu pesquiso no meu bd,
        pra depois eu saber se quem fez a requisição poderia ter pesquisado no meu bd? esse tipo de validação tem que
        ser feita logo o receber a requisição, poupa tempo do hardware, existem formas melhores de fazer isso, mas são
        descobertas para mais pra frente
        */
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) /* DENOVO? isso aqui é um  401 Unauthorized
            se voce tiver duvidas de qual retorno dar, pode dar um ctrl+click no HttpStatus. ler todos que tao la
            parametrizados e ver qual melhor se encaixa ai */
                    .body("Usuário não tem permissão para alterar essa tarefa");
        }

        Utils.copyNonNullProperties(taskModel, task);

        var taskUpdated = this.taskRepository.save(task);/* Inimiga da alocação de memoria, tem mesmo necessidade
        de retornar para o usuario a confirmação de salvamento do que ele acabou de mandar ao inves de so uma fase
        de : ta tudo bem meu consagrado, segue a vida */
        return ResponseEntity.ok().body(taskUpdated);
    }
}
