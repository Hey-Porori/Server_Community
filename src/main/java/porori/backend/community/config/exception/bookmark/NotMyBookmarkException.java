package porori.backend.community.config.exception.bookmark;

import static porori.backend.community.config.exception.bookmark.BookmarkExceptionList.NOT_MY_BOOKMARK_ERROR;

public class NotMyBookmarkException extends BookmarkException{
    public NotMyBookmarkException(){
        super(NOT_MY_BOOKMARK_ERROR.getErrorCode(), NOT_MY_BOOKMARK_ERROR.getHttpStatus(), NOT_MY_BOOKMARK_ERROR.getMessage());
    }
}
