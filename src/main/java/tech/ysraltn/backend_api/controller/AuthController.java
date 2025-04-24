package tech.ysraltn.backend_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ysraltn.backend_api.dto.RegisterRequest;
import tech.ysraltn.backend_api.dto.ResetPasswordRequest;
import tech.ysraltn.backend_api.dto.AuthResponse;
import tech.ysraltn.backend_api.dto.ForgotPasswordRequest;
import tech.ysraltn.backend_api.dto.LoginRequest;
import tech.ysraltn.backend_api.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService; //spring inject etti

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        authService.sendResetCode(request);
        return ResponseEntity.ok("Doğrulama kodu gönderildi.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("Şifre başarıyla güncellendi.");
    }

}

//@RequestBody: HTTP request gövdesindeki JSON içeriği, RegisterRequest sınıfına deserialize eder.

//@Valid: RegisterRequest üzerindeki field validation kurallarını (örneğin @NotBlank, @Email) aktif eder. Eğer validation hatası varsa Spring otomatik olarak 400 Bad Request döner.
