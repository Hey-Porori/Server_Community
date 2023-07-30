package porori.backend.community.config.exception.user;

import org.springframework.http.HttpStatus;
import porori.backend.community.config.exception.ApplicationException;

public abstract class UserException extends ApplicationException {
    protected UserException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
