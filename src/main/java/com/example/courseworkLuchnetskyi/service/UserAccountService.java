package com.example.courseworkLuchnetskyi.service;

import com.example.courseworkLuchnetskyi.dto.RegisterRequest;
import com.example.courseworkLuchnetskyi.model.AuthProvider;
import com.example.courseworkLuchnetskyi.model.Role;
import com.example.courseworkLuchnetskyi.model.UserAccount;
import com.example.courseworkLuchnetskyi.repository.RoleRepository;
import com.example.courseworkLuchnetskyi.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAccountService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String DEFAULT_ROLE = "ROLE_USER";

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .toList()
        );
    }

    public UserAccount register(RegisterRequest request) {
        userAccountRepository.findByUsername(request.username()).ifPresent(u -> {
            throw new IllegalArgumentException("Username already exists");
        });
        Role role = roleRepository.findByName(DEFAULT_ROLE)
                .orElseGet(() -> roleRepository.save(Role.builder().name(DEFAULT_ROLE).build()));
        UserAccount account = UserAccount.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .provider(AuthProvider.LOCAL.name())
                .roles(Set.of(role))
                .build();
        return userAccountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public List<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }
}
