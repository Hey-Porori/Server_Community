package porori.backend.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import porori.backend.community.config.exception.bookmark.AlreadyBookmarkException;
import porori.backend.community.config.exception.bookmark.MyPostException;
import porori.backend.community.config.exception.post.NotFoundPostIdException;
import porori.backend.community.domain.Bookmark;
import porori.backend.community.domain.Post;
import porori.backend.community.dto.PostResDto.*;
import porori.backend.community.repository.BookmarkRepository;
import porori.backend.community.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;

    private final UserService userService;

    public PostContentRes createBookmark(String token, Long postId){
        //토큰 유효 확인
        userService.sendTestJwtRequest(token);

        Post post = postRepository.findByPostIdAndStatus(postId, "ACTIVE").orElseThrow(NotFoundPostIdException::new);

        //자기 게시글 확인
        Long userId = userService.getUserId(token);
        if(post.getUserId() == userId){
            throw new MyPostException();
        }
        //이미 북마크 한거 확인
         Optional<Bookmark> optional= bookmarkRepository.findByPostIdAndUserId(post, userId);
        if(optional.isPresent()){
            throw new AlreadyBookmarkException();
        }

        bookmarkRepository.save(Bookmark.builder()
                .userId(userId)
                .postId(post)
                .build());

        return new PostContentRes(post);

    }
}
