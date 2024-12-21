package webJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webJava.entity.BoardEntity;

@Repository
public interface BoardRepo extends JpaRepository<BoardEntity, Long>{
    
}
