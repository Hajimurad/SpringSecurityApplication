package crud.controller;

import crud.dao.NotFoundException;
import crud.entity.Role;
import crud.entity.User;
import crud.service.RoleService;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, @Lazy RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/admin/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user";
    }



    @GetMapping("/admin/new")
    public String addUser(@ModelAttribute("user") User user, Model model) {

        Set<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);

        return "new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") Long id, Model model) {
        Set<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);

        if (bindingResult.hasErrors()) {
            return "new";
        }
        userService.updateUser(id, user);

        return "redirect:/admin";
    }


    @DeleteMapping("/admin/users/{id}")
    public String removeUser(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String userPage(Principal principal, Model model) {
        User user = userService.getByEmail(principal.getName());
        Role role =  roleService.getByRole("USER");
        model.addAttribute("user", user);
        model.addAttribute("roles", role);
        return "user";
    }

    @GetMapping("admin/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        Set<Role> roles = new HashSet<>(roleService.getAllRoles());
        model.addAttribute("roles", roles);
        model.addAttribute("user", userService.getById(id));
        return "edit";
    }


    @PatchMapping("{admin/{id}/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") Long id,
                         Model model) {
        Set<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("userOrig", userService.getById(id));

        if (bindingResult.hasErrors()) {
            return "edit";
        }

        userService.updateUser(id, user);
        return "redirect:/admin";
    }
}
