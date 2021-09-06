package crud.controller;

import crud.entity.Role;
import crud.entity.User;
import crud.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        Role defaultRole = new Role("USER");
        Set<Role> defaultRolesSet = new HashSet<>();
        defaultRolesSet.add(defaultRole);

        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.getByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            if (user.getRoles() == null){
                user.setRoles(defaultRolesSet);
            }
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", user);

            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }
}
