package porori.backend.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import porori.backend.community.domain.Post;
import porori.backend.community.domain.PostAttach;
import porori.backend.community.domain.PostTag;
import porori.backend.community.domain.Tag;
import porori.backend.community.dto.PostReqDto;
import porori.backend.community.dto.PostResDto;
import porori.backend.community.dto.UserReqDto;
import porori.backend.community.dto.UserResDto.CommunityUserInfo;
import porori.backend.community.repository.*;
import porori.backend.community.config.exception.post.NotFoundPostIdException;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;

    private final PostAttachService postAttachService;
    private final TagService tagService;
    private final PostTagService postTagService;
    private final UserService userService;


    public PostResDto.PostContentRes createPost(String token, PostReqDto.PostContentReq postContent) {
        //토큰 유효 확인
        userService.sendTestJwtRequest(token);

        Long userId = userService.getUserId(token);

        //Post 생성
        Post postEntity = Post.builder()
                .title(postContent.getTitle())
                .content(postContent.getContent())
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

        return new PostResDto.PostContentRes(post);
    }

    //게시글 지도에서 보기
    public List<PostResDto.PostOnMapRes> getPostsOnMap(String token, PostReqDto.CurrentLocationReq currentLocation) {
        //토큰 유효 확인
        userService.sendTestJwtRequest(token);

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        //주변 게시글 목록 가져오기
        List<Post> nearByPosts = postRepository.findNearByPosts(currentLocation.getLatitude(), currentLocation.getLongitude(), yesterday);

        //주변 게시글 목록에서 userId 추출
        List<Long> userIdList = nearByPosts.stream().map(Post::getUserId).collect(Collectors.toList());

        //userId로 communityUser 정보 가져오기
        List<CommunityUserInfo> userInfoBlocks = userService.sendCommunitiesInfoRequest(token, UserReqDto.UserIdListReq.builder().userIdList(userIdList).build());

        HashMap<Long, CommunityUserInfo> communityUserInfoHashMap = new HashMap<>();
        userInfoBlocks.forEach(communityUserInfo -> {
            communityUserInfoHashMap.put(communityUserInfo.getUserId(), communityUserInfo);
        });

        List<PostResDto.PostOnMapRes> postOnMapList = new ArrayList<>();

        for (Post post : nearByPosts) {
            PostResDto.PostOnMapRes postOnMap = PostResDto.PostOnMapRes.builder()
                    .post(post)
                    .user(communityUserInfoHashMap.get(post.getUserId()))
                    .build();
            postOnMapList.add(postOnMap);
        }

        return postOnMapList;
    }

    //게시글 스와이프 보기
    public List<PostResDto.PostSwipeRes> getPostsOnSwipe(String token, List<Long> postIdList) {
        //토큰 유효 확인
        userService.sendTestJwtRequest(token);

        //postId로 게시글 목록 가져오기
        List<Post> postsOnSwipe = new ArrayList<>();
        postIdList.forEach(postId->{
            postsOnSwipe.add(postRepository.findByPostIdAndStatusWithImageList(postId).orElseThrow(NotFoundPostIdException::new));
        });

        //게시글 목록에서 userId 추출
        List<Long> userIdList = postsOnSwipe.stream().map(Post::getUserId).collect(Collectors.toList());

        //userId로 communityUser 정보 가져오기
        List<CommunityUserInfo> userInfoBlocks = userService.sendCommunitiesInfoRequest(token, UserReqDto.UserIdListReq.builder().userIdList(userIdList).build());

        HashMap<Long, CommunityUserInfo> communityUserInfoHashMap = new HashMap<>();
        userInfoBlocks.forEach(communityUserInfo -> {
            communityUserInfoHashMap.put(communityUserInfo.getUserId(), communityUserInfo);
        });

        return postsOnSwipe.stream()
                .map(post -> PostResDto.PostSwipeRes.builder()
                        .post(post)
                        .user(communityUserInfoHashMap.get(post.getUserId()))
                        .build())
                .collect(Collectors.toList());
    }

}
