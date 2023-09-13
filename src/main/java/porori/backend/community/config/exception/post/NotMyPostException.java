package porori.backend.community.config.exception.post;

import static porori.backend.community.config.exception.post.PostExceptionList.NOT_MY_POST_ERROR;

public class NotMyPostException extends PostException{
    public NotMyPostException(){
        super(NOT_MY_POST_ERROR.getErrorCode(), NOT_MY_POST_ERROR.getHttpStatus(), NOT_MY_POST_ERROR.getMessage());
    }
}
