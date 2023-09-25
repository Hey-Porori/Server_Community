package porori.backend.community.config.exception.bookmark;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BookmarkExceptionList {
    MY_POST_ERROR("B0001", HttpStatus.FORBIDDEN, "자신의 게시글에는 북마크를 생성할 수 없습니다."),
    ALREADY_BOOKMARK_ERROR("B0002", HttpStatus.CONFLICT, "이미 북마크된 게시글입니다."),
    NOT_FOUND_BOOKMARKID_ERROR("B0003", HttpStatus.NOT_FOUND, "해당 bookmarkId는 없거나 삭제된 북마크입니다."),
    NOT_MY_BOOKMARK_ERROR("B0004", HttpStatus.FORBIDDEN, "자신이 설정한 북마크가 아닙니다.");
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
