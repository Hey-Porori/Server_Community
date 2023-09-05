package porori.backend.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import porori.backend.community.config.BaseResponse;
import porori.backend.community.dto.CommentReqDto.*;
import porori.backend.community.dto.CommentResDto.*;
import porori.backend.community.service.CommentService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/communities")
@RestController
@Tag(name = "Comment API", description = "댓글 API")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "게시글에 댓글 작성", description = "postId로 게시글의 댓글을 작성합니다.")
    @PostMapping("/comments/{postId}")
    public BaseResponse<String> createComments(@RequestHeader("Authorization") String token, @PathVariable Long postId,
                                               @RequestBody CommentContentReq comment){
        commentService.createComment(token, postId, comment.getContent());
        return new BaseResponse<>("postId가 "+postId+"인 게시글에 댓글이 작성되었습니다.");
    }

    @Operation(summary = "게시글의 댓글 조회", description = "postId로 게시글의 댓글을 조회합니다.")
    @GetMapping("/comments/{postId}")
    public BaseResponse<List<CommentDetailRes>> getComments(@RequestHeader("Authorization") String token, @PathVariable Long postId){
        return new BaseResponse<>(commentService.getComments(token, postId));
    }
}
