package webJava.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;
import webJava.board.dto.BoardFormDTO;
import webJava.user.dto.UserDTO;

import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
public class BoardEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq_board_num")
    private Long seqBoardNum;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "create_dt")
    private LocalDateTime createDt;

//    @Column(name = "update_dt")
//    private LocalDateTime updateDt;

    @Builder
    public BoardEntity(BoardFormDTO boardForm, Model model){
        this.title = boardForm.getTitle();
        this.content = boardForm.getContent();
        this.email = (String) model.getAttribute("email");
        this.name = String.valueOf(model.getAttribute("name"));
        this.createDt = LocalDateTime.now();
    }

    public void setUpdateBoard(BoardFormDTO boardForm, Model model){
        this.title = boardForm.getTitle();
        this.content = boardForm.getContent();
        this.name = String.valueOf(model.getAttribute("name"));
    }

}
