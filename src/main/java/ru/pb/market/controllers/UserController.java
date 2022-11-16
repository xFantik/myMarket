package ru.pb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pb.market.dto.ProductDto;
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
    public void addUser(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }

    @PutMapping("")
    public void updateUser(@RequestBody UserDto userDto) {
        userService.update(userDto);
    }

}
