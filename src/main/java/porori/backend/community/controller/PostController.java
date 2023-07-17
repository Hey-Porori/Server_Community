package porori.backend.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import porori.backend.community.config.BaseException;
import porori.backend.community.config.BaseResponse;
import porori.backend.community.dto.PostReqDto;
import porori.backend.community.dto.PostResDto;
import porori.backend.community.service.PostService;
import porori.backend.community.service.UserService;



@RequiredArgsConstructor
@RequestMapping("/api/communities")
@RestController
public class PostController {
    private final PostService postService;
    private final UserService userService;

    //게시글 작성하기
    @PostMapping("/")
    public BaseResponse<PostResDto.PostContentRes> createPost(@RequestHeader("Authorization") String token, @RequestBody PostReqDto.PostContentReq postContent){
        Long userId = userService.getUserId(token);
        try {
            PostResDto.PostContentRes postContentRes = postService.createPost(userId, postContent);
            return new BaseResponse<>(postContentRes);
        } catch (BaseException exception) {
            exception.printStackTrace();
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
