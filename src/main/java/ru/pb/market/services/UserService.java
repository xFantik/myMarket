package ru.pb.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pb.market.data.Role;
import ru.pb.market.data.User;
import ru.pb.market.dto.UserDto;
import ru.pb.market.exceptions.ResourceNotFoundException;
import ru.pb.market.exceptions.UserAlreadyExistException;
import ru.pb.market.repositories.RoleRepository;
import ru.pb.market.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> findUserByName(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(user -> new UserDto(user.getUsername(), user.getEmail(), user.getRoles())).toList();
    }

    @Override
    @Transactional //транзакционность, чтобы сессия не закрылась до конца метода, чтобы достать роли
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByName(username).orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public void createUser(User user) {

        if (userRepository.findUserByUsername(user.getUsername()).isPresent())
            throw (new UserAlreadyExistException("Имя " + user.getUsername() + " занято"));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findRoleByName("ROLE_USER").get();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Transactional
    public void update(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new ResourceNotFoundException("User with id " + userDto.getId() + " not found"));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRoles(userDto.getRoles());
    }


}
