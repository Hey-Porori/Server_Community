package porori.backend.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.community.domain.Post;
import porori.backend.community.domain.PostTag;
import porori.backend.community.domain.Tag;
import porori.backend.community.repository.PostTagRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostTagService {
    private final PostTagRepository postTagRepository;
    public List<PostTag> savePostTag(Post post, List<Tag> tagList){
        List<PostTag> deletePostTags = postTagRepository.findAllByPostId(post);
        postTagRepository.deleteAll(deletePostTags);

        List<PostTag> postTagList = new ArrayList<>();
        for(Tag tag: tagList){
            PostTag postTag = PostTag.builder()
                    .postId(post)
                    .tagId(tag)
                    .build();
            postTagList.add(postTag);
        }
        return postTagList;
    }
}
