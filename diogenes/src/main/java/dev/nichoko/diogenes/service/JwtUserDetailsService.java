package dev.nichoko.diogenes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if ("username".equals(username)) {
            return new User("username", "$2a$12$y3V33JVHR2wbEaJKSDAIlOhEK/zEUOGaV93n3f3WG1egZ9dhKGSc2",
                    authorities);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}