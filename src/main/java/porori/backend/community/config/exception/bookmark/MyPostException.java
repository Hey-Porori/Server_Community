package porori.backend.community.config.exception.bookmark;

import static porori.backend.community.config.exception.bookmark.BookmarkExceptionList.MY_POST_ERROR;

public class MyPostException extends BookmarkException{
    public MyPostException(){
        super(MY_POST_ERROR.getErrorCode(), MY_POST_ERROR.getHttpStatus(), MY_POST_ERROR.getMessage());
    }
}
