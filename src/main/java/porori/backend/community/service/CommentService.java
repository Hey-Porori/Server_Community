package porori.backend.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.community.config.exception.post.NotFoundPostIdException;
import porori.backend.community.domain.Comment;
import porori.backend.community.domain.Post;
import porori.backend.community.dto.CommentResDto;
import porori.backend.community.dto.CommentResDto.*;
import porori.backend.community.dto.UserResDto;
import porori.backend.community.repository.CommentRepository;
import porori.backend.community.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final UserService userService;

    //게시글에 댓글 달기
    public void createComment(String token, Long postId, String content) {
        Long userId = userService.getUserId(token);

        Post post = postRepository.findByPostIdAndStatus(postId, "ACTIVE").orElseThrow(NotFoundPostIdException::new);

        Comment comment = Comment.builder().userId(userId).postId(post).content(content).build();

        commentRepository.save(comment);
    }

    //게시글 댓글 조회 (분리)
    public List<CommentDetailRes> getComments(String token, Long postId) {
        Post post = postRepository.findByPostIdAndStatus(postId, "ACTIVE").orElseThrow(NotFoundPostIdException::new);

        List<Comment> commentList = commentRepository.findAllByPostIdOrderByCreatedAt(post);
        List<Long> userIdList = commentList.stream().map(Comment::getUserId).collect(Collectors.toList());

        List<UserResDto.CommunityUserInfo> commentUserInfoBlocks = userService.sendCommunitiesInfoRequest(token, userIdList);

        HashMap<Long, UserResDto.CommunityUserInfo> commentUserInfoHashMap = new HashMap<>();
        commentUserInfoBlocks.forEach(communityUserInfo -> {
            commentUserInfoHashMap.put(communityUserInfo.getUserId(), communityUserInfo);
        });

        return commentList.stream().map(comment -> CommentResDto.CommentDetailRes.builder()
                .comment(comment)
                .userInfo(commentUserInfoHashMap.get(comment.getUserId())).build()).collect(Collectors.toList());
    }

}
