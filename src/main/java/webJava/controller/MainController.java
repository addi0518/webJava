package webJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import webJava.dto.BoardFormDTO;
import webJava.entity.BoardEntity;
import webJava.entity.UserEntity;
import webJava.service.BoardService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@ResponseBody
public class MainController {
    @Autowired 
    private BoardService boardService;

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> mainP(@AuthenticationPrincipal UserEntity userEntity) {

        Map<String, Object> response = new HashMap<>();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //세션 사용자 role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        response.put("username", username);
        response.put("role", role);
        response.put("UserEntity", userEntity);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/board/list")
    public ResponseEntity<Map<String, Object>> getBoardList(@PageableDefault(size = 10, sort = "createDt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardEntity> boardList = boardService.getBoardList(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("boardList", boardList.getContent());
        response.put("currentPage", boardList.getNumber());
        response.put("totalPages", boardList.getTotalPages());
        response.put("totalElements", boardList.getTotalElements());

        //return ResponseEntity.ok(response);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    /*
    @GetMapping("/boardWrite")
    public String getBoardWrite(Model model) {
        model.addAttribute("board", null);

        return "boardWrite";
    }

    @GetMapping("/board/write/{seqBoardNum}")
    public ResponseEntity<Map<String, Object>> getBoardWrite(@PathVariable Long seqBoardNum) {
        Map<String, Object> response = new HashMap<>();

        try {
            BoardEntity boardEntity = boardService.getBoard(seqBoardNum);
            if (boardEntity == null) {
                response.put("message", "Board not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            response.put("board", boardEntity);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "An error occurred");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
     */

    @GetMapping("/board/{seqBoardNum}")
    public ResponseEntity<Map<String, Object>> getBoard(@PathVariable Long seqBoardNum) {
        Map<String, Object> response = new HashMap<>();

        try {
            BoardEntity boardEntity = boardService.getBoard(seqBoardNum);
            if (boardEntity == null) {
                response.put("message", "Board not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            response.put("board", boardEntity);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "An error occurred");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PutMapping("/board/{seqBoardNum}")
    public ResponseEntity<Map<String, Object>> updateBoard(@PathVariable Long seqBoardNum, @RequestBody BoardFormDTO boardForm) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 서비스 호출
            boardService.updateBoard(seqBoardNum, boardForm);
            response.put("message", "Board updated successfully");
            response.put("board", boardForm);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (SecurityException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (Exception e) {
            response.put("error", "An unexpected error occurred");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PostMapping("/board")
    public ResponseEntity<Map<String, Object>> saveBoard(
            @RequestBody BoardFormDTO boardForm,
            @AuthenticationPrincipal UserEntity userEntity) {

        String email = userEntity.getEmail(); // 로그인 시 확인 요망

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //세션 사용자 role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();

        String role = auth.getAuthority();
        
        Map<String, Object> response = new HashMap<>();

        try {
            if (username == null || role == null) {
                response.put("message", "Unauthorized");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            // 사용자 정보와 게시글 데이터 저장
            boardService.saveBoard(boardForm, username);

            response.put("message", "Board saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {

            response.put("message", "An error occurred while saving the board");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }


    
}
