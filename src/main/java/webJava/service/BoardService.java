package webJava.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import webJava.dto.BoardFormDTO;
import webJava.entity.BoardEntity;
import webJava.repository.BoardRepo;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepo boardRepo;

    @Transactional
    public void saveBoard(BoardFormDTO boardForm, Model model){
        //UserDTO dtoTmp = new UserDTO();
        //dtoTmp.setEmail("email@email.com");
        //dtoTmp.setName("임시 이름");
        model.addAttribute("email", "email@email.com");
        model.addAttribute("name", "임시 이름");
        BoardEntity boardEntity = new BoardEntity(boardForm, model);
        boardRepo.save(boardEntity);
        
    }

    @Transactional
    public void updateBoard(BoardFormDTO boardForm, Model model){
        model.addAttribute("email", "email@email.com");
        model.addAttribute("name", "수정된 이름");

        BoardEntity boardEntity = boardRepo.findById(boardForm.getSeqBoardNum()).orElseThrow();;

        boardEntity.setUpdateBoard(boardForm, model);
    }

    @Transactional(readOnly = true)
    public BoardEntity getBoard(Long seqBoardNum){
        BoardEntity board;
        if(seqBoardNum != null) {
            board = boardRepo.findById(seqBoardNum).orElseThrow();
        }else{
            board = null;
        }

        return board;
    }

    public Page<BoardEntity> getBoardList(Pageable pageable){
        Page<BoardEntity> boardList = boardRepo.findAll(pageable);
        
        return boardList;
    }
}
