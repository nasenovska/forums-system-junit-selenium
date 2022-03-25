package org.master.testing.security.service.impl;

import org.master.testing.entity.User;
import org.master.testing.repository.UserRepository;
import org.master.testing.security.InternalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String name) {
        User probe = new User();
        probe.setEmail(name);

        Optional<User> optionalUser = userRepository.findOne(Example.of(probe));

        return optionalUser.map(InternalUser::create)
                .orElseThrow(() -> new UsernameNotFoundException(name));

    }
}
