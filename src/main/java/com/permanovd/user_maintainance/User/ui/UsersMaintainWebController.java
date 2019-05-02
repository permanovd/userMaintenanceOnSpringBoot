package com.permanovd.user_maintainance.User.ui;

import com.permanovd.user_maintainance.User.application.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "users")
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
    public String createNew() {
        return "user_maintanance/create_new";
    }

    @PostMapping("/create")
    public String createNew(UserCreateDTO userCreateDTO) {


        return "redirect:users";
    }

}
