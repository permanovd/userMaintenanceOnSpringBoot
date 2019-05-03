package com.permanovd.user_maintainance.User.ui;

import com.permanovd.user_maintainance.User.application.UserService;
import com.permanovd.user_maintainance.User.domain.model.User;
import com.permanovd.user_maintainance.User.domain.model.UserWithSameNameExistsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class UsersMaintainWebController {

    private UserService userService;
    private UserOutputDTOAssembler dtoAssembler;

    public UsersMaintainWebController(UserService userService, UserOutputDTOAssembler dtoAssembler) {
        this.userService = userService;
        this.dtoAssembler = dtoAssembler;
    }

    @GetMapping
    public String list(Model model,
                       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                       @RequestParam(value = "size", required = false, defaultValue = "50") int size) {

        // todo solve this issue https://stackoverflow.com/a/49575492/4082772
        if (page > 0) {
            --page;
        }

        List<UserOutputDTO> userDTOList = dtoAssembler.assembleList(userService.getList(page, size));
        model.addAttribute("users", userDTOList);

        return "user_maintanance/list";
    }

    @GetMapping(path = "{id}/delete")
    public String deleteUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUser(id);
        if (null == user) {
            return "redirect:/";
        }

        model.addAttribute("userDto", dtoAssembler.assembleOne(user));
        return "user_maintanance/delete_form";
    }

    @PostMapping(path = "{id}/delete")
    public String deleteUserFormSubmit(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
        } catch (IllegalStateException ignored) {
        }

        return "redirect:/";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("userDto", new UserCreateDTO());
        model.addAttribute("pageTitle", "Create new user");

        return "user_maintanance/user_form";
    }

    @GetMapping("/{id}/edit")
    public String changeUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUser(id);
        UserCreateDTO userCreateDTO = dtoAssembler.assembleForChanges(user);
        // todo find better solution.
        userCreateDTO.setPassword("********");

        model.addAttribute("userDto", userCreateDTO);
        model.addAttribute("pageTitle", "Edit ".concat(user.login()).concat(" user"));

        return "user_maintanance/user_form";
    }

    @PostMapping("/{id}/edit")
    public String changeUserFormSubmit(@PathVariable("id") Long id, @ModelAttribute("userDto") @Valid UserUpdateDTO userDTO,
                                       BindingResult bindingResult, Model model) {
        boolean passwordIsEmpty = bindingResult.getRawFieldValue("password") == null
                || bindingResult.getRawFieldValue("password").equals("");

        if (!passwordIsEmpty && userDTO.getPassword().length() < 8) {
            bindingResult.rejectValue("password", "user.password", "Password has to be more than 8 characters long.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", userDTO);
            return "user_maintanance/user_form";
        }

        try {
            userService.changeUser(userDTO, id);
        } catch (UserWithSameNameExistsException ex) {
            bindingResult.rejectValue("login", "user.login", "This login is already taken. Please try another one");
            model.addAttribute("userDto", userDTO);

            return "user_maintanance/user_form";
        }

        return "redirect:/";
    }

    @PostMapping("/create")
    public String createUserFormSubmit(@ModelAttribute(name = "userDto") @Valid UserCreateDTO userCreateDTO,
                                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", userCreateDTO);
            return "user_maintanance/user_form";
        }

        try {
            userService.register(userCreateDTO);
        } catch (UserWithSameNameExistsException ex) {
            bindingResult.rejectValue("login", "user.login", "This login is already taken. Please try another one");
            model.addAttribute("userDto", userCreateDTO);

            return "user_maintanance/user_form";
        }

        return "redirect:/";
    }

}
