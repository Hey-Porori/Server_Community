package porori.backend.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import porori.backend.community.domain.*;
import porori.backend.community.dto.UserResDto.CommunityUserInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
public class PostResDto {
    @Getter
    public static class PostContentRes {
        private Long postId;

        @Builder
        public PostContentRes(Post post) {
            this.postId = post.getPostId();
        }
    }

    @Getter
    public static class PreSignedUrlRes {
        private String url;

        @Builder
        public PreSignedUrlRes(String url) {
            this.url = url;
        }
    }

    @Getter
    public static class PostOnMapRes {
        private Long postId;
        private Double latitude;
        private Double longitude;
        private String imageUrl;
        private String backgroundColor;

        @Builder
        public PostOnMapRes(Post post, CommunityUserInfo user) {
            this.postId = post.getPostId();
            this.latitude = post.getLatitude();
            this.longitude = post.getLongitude();
            this.imageUrl = user.getImage();
            this.backgroundColor = user.getBackgroundColor();
        }
    }

    @Getter
    public static class PostSwipeRes {
        private Long postId;
        private String title;
        private String content;
        private List<String> tagList;
        private List<String> imageList;
        private String nickName;
        private String imageUrl;
        private String backgroundColor;

        @Builder
        public PostSwipeRes(Post post, CommunityUserInfo user) {
            this.postId = post.getPostId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.tagList = post.getTagList().stream().map(postTag -> postTag.getTagId().getName()).collect(Collectors.toList());
            this.imageList = post.getImageList().stream().map(PostAttach::getImageName).collect(Collectors.toList());
            this.nickName = user.getNickname();
            this.imageUrl = user.getImage();
            this.backgroundColor = user.getBackgroundColor();
        }
    }

    @Getter
    public static class PostDetailRes {
        private String title;
        private String content;
        private String location;
        private List<String> tagList;
        private List<String> imageList;
        private LocalDateTime createdAt;
        private String nickName;
        private String imageUrl;
        private String backgroundColor;


        @Builder
        public PostDetailRes(Post post, CommunityUserInfo user) {
            this.title = post.getTitle();
            this.content = post.getContent();
            this.location = post.getLocation();
            this.tagList = post.getTagList().stream().map((postTag) -> postTag.getTagId().getName()).collect(Collectors.toList());
            this.imageList = post.getImageList().stream().map(PostAttach::getImageName).collect(Collectors.toList());
            this.createdAt = post.getCreatedAt();
            this.nickName = user.getNickname();
            this.imageUrl = user.getImage();
            this.backgroundColor = user.getBackgroundColor();
        }
    }

    @Getter
    @Builder
    public static class PostPreviewListRes {
        //페이징 처리
        //제목, 내용, 위치, 북마크 수 , 댓글 수, 게시글 작성 시간, 1번 이미지
        int totalPage;
        List<PostPreview> previewList;

    }

    @Getter
    public static class PostPreview {
        private Long postId;
        private String title;
        private String content;
        private String image;
        private String location;
        private Long bookmarkCnt;
        private Long commentCnt;
        private LocalDateTime createdAt;

        @Builder
        public PostPreview(Post post, Long bookmarkCnt, Long commentCnt){
            this.postId = post.getPostId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.image = post.getImageList()
                    .stream()
                    .findFirst()
                    .map(image -> image.getImageName())
                    .orElse(null);
            this.location = post.getLocation();
            this.bookmarkCnt = bookmarkCnt;
            this.commentCnt = commentCnt;
            this.createdAt = post.getCreatedAt();
        }
    }

}
