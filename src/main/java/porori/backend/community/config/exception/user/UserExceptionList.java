package porori.backend.community.config.exception.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserExceptionList {
    TOKEN_ERROR("U0001", HttpStatus.UNAUTHORIZED, "AccessToken 값이 잘못되었습니다"),
    USER_BAD_GATEWAY_ERROR("U0002", HttpStatus.BAD_GATEWAY, "User 서버로부터 올바른 응답을 받지 못했습니다.");
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
