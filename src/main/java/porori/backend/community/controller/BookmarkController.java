package porori.backend.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import porori.backend.community.config.BaseResponse;
import porori.backend.community.dto.PostResDto;
import porori.backend.community.service.BookmarkService;

@RequiredArgsConstructor
@RequestMapping("/api/communities")
@RestController
@Tag(name = "Bookmark API", description = "북마크 API")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    //북마크 생성하기
    @Operation(summary = "북마크 생성", description = "해당 postId에 북마크를 생성합니다.")
    @PostMapping("/bookmark/{postId}")
    public BaseResponse<PostResDto.PostContentRes> createBookmark(@RequestHeader("Authorization") String token, @PathVariable("postId") Long postId){
        return new BaseResponse<>(bookmarkService.createBookmark(token, postId));
    }
}
