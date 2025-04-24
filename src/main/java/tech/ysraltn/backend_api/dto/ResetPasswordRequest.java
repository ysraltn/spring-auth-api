package tech.ysraltn.backend_api.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @Email
    private String email;

    private String code;
    private String newPassword;
}
