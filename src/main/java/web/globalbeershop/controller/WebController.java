package web.globalbeershop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import web.globalbeershop.data.Order;
import web.globalbeershop.data.User;
import web.globalbeershop.service.UserService;

import javax.validation.Valid;

@Controller
public class WebController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginError", false);
        return "login";
    }

    @GetMapping("/newuser")
    public String newUser(Model model) {
        User user = new User();
        user.setRole("USER");
        model.addAttribute("user", user);
        return "newuser";
    }

    @PostMapping("/newuser")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();


        if (bindingResult.hasErrors()) {
            System.out.printf("\n\n"+bindingResult.toString()+"\n\n");
            modelAndView.setViewName("/newuser");
        } else {
            // Successful checkout
            userService.saveUser(user);

            modelAndView.addObject("successMessage", "User checkout successful");
            user = new User();
            user.setRole("USER");
            modelAndView.addObject("user", user);
            modelAndView.setViewName("/login");
        }
        return modelAndView;
    }
}