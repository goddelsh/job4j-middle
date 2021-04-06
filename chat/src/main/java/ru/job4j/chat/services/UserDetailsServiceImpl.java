package ru.job4j.chat.services;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.chat.models.Person;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersService usersService;

    public UserDetailsServiceImpl(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Person person = this.usersService.getUserByLogin(s);
        if(person == null) {
            throw new UsernameNotFoundException(s);
        }
        return new org.springframework.security.core.userdetails
                .User(person.getLogin(), person.getPassword(),
                person.getRoles().stream()
                        .map(el -> new SimpleGrantedAuthority(el.getName())).collect(Collectors.toList()));
    }
}
