package porori.backend.community.config.exception.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostExceptionList {
    NOT_FOUND_POSTID_ERROR("P0001", HttpStatus.NOT_FOUND, "해당 postId로 Post를 찾을 수 없습니다.");
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
