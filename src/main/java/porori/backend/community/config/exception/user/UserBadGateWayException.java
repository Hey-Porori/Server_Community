package porori.backend.community.config.exception.user;

import static porori.backend.community.config.exception.user.UserExceptionList.USER_BAD_GATEWAY_ERROR;

public class UserBadGateWayException extends UserException{
    public UserBadGateWayException(){
        super(USER_BAD_GATEWAY_ERROR.getErrorCode(), USER_BAD_GATEWAY_ERROR.getHttpStatus(), USER_BAD_GATEWAY_ERROR.getMessage());
    }
}
