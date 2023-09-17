package porori.backend.community.config.exception.bookmark;

import static porori.backend.community.config.exception.bookmark.BookmarkExceptionList.NOT_FOUND_BOOKMARKID_ERROR;

public class NotFoundBookmarkIdException extends BookmarkException {
    public NotFoundBookmarkIdException(){
        super(NOT_FOUND_BOOKMARKID_ERROR.getErrorCode(), NOT_FOUND_BOOKMARKID_ERROR.getHttpStatus(), NOT_FOUND_BOOKMARKID_ERROR.getMessage());
    }
}
