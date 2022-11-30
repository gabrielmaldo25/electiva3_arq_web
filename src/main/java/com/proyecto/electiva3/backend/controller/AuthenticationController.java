package com.proyecto.electiva3.backend.controller;

import com.proyecto.electiva3.backend.captcha.CaptchaService;
import com.proyecto.electiva3.backend.captcha.CaptchaSettings;
import com.proyecto.electiva3.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class AuthenticationController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/login")
    public String login(Model model) {

        // realiza un doble request
        CaptchaSettings captcha = captchaService.genCaptcha();
        model.addAttribute("captcha", captcha);

        return "login";
    }
}
