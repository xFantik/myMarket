package ru.pb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pb.market.data.User;
import ru.pb.market.dto.UserDto;
import ru.pb.market.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("")
    public List<UserDto> find(@RequestParam(required = false) String partName) {
        return userService.getAllUsers();
    }
    @PostMapping("")
    public void createUser(@RequestBody User user) {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getEmail());
        userService.createUser(user);
    }

    @PutMapping("")
    public void updateUser(@RequestBody UserDto userDto) {
        userService.update(userDto);
    }

}
