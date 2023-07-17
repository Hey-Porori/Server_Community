package porori.backend.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.community.domain.Post;
import porori.backend.community.domain.PostTag;
import porori.backend.community.domain.Tag;
import porori.backend.community.repository.PostTagRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostTagService {
    private final PostTagRepository postTagRepository;

    public void savePostTag(Post post, List<Tag> tagList){
        for(Tag tag: tagList){
            PostTag postTag = PostTag.builder()
                    .postId(post)
                    .tagId(tag)
                    .build();

            postTagRepository.save(postTag);
        }
    }
}
