package webJava.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import webJava.dto.TokenDTO;
import webJava.dto.UserDTO;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq_num")
    private Long seqNum;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @Column(name = "join_dt")
    private LocalDateTime joinDt;

    @Builder
    public UserEntity(UserDTO userDTO) {
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.name = userDTO.getName();
        this.role = "ROLE_USER";
        this.joinDt = LocalDateTime.now();
    }

    @Builder
    public UserEntity(TokenDTO tokenDTO) {
        this.email = tokenDTO.getUsername();
        this.password = tokenDTO.getPassword();
        this.role = tokenDTO.getRole();
    }

}
