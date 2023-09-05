package porori.backend.community.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CommentReqDto {
    @Getter
    public static class CommentContentReq{
        String content;
    }
}
