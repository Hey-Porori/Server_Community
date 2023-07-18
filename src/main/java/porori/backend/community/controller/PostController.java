package porori.backend.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import porori.backend.community.config.BaseException;
import porori.backend.community.config.BaseResponse;
import porori.backend.community.dto.PostReqDto;
import porori.backend.community.dto.PostResDto;
import porori.backend.community.service.AmazonS3Service;
import porori.backend.community.service.PostService;
import porori.backend.community.service.UserService;

import static porori.backend.community.config.BaseResponseStatus.TOKEN_ERROR;


@RequiredArgsConstructor
@RequestMapping("/api/communities")
@RestController
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final AmazonS3Service amazonS3Service;

    //게시글 작성하기
    @PostMapping("/post")
    public BaseResponse<PostResDto.PostContentRes> createPost(@RequestHeader("Authorization") String token, @RequestBody PostReqDto.PostContentReq postContent) throws BaseException {
        //토큰 유효 확인
        if(!userService.validationToken(token)){
            throw new BaseException(TOKEN_ERROR);
        }

        Long userId = userService.getUserId(token);

        try {
            PostResDto.PostContentRes postContentRes = postService.createPost(userId, postContent);
            return new BaseResponse<>(postContentRes);
        } catch (BaseException exception) {
            exception.printStackTrace();
            return new BaseResponse<>(exception.getStatus());
        }
    }

    //Pre-Signed Url 발급받기
    @GetMapping("/url")
    public BaseResponse<PostResDto.PreSignedUrlRes> getPreSignedUrl(@RequestHeader("Authorization") String token) throws BaseException {
        //토큰 유효 확인
        if(!userService.validationToken(token)){
            throw new BaseException(TOKEN_ERROR);
        }

        PostResDto.PreSignedUrlRes urlRes = amazonS3Service.getPreSignedUrl();
        return new BaseResponse<>(urlRes);
    }
}
