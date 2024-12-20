package webJava.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import webJava.board.dto.BoardFormDTO;
import webJava.board.entity.BoardEntity;
import webJava.board.service.BoardService;
import webJava.user.dto.UserDTO;

@Controller
public class MainController {
    @Autowired 
    private BoardService boardService;

    @GetMapping("/")
    public String getBoardList(@PageableDefault(size = 10, sort = "createDt", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<BoardEntity> boardList = boardService.getBoardList(pageable);
        model.addAttribute("boardList", boardList);
        model.addAttribute("currentPage", boardList.getNumber());
        model.addAttribute("totalPages", boardList.getTotalPages());

        return "boardList";
    }

    @GetMapping("/boardWrite")
    public String getBoardWrite(Model model) {
        model.addAttribute("board", null);

        return "boardWrite";
    }

    @GetMapping("/boardWrite/{seqBoardNum}")
    public String getBoardWrite(@PathVariable(required = false) Long seqBoardNum, Model model) {
        model.addAttribute("board", boardService.getBoard(seqBoardNum));

        return "boardWrite";
    }

    @GetMapping("/board/{seqBoardNum}")
    public String getBoard(@PathVariable Long seqBoardNum, Model model) {
        BoardEntity boardEntity = boardService.getBoard(seqBoardNum);
        model.addAttribute("board", boardEntity);

        return "boardDetail";
    }

    @PutMapping("/board/{seqBoardNum}")
    public String updateBoard(@RequestBody BoardFormDTO boardForm, Model model) {
        boardService.updateBoard(boardForm, model);

        return "redirect:/boardDetail";
    }

    @PostMapping("/board")
    public String saveBoard(@RequestBody BoardFormDTO boardForm, Model model) {
        boardService.saveBoard(boardForm, model);

        return "redirect:/boardList";
    }


    
}
