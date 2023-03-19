package passcard.infrastructure.service;

import passcard.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import passcard.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsService implements ReactiveUserDetailsService {

    private UserRepository repository;

    @Override
    public Mono<UserDetails> findByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRoles())
                        .build())
                .map(user -> (UserDetails) user)
                .switchIfEmpty(Mono.empty());
    }
}
