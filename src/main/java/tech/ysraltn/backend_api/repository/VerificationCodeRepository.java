package tech.ysraltn.backend_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ysraltn.backend_api.model.VerificationCode;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByEmailAndCode(String email, String code);
}
