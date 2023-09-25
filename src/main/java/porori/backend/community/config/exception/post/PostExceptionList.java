package porori.backend.community.config.exception.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostExceptionList {
    NOT_FOUND_POSTID_ERROR("P0001", HttpStatus.NOT_FOUND, "해당 postId는 없거나 삭제된 Post입니다."),
    NOT_MY_POST_ERROR("P0002", HttpStatus.FORBIDDEN, "자신의 Post가 아닙니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
