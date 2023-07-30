package porori.backend.community.config.exception.post;

import org.springframework.http.HttpStatus;
import porori.backend.community.config.exception.ApplicationException;

public abstract class PostException extends ApplicationException {
    protected PostException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
