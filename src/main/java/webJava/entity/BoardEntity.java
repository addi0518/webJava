package webJava.entity;

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
import webJava.dto.BoardFormDTO;

import java.time.LocalDateTime;

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
    public BoardEntity(BoardFormDTO boardForm, UserEntity user) {
        this.title = boardForm.getTitle();
        this.content = boardForm.getContent();
        this.email = user.getEmail();
        this.name = user.getName();
        this.createDt = LocalDateTime.now();
    }

    public void setUpdateBoard(BoardFormDTO boardForm, String name){
        this.title = boardForm.getTitle();
        this.content = boardForm.getContent();
        this.name = name;
    }

}
