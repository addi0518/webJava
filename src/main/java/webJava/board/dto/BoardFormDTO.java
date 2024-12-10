package webJava.board.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardFormDTO {
    private Long seqBoardNum;
    private String title;
    private String content;

}
