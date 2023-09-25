package porori.backend.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import porori.backend.community.config.BaseResponse;
import porori.backend.community.dto.PostReqDto.*;
import porori.backend.community.dto.PostResDto.*;
import porori.backend.community.service.AmazonS3Service;
import porori.backend.community.service.PostService;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/api/communities")
@RestController
@Tag(name = "Post API", description = "게시글 API")
public class PostController {
    private final PostService postService;
    private final AmazonS3Service amazonS3Service;

    @Operation(summary = "게시글 작성", description = "게시글을 작성합니다.")
    @PostMapping("/post")
    public BaseResponse<PostContentRes> createPost(@RequestHeader("Authorization") String token, @RequestBody PostContentReq postContent) {
        return new BaseResponse<>(postService.createPost(token, postContent));
    }

    @Operation(summary = "Pre-Signed Url 발급", description = "Pre-Signed Url을 발급합니다.")
    @GetMapping("/url")
    public BaseResponse<PreSignedUrlRes> getPreSignedUrl(@RequestHeader("Authorization") String token) {
        return new BaseResponse<>(amazonS3Service.getPreSignedUrl(token));
    }

    @Operation(summary = "지도에서 게시글 목록 보기", description = "현재 위치에서 2KM 이내의 게시글 목록을 조회합니다.")
    @PostMapping("/post/map")
    public BaseResponse<List<PostOnMapRes>> getPostsOnMap(@RequestHeader("Authorization") String token, @RequestBody CurrentLocationReq currentLocation){
        return new BaseResponse<>(postService.getPostsOnMap(token, currentLocation));
    }

    @Operation(summary = "게시글 스와이프 보기", description = "요청받은 postId의 게시글 목록을 조회합니다.")
    @PostMapping("/post/swipe")
    public BaseResponse<List<PostSwipeRes>> getPostsOnSwipe(@RequestHeader("Authorization") String token, @RequestBody PostIdListReq postIdList){
        return new BaseResponse<>(postService.getPostsOnSwipe(token, postIdList.getPostIdList()));
    }

    @Operation(summary = "게시글 상세 보기", description = "postId로 게시글을 조회합니다.")
    @GetMapping("/post/{postId}")
    public BaseResponse<PostDetailRes> getPostDetail(@RequestHeader("Authorization") String token, @PathVariable Long postId){
        return new BaseResponse<>(postService.getPostDetail(token, postId));
    }

    @Operation(summary = "내 게시글 목록 보기", description = "page=1부터 totalPage까지 내 게시글 목록을 10개씩 조회합니다.")
    @GetMapping("/post/my/{page}")
    public BaseResponse<PostPreviewListRes> getMyPostList(@RequestHeader("Authorization") String token, @PathVariable int page){
        return new BaseResponse<>(postService.getMyPostList(token, page));
    }

    @Operation(summary = "게시글 수정하기", description = "postId로 게시글을 수정합니다.")
    @PutMapping("/post/{postId}")
    public BaseResponse<PostContentRes> editPost(@RequestHeader("Authorization") String token, @PathVariable Long postId, @RequestBody EditPostContentReq editPostContent){
        return new BaseResponse<>(postService.editPost(token, postId, editPostContent));
    }

    @Operation(summary = "게시글 삭제하기", description = "postId로 게시글을 삭제합니다.")
    @DeleteMapping("/post/{postId}")
    public BaseResponse<PostContentRes> deletePost(@RequestHeader("Authorization") String token, @PathVariable Long postId){
        return new BaseResponse<>(postService.deletePost(token, postId));
    }

}
