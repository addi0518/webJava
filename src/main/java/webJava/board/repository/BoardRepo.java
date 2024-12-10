package webJava.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webJava.board.entity.BoardEntity;

@Repository
public interface BoardRepo extends JpaRepository<BoardEntity, Long>{
    
}
