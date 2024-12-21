package webJava.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webJava.dto.UserDTO;
import webJava.entity.UserEntity;
import webJava.repository.UserRepo;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO joinUser(UserDTO userDTO) {

        Boolean isDuplicatedEmail = userRepo.existsByEmail(userDTO.getEmail());

        if (isDuplicatedEmail) {

            return null;
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserEntity joinUser = new UserEntity(userDTO);
        userRepo.save(joinUser);

        return userDTO;
    }

}
