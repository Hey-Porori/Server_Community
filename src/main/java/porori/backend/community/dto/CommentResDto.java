package porori.backend.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import porori.backend.community.domain.Comment;

import java.time.LocalDateTime;

@NoArgsConstructor
public class CommentResDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentDetailRes{
        private String content;
        private LocalDateTime createdAt;
        private String nickname;
        private String image;
        private String backgroundColor;

        @Builder
        public CommentDetailRes(Comment comment, UserResDto.CommunityUserInfo userInfo){
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
            this.nickname = userInfo.getNickname();
            this.image = userInfo.getImage();
            this.backgroundColor = userInfo.getBackgroundColor();
        }
    }
}
