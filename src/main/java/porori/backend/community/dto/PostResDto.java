package porori.backend.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import porori.backend.community.domain.Post;

@NoArgsConstructor
public class PostResDto {
    @Getter
    public static class PostContentRes{
        private Long postId;

        @Builder
        public PostContentRes(Post post) {
            this.postId = post.getPostId();
        }
    }
}
