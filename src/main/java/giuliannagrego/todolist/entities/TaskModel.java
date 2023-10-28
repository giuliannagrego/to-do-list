package giuliannagrego.todolist.entities;
//Mais uma pasta movida//

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;
    @Column(length = 50)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;
    private UUID idUser;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception {
        if (title.length() > 50) {
            throw new Exception("O campo title deve conter no máximo 50 caracteres");
        }
        this.title = title;

    /*O lombook já dispõe de um setTitle no @Data.Carece teste pra ver como funcionaria a utilização desse cara
    * se voce ia precisar definir ele para sobrepor o base mas como nunca é usado, nem deveria estar aqui :D
    * no proprio @ voce poderia ja definir uma mensagem de erro caso passe dos 50, mas é conhecimento futuro */
    }
}
