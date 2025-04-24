package tech.ysraltn.backend_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ysraltn.backend_api.model.*;

public interface UserRepository extends JpaRepository<User, Long> {//CRUD operasyonları için kullanılan sınıf
	Optional<User> findByEmail(String email); //optional: null dönüşünü güvenli hale getirir
}
