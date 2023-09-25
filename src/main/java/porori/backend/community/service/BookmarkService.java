package porori.backend.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import porori.backend.community.config.exception.bookmark.AlreadyBookmarkException;
import porori.backend.community.config.exception.bookmark.MyPostException;
import porori.backend.community.config.exception.bookmark.NotFoundBookmarkIdException;
import porori.backend.community.config.exception.bookmark.NotMyBookmarkException;
import porori.backend.community.config.exception.post.NotFoundPostIdException;
import porori.backend.community.domain.Bookmark;
import porori.backend.community.domain.Post;
import porori.backend.community.dto.BookmarkResDto.*;
import porori.backend.community.dto.PostResDto.*;
import porori.backend.community.repository.BookmarkRepository;
import porori.backend.community.repository.CommentRepository;
import porori.backend.community.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final UserService userService;

    public PostContentRes createBookmark(String token, Long postId) {
        Post post = postRepository.findByPostIdAndStatus(postId, "ACTIVE").orElseThrow(NotFoundPostIdException::new);

        //자기 게시글 확인
        Long userId = userService.getUserId(token);
        if (post.getUserId() == userId) {
            throw new MyPostException();
        }
        //이미 북마크 한거 확인
        Optional<Bookmark> optional = bookmarkRepository.findByPostIdAndUserId(post, userId);
        if (optional.isPresent()) {
            throw new AlreadyBookmarkException();
        }

        bookmarkRepository.save(Bookmark.builder()
                .userId(userId)
                .postId(post)
                .build());

        return new PostContentRes(post);

    }

    //북마크 목록 보기
    public BookmarkPreviewListRes getMyBookmarkList(String token, int page){
        Long userId = userService.getUserId(token);

        ArrayList<BookmarkPreview> previewList = new ArrayList<>();
        //페이징 10개씩
        PageRequest pageRequest = PageRequest.of(page-1, 10);
        Page<Bookmark> pagedBookmark = bookmarkRepository.findAllByUserIdOrderByBookmarkIdDesc(userId, pageRequest);
        pagedBookmark.forEach(bookmark -> {
            Long bookmarkCnt = bookmarkRepository.countByPostId(bookmark.getPostId());
            Long commentCnt = commentRepository.countByPostIdAndUserId(bookmark.getPostId(), userId);
            previewList.add(BookmarkPreview.builder().bookmark(bookmark).bookmarkCnt(bookmarkCnt).commentCnt(commentCnt).build());
        });

        return BookmarkPreviewListRes.builder().previewList(previewList).totalPage(pagedBookmark.getTotalPages()).build();
    }

    //북마크 삭제하기
    public BookmarkIdRes deleteBookmark(String token, Long bookmarkId){
        Long userId = userService.getUserId(token);
        //북마크 존재 여부 확인
        Bookmark bookmark = bookmarkRepository.findByBookmarkId(bookmarkId).orElseThrow(NotFoundBookmarkIdException::new);


        //내가 생성한 북마크인지 확인
        if(bookmark.getUserId() != userId){
            throw new NotMyBookmarkException();
        }

        bookmarkRepository.delete(bookmark);
        return BookmarkIdRes.builder().bookmarkId(bookmarkId).build();
    }
}
