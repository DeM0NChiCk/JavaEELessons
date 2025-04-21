package ru.itis.lessonservlet.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.lessonservlet.dto.request.SignInRequest;
import ru.itis.lessonservlet.dto.response.AuthResponse;
import ru.itis.lessonservlet.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminAuthController {
    private final UserService userService;
    private final String AUTHORIZATION;
    private final String IS_ADMIN;
    private final String SECRET_KEY;

    public AdminAuthController(
            UserService userService,
            @Qualifier("AUTHORIZATION") String authorization,
            @Qualifier("IS_ADMIN") String isAdmin,
            @Qualifier("SECRET_KEY") String secretKey
    ) {
        this.userService = userService;
        this.AUTHORIZATION = authorization;
        this.IS_ADMIN = isAdmin;
        this.SECRET_KEY = secretKey;
    }

    @GetMapping("/signIn")
    public String getAdminSignInPage() {
        return "adminSignIn"; // jsp/adminSignIn.jsp
    }

    @PostMapping("/signIn")
    public String signInAdmin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              @RequestParam("codeword") String codeword,
                              HttpServletRequest request) {

        if (!codeword.equals(SECRET_KEY)) {
            return "redirect:/error?err=Invalid+codeword";
        }

        SignInRequest signInRequest = SignInRequest.builder()
                .email(email)
                .password(password)
                .build();

        AuthResponse authResponse = userService.signIn(signInRequest);
        authResponse = userService.checkAdmin(authResponse.getUser());

        if (authResponse.getStatus() == 0) {
            HttpSession session = request.getSession(true);
            session.setAttribute(AUTHORIZATION, true);
            session.setAttribute(IS_ADMIN, true);
            session.setAttribute("user", authResponse.getUser());

            return "redirect:/admin/main";
        } else {
            return "redirect:/error?err=" + authResponse.getStatusDesc();
        }
    }
}
