package com.example.security.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 회원가입 Controller
 */
@Controller
@RequestMapping("/signup")
public class SignUpController {

    private final UserService userService;

    public SignUpController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String singUp() {
        return "signup";
    }

    @PostMapping
    public String singUp(@ModelAttribute final UserRegisterDto userDto) {
        userService.singUp(userDto.username(), userDto.password());
        return "redirect:login"; // 회원 가입이 완료되면 로그인 페이지로 리다이렉트
    }

}
