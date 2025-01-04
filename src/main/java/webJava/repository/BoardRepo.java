package webJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webJava.entity.BoardEntity;
import webJava.entity.UserEntity;

@Repository
public interface BoardRepo extends JpaRepository<BoardEntity, Long>{
    UserEntity getUserEntityByEmail(String username);
}
