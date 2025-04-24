package tech.ysraltn.backend_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
	@NotBlank(message = "Ad soyad boş olamaz")
    private String fullName;

    @Email(message = "Geçerli bir e-posta girin")
    @NotBlank(message = "E-posta boş olamaz")
    private String email;

    @NotBlank(message = "Şifre boş olamaz")
    private String password;
}
