package porori.backend.community.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import porori.backend.community.config.BaseResponse;
import porori.backend.community.config.exception.user.TokenException;
import porori.backend.community.dto.PostReqDto;
import porori.backend.community.dto.PostResDto;
import porori.backend.community.service.AmazonS3Service;
import porori.backend.community.service.PostService;
import porori.backend.community.service.UserService;



@RequiredArgsConstructor
@RequestMapping("/api/communities")
@RestController
@Api(tags = "Post API")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final AmazonS3Service amazonS3Service;

    //게시글 작성하기
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성합니다.")
    @PostMapping("/post")
    public BaseResponse<PostResDto.PostContentRes> createPost(@RequestHeader("Authorization") String token, @RequestBody PostReqDto.PostContentReq postContent) {
        //토큰 유효 확인
        if(!userService.validationToken(token)){
            throw new TokenException();
        }

        Long userId = userService.getUserId(token);

        PostResDto.PostContentRes postContentRes = postService.createPost(userId, postContent);
        return new BaseResponse<>(postContentRes);
    }

    //Pre-Signed Url 발급받기
    @ApiOperation(value = "Pre-Signed Url 발급", notes = "Pre-Signed Url을 발급합니다.")

    @GetMapping("/url")
    public BaseResponse<PostResDto.PreSignedUrlRes> getPreSignedUrl(@RequestHeader("Authorization") String token) {
        //토큰 유효 확인
        if(!userService.validationToken(token)){
            throw new TokenException();
        }

        PostResDto.PreSignedUrlRes urlRes = amazonS3Service.getPreSignedUrl();
        return new BaseResponse<>(urlRes);
    }
}
