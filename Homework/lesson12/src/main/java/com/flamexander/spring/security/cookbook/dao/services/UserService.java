package com.flamexander.spring.security.cookbook.dao.services;

import com.flamexander.spring.security.cookbook.dao.entities.Role;
import com.flamexander.spring.security.cookbook.dao.entities.Rule;
import com.flamexander.spring.security.cookbook.dao.entities.User;
import com.flamexander.spring.security.cookbook.dao.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        var result = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                                    mapRolesToAuthorities(user.getRoles(), user.getRules()));


        return result;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles, Collection<Rule> rules) {
        var resultRules = rules.stream().map(rule -> new SimpleGrantedAuthority(rule.getName())).collect(Collectors.toList());
        var resultRoles = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        var allRulesFromRoles = roles.stream().map(role -> role.getRules()).flatMap(m -> m.stream()).
                map(m ->  new SimpleGrantedAuthority(m.getName())).collect(Collectors.toList());

        resultRules = Stream.concat(allRulesFromRoles.stream(), resultRules.stream()).distinct().collect(Collectors.toList());

        return Stream.concat(resultRoles.stream(), resultRules.stream()).collect(Collectors.toList());
    }
}