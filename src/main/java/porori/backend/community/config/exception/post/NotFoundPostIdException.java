package porori.backend.community.config.exception.post;

import static porori.backend.community.config.exception.post.PostExceptionList.NOT_FOUND_POSTID_ERROR;

public class NotFoundPostIdException extends PostException{
    public NotFoundPostIdException(){
        super(NOT_FOUND_POSTID_ERROR.getErrorCode(), NOT_FOUND_POSTID_ERROR.getHttpStatus(), NOT_FOUND_POSTID_ERROR.getMessage());
    }
}
