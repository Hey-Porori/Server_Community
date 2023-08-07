package porori.backend.community.config.exception.bookmark;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BookmarkExceptionList {
    MY_POST_ERROR("B0001", HttpStatus.FORBIDDEN, "자신의 게시글에는 북마크를 생성할 수 없습니다."),
    ALREADY_BOOKMARK_ERROR("B0002", HttpStatus.CONFLICT, "이미 북마크된 게시글입니다.");
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
