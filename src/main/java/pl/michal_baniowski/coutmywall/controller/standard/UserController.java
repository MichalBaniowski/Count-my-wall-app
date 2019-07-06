package pl.michal_baniowski.coutmywall.controller.standard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.michal_baniowski.coutmywall.entity.User;
import pl.michal_baniowski.coutmywall.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register_form";
    }
    @PostMapping("/register")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "register_form";
        } else {
            userService.addWithDefaultRole(user);
            model.addAttribute("prompt", "rejestracja udana");
            return "action_result";
        }
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("dupa");
        return "redirect:/user/login";
    }

    @RequestMapping("/user/login")
    public String loginPage(Model model) {
        model.addAttribute("prompt", "jesteś zalogowany");
        return "action_result";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "login_form";
    }
}
