package com.permanovd.user_maintainance.User.ui;

import com.permanovd.user_maintainance.User.application.UserService;
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

    @GetMapping("/create")
    public String createUserForm(Model model) {

        model.addAttribute("userDto", new UserCreateDTO());
        return "user_maintanance/create_new";
    }

    @PostMapping("/create")
    public String createUserFormSubmit(@ModelAttribute(name = "userDto") @Valid UserCreateDTO userCreateDTO,
                                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", userCreateDTO);
            return "user_maintanance/create_new";
        }

        try {
            userService.register(userCreateDTO);
        } catch (UserWithSameNameExistsException ex) {
            bindingResult.rejectValue("login", "user.login", "This login is already taken. Please try another one");
            model.addAttribute("userDto", userCreateDTO);
            return "user_maintanance/create_new";
        }

        return "redirect:/";
    }

}
