package webJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import webJava.dto.UserDTO;
import webJava.entity.BoardEntity;
import webJava.service.UserService;

import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> userJoin(UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();
        UserDTO resultUserDTO = userService.joinUser(userDTO);

        try {
            if (resultUserDTO == null) {
                response.put("message", "Duplicated Email");
                return ResponseEntity.status(HttpStatus.IM_USED).body(response);
            }
            response.put("user", resultUserDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "An error occurred");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

}
