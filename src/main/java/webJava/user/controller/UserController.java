package webJava.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import webJava.user.dto.UserDTO;
import webJava.user.service.UserService;

@Controller
@ResponseBody
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/join")
    public String singUp() {

        return "join";
    }

    @PostMapping("/join")
    public String userJoin(UserDTO userDTO) {
        userService.joinUser(userDTO);

        return "login";
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

}
