package webJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import webJava.dto.BoardFormDTO;
import webJava.entity.BoardEntity;
import webJava.service.BoardService;

import java.util.Collection;
import java.util.Iterator;

@Controller
@ResponseBody
public class MainController {
    @Autowired 
    private BoardService boardService;

    @GetMapping("/")
    public String mainP() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //세션 사용자 role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return "main Controller" + username + " " + role;
    }

    @GetMapping("/boardList")
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
