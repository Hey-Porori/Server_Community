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
import porori.backend.community.repository.PostRepository;

import javax.transaction.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;

    private final PostAttachService postAttachService;
    private final TagService tagService;
    private final PostTagService postTagService;


    public PostResDto.PostContentRes createPost(Long userId, PostReqDto.PostContentReq postContent) {

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
}
