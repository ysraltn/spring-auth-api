package tech.ysraltn.backend_api.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails { //temel kullanici bilgilerini bu interfaceden impl ettik

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled; //hesap aktif mi?

    // --- UserDetails methodları ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role.name()); // yetki olarak sadece rol ismini döner
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // hesap süresi dolmadı
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // hesap kilitli değil
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // şifre süresi dolmadı
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
