package tech.ysraltn.backend_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.ysraltn.backend_api.repository.UserRepository;

@Service
@RequiredArgsConstructor //constructor-based dependency injection yapmasını sağlar
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository; //final alanlar inject edilir

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));
    }
}
