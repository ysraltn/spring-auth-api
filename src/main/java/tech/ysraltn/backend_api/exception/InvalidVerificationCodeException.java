package tech.ysraltn.backend_api.exception;

import org.springframework.http.HttpStatus;

public class InvalidVerificationCodeException extends BaseException {
    public InvalidVerificationCodeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
