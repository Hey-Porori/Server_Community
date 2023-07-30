package porori.backend.community.config.exception.user;

import static porori.backend.community.config.exception.user.UserExceptionList.JSON_ERROR;

public class JsonException extends UserException{
    public JsonException(){
        super(JSON_ERROR.getErrorCode(), JSON_ERROR.getHttpStatus(), JSON_ERROR.getMessage());
    }
}
