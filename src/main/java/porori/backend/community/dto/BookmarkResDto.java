package porori.backend.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import porori.backend.community.domain.Bookmark;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor

public class BookmarkResDto {
    @Getter
    public static class BookmarkIdRes {
        private Long bookmarkId;

        @Builder
        public BookmarkIdRes(Long bookmarkId) {
            this.bookmarkId = bookmarkId;
        }
    }

    @Getter
    @Builder
    public static class BookmarkPreviewListRes {
        //페이징 처리
        //제목, 내용, 위치, 북마크 수 , 댓글 수, 게시글 작성 시간, 1번 이미지
        int totalPage;
        List<BookmarkPreview> previewList;

    }

    @Getter
    public static class BookmarkPreview {
        private Long bookmarkId;
        private Long postId;
        private String title;
        private String content;
        private String image;
        private String location;
        private Long bookmarkCnt;
        private Long commentCnt;
        private LocalDateTime createdAt;

        @Builder
        public BookmarkPreview(Bookmark bookmark, Long bookmarkCnt, Long commentCnt){
            this.bookmarkId = bookmark.getBookmarkId();
            this.postId = bookmark.getPostId().getPostId();
            this.title = bookmark.getPostId().getTitle();
            this.content = bookmark.getPostId().getContent();
            this.image = bookmark.getPostId().getImageList()
                    .stream()
                    .findFirst()
                    .map(image -> image.getImageName())
                    .orElse(null);
            this.location = bookmark.getPostId().getLocation();
            this.bookmarkCnt = bookmarkCnt;
            this.commentCnt = commentCnt;
            this.createdAt = bookmark.getPostId().getCreatedAt();
        }
    }
}
