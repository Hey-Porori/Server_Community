package porori.backend.community.config.exception.user;

import static porori.backend.community.config.exception.user.UserExceptionList.TOKEN_ERROR;

public class TokenException extends UserException{
    public TokenException() {
        super(TOKEN_ERROR.getErrorCode(), TOKEN_ERROR.getHttpStatus(), TOKEN_ERROR.getMessage());
    }
}
