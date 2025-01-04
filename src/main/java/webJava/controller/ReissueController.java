package webJava.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import webJava.jwt.JWTUtil;
import webJava.service.ReissueService;

@Controller
@ResponseBody
public class ReissueController {

    private final ReissueService reissueService;

    public ReissueController(JWTUtil jwtUtil, ReissueService reissueService) {

        this.reissueService = reissueService;
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        return reissueService.getNewAccessWithRefresh(request, response);
    }

}
