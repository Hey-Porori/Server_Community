package porori.backend.community.config.exception.bookmark;

import static porori.backend.community.config.exception.bookmark.BookmarkExceptionList.ALREADY_BOOKMARK_ERROR;

public class AlreadyBookmarkException extends BookmarkException{
    public AlreadyBookmarkException() {
        super(ALREADY_BOOKMARK_ERROR.getErrorCode(), ALREADY_BOOKMARK_ERROR.getHttpStatus(), ALREADY_BOOKMARK_ERROR.getMessage());
    }
}
