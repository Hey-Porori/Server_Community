package porori.backend.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import porori.backend.community.config.BaseException;
import porori.backend.community.domain.Post;
import porori.backend.community.domain.Tag;
import porori.backend.community.dto.PostReqDto;
import porori.backend.community.dto.PostResDto;
import porori.backend.community.repository.PostRepository;

import javax.transaction.Transactional;

import java.util.List;

import static porori.backend.community.config.BaseResponseStatus.DATABASE_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;

    private final PostAttachService postAttachService;
    private final TagService tagService;
    private final PostTagService postTagService;


    public PostResDto.PostContentRes createPost(Long userId, PostReqDto.PostContentReq postContent) throws BaseException {
        //Post 생성
        Post postEntity = Post.builder()
                .title(postContent.getTitle())
                .content(postContent.getContent())
                .latitude(postContent.getLatitude())
                .longitude(postContent.getLongitude())
                .userId(userId)
                .build();
        try{
            //Post 저장
            Post post = postRepository.save(postEntity);

            //첨부파일 저장
            postAttachService.saveAttach(post, postContent.getImageNameList());

            //태그 저장
            List<Tag> tagList = tagService.saveTag(postContent.getTagList());

            //PostTag 저장
            postTagService.savePostTag(post, tagList);

            return new PostResDto.PostContentRes(post);
        } catch (Exception e){
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
