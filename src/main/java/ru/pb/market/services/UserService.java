package ru.pb.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pb.market.data.Product;
import ru.pb.market.data.Role;
import ru.pb.market.data.User;
import ru.pb.market.dto.ProductDto;
import ru.pb.market.dto.UserDto;
import ru.pb.market.exceptions.ResourceNotFoundException;
import ru.pb.market.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public Optional<User> findUserByName(String username) {
        return userRepository.findUserByName(username);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(user -> new UserDto(user.getName(), user.getEmail(), user.getRoles())).toList();
    }

    @Override
    @Transactional //транзакционность, чтобы сессия не закрылась до конца метода, чтобы достать роли
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByName(username).orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found"));
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public void addUser(UserDto userDto) {
//        userValidator.validate(userDto);
        userRepository.save(new User(null, userDto.getName(), "$2a$12$rr6hixcXbZPIPTYRPW5fkO8OZlcyESLYdWDyTgQ77jr.Lp2OSHjjK", userDto.getEmail(), userDto.getRoles()));
    }

    @Transactional
    public void update(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new ResourceNotFoundException("User with id " + userDto.getId() + " not found"));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRoles(userDto.getRoles());
    }



}
