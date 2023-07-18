package porori.backend.community.controller;

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
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final AmazonS3Service amazonS3Service;

    //게시글 작성하기
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
