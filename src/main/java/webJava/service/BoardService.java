package webJava.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import webJava.dto.BoardFormDTO;
import webJava.entity.BoardEntity;
import webJava.entity.UserEntity;
import webJava.repository.BoardRepo;
import webJava.repository.UserRepo;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepo boardRepo;
    private final UserRepo userRepo;

    @Transactional
    public void saveBoard(BoardFormDTO boardForm, String email) {
        UserEntity userEntity = boardRepo.getUserEntityByEmail(email);
        BoardEntity boardEntity = new BoardEntity(boardForm, userEntity);

        boardRepo.save(boardEntity);
    }

    @Transactional
    public void updateBoard(Long seqBoardNum, BoardFormDTO boardForm){
        
        // 게시글 조회
        BoardEntity boardEntity = boardRepo.findById(seqBoardNum)
                .orElseThrow(() -> new IllegalArgumentException("Board not found with id: " + seqBoardNum));

        // 게시글 작성자 조회
        UserEntity userEntity = userRepo.findByEmail(boardEntity.getEmail());
        if (userEntity == null) {
            throw new IllegalArgumentException("User not found with email: " + boardEntity.getEmail());
        }

        // 이메일 검증
        if (!emailAndRole().get("email").equals(userEntity.getEmail())) {
            throw new SecurityException("You do not have permission to update this board.");
        }

        boardEntity.setUpdateBoard(boardForm, userEntity.getName());
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

    public Map<String, String> emailAndRole(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //세션 사용자 role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return Map.of("email", username, "role", role);
    }

}
