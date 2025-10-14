package com.example.courseworkLuchnetskyi.config;

import com.example.courseworkLuchnetskyi.model.Role;
import com.example.courseworkLuchnetskyi.model.UserAccount;
import com.example.courseworkLuchnetskyi.repository.RoleRepository;
import com.example.courseworkLuchnetskyi.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserAccessInitializer implements ApplicationRunner {

    private static final String DEFAULT_ROLE = "ROLE_USER";

    private final RoleRepository roleRepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        Role userRole = roleRepository.findByName(DEFAULT_ROLE)
                .orElseGet(() -> roleRepository.save(Role.builder().name(DEFAULT_ROLE).build()));

        for (UserAccount account : userAccountRepository.findAll()) {
            if (account.getRoles().isEmpty()) {
                account.getRoles().add(userRole);
                userAccountRepository.save(account);
            }
        }
    }
}
