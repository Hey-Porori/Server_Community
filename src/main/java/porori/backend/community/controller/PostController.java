package porori.backend.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import porori.backend.community.config.BaseResponse;
import porori.backend.community.dto.PostReqDto;
import porori.backend.community.dto.PostResDto;
import porori.backend.community.service.AmazonS3Service;
import porori.backend.community.service.PostService;


@RequiredArgsConstructor
@RequestMapping("/api/communities")
@RestController
@Tag(name = "Post API", description = "게시글 API")
public class PostController {
    private final PostService postService;
    private final AmazonS3Service amazonS3Service;

    //게시글 작성하기
    @Operation(summary = "게시글 작성", description = "게시글을 작성합니다.")
    @PostMapping("/post")
    public BaseResponse<PostResDto.PostContentRes> createPost(@RequestHeader("Authorization") String token, @RequestBody PostReqDto.PostContentReq postContent) {
        return new BaseResponse<>(postService.createPost(token, postContent));
    }

    //Pre-Signed Url 발급받기
    @Operation(summary = "Pre-Signed Url 발급", description = "Pre-Signed Url을 발급합니다.")
    @GetMapping("/url")
    public BaseResponse<PostResDto.PreSignedUrlRes> getPreSignedUrl(@RequestHeader("Authorization") String token) {
        return new BaseResponse<>(amazonS3Service.getPreSignedUrl(token));
    }


}
