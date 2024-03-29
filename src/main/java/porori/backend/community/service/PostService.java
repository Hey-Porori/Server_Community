package porori.backend.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import porori.backend.community.config.exception.post.NotMyPostException;
import porori.backend.community.domain.*;
import porori.backend.community.dto.PostReqDto.*;
import porori.backend.community.dto.PostResDto.*;
import porori.backend.community.dto.UserResDto.*;
import porori.backend.community.repository.*;
import porori.backend.community.config.exception.post.NotFoundPostIdException;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final CommentRepository commentRepository;

    private final PostAttachService postAttachService;
    private final TagService tagService;
    private final PostTagService postTagService;
    private final UserService userService;


    public PostContentRes createPost(String token, PostContentReq postContent) {
        Long userId = userService.getUserId(token);

        //Post 생성
        Post postEntity = Post.builder()
                .title(postContent.getTitle())
                .content(postContent.getContent())
                .location(postContent.getLocation())
                .latitude(postContent.getLatitude())
                .longitude(postContent.getLongitude())
                .userId(userId)
                .build();

        //PostAttach 생성
        List<PostAttach> imageList = postAttachService.saveAttach(postEntity, postContent.getImageNameList());
        postEntity.setImageList(imageList);

        //태그 저장
        List<Tag> tagList = tagService.saveTag(postContent.getTagList());

        //PostTag 생성
        List<PostTag> postTagList = postTagService.savePostTag(postEntity, tagList);
        postEntity.setTagList(postTagList);

        //Post 저장
        Post post = postRepository.save(postEntity);

        return new PostContentRes(post);
    }

    //게시글 지도에서 보기
    public List<PostOnMapRes> getPostsOnMap(String token, CurrentLocationReq currentLocation) {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        //주변 게시글 목록 가져오기
        List<Post> nearByPosts = postRepository.findNearByPosts(
                currentLocation.getLatitude(), currentLocation.getLongitude(), yesterday);

        //주변 게시글 목록에서 userId 추출
        List<Long> userIdList = nearByPosts.stream().map(Post::getUserId).collect(Collectors.toList());

        //userId로 communityUser 정보 가져오기
        List<CommunityUserInfo> userInfoBlocks = userService.sendCommunitiesInfoRequest(token, userIdList);

        HashMap<Long, CommunityUserInfo> communityUserInfoHashMap = new HashMap<>();
        userInfoBlocks.forEach(communityUserInfo -> {
            communityUserInfoHashMap.put(communityUserInfo.getUserId(), communityUserInfo);
        });

        List<PostOnMapRes> postOnMapList = new ArrayList<>();

        for (Post post : nearByPosts) {
            PostOnMapRes postOnMap = PostOnMapRes.builder()
                    .post(post)
                    .user(communityUserInfoHashMap.get(post.getUserId())).build();
            postOnMapList.add(postOnMap);
        }

        return postOnMapList;
    }

    //게시글 스와이프 보기
    public List<PostSwipeRes> getPostsOnSwipe(String token, List<Long> postIdList) {
        Long userId = userService.getUserId(token);

        //postId로 게시글 목록 가져오기
        List<Post> postsOnSwipe = new ArrayList<>();
        postIdList.forEach(postId -> {
            postsOnSwipe.add(
                    postRepository.findByPostIdAndStatusWithImageList(postId).orElseThrow(NotFoundPostIdException::new));
        });

        //게시글 목록에서 userId 추출
        List<Long> userIdList = postsOnSwipe.stream().map(Post::getUserId).collect(Collectors.toList());

        //userId로 communityUser 정보 가져오기
        List<CommunityUserInfo> userInfoBlocks = userService.sendCommunitiesInfoRequest(token, userIdList);

        HashMap<Long, CommunityUserInfo> communityUserInfoHashMap = new HashMap<>();
        userInfoBlocks.forEach(communityUserInfo -> {
            communityUserInfoHashMap.put(communityUserInfo.getUserId(), communityUserInfo);
        });

        return postsOnSwipe.stream().map(post -> {
                    boolean isBoookmark = bookmarkRepository.findByPostIdAndUserId(post, userId).isPresent();
                    return PostSwipeRes.builder()
                            .post(post).
                            user(communityUserInfoHashMap.get(post.getUserId()))
                            .isBookmark(isBoookmark).build();
                })
                .collect(Collectors.toList());
    }

    //게시글 상세보기 - 내 게시글인 경우 확인해보기
    public PostDetailRes getPostDetail(String token, Long postId) {
        Long userId = userService.getUserId(token);

        Post post = postRepository.findByPostIdAndStatus(postId, "ACTIVE").orElseThrow(NotFoundPostIdException::new);

        List<CommunityUserInfo> userInfoBlock = userService.sendCommunitiesInfoRequest(
                token, Collections.singletonList(post.getUserId()));

        boolean isBoookmark = bookmarkRepository.findByPostIdAndUserId(post, userId).isPresent();

        return PostDetailRes.builder().post(post).user(userInfoBlock.get(0)).isBookmark(isBoookmark).build();
    }

    //내 게시글 목록 보기
    public PostPreviewListRes getMyPostList(String token, int page) {
        Long userId = userService.getUserId(token);

        ArrayList<PostPreview> previewList = new ArrayList<>();
        //페이징 10개씩
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        Page<Post> pagedPost = postRepository.findAllByUserIdAndStatusOrderByPostIdDesc(userId, "ACTIVE", pageRequest);
        pagedPost.forEach(post -> {
            Long bookmarkCnt = bookmarkRepository.countByPostId(post);
            Long commentCnt = commentRepository.countByPostIdAndUserId(post, userId);
            previewList.add(PostPreview.builder().post(post).bookmarkCnt(bookmarkCnt).commentCnt(commentCnt).build());
        });

        return PostPreviewListRes.builder().previewList(previewList).totalPage(pagedPost.getTotalPages()).build();
    }

    //게시글 수정
    public PostContentRes editPost(String token, Long postId, EditPostContentReq editPostContent) {
        Long userId = userService.getUserId(token);

        //게시글 존재 여부 확인
        Post post = postRepository.findByPostIdAndStatus(postId, "ACTIVE").orElseThrow(NotFoundPostIdException::new);

        //자신의 게시글 확인
        if (post.getUserId() != userId) {
            throw new NotMyPostException();
        }

        //제목, 본문 수정
        post.editPost(editPostContent);


        //PostAttach 수정
        List<PostAttach> imageList = postAttachService.saveAttach(post, editPostContent.getImageNameList());
        post.setImageList(imageList);

        //태그 수정
        List<Tag> tagList = tagService.saveTag(editPostContent.getTagList());

        //PostTag 수정
        List<PostTag> postTagList = postTagService.savePostTag(post, tagList);
        post.setTagList(postTagList);


        return PostContentRes.builder().post(post).build();
    }

    //게시글 삭제
    public PostContentRes deletePost(String token, Long postId) {
        Long userId = userService.getUserId(token);

        //게시글 존재 여부 확인
        Post post = postRepository.findByPostIdAndStatus(postId, "ACTIVE").orElseThrow(NotFoundPostIdException::new);

        //자신의 게시글 확인
        if (post.getUserId() != userId) {
            throw new NotMyPostException();
        }

        post.deletePost();

        return PostContentRes.builder().post(post).build();

    }

}
