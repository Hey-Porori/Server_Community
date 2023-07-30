package porori.backend.community.config.exception.bookmark;

import org.springframework.http.HttpStatus;
import porori.backend.community.config.exception.ApplicationException;

public class BookmarkException extends ApplicationException {
    protected BookmarkException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
