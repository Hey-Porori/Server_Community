package porori.backend.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.community.domain.Post;
import porori.backend.community.domain.PostAttach;
import porori.backend.community.repository.PostAttachRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostAttachService {
    private final PostAttachRepository postAttachRepository;

    public List<PostAttach> saveAttach(Post post, List<String> imageNameList){
        List<PostAttach> imageList = new ArrayList<>();
        List<PostAttach> deletePostAttachs = postAttachRepository.findAllByPostId(post);
        postAttachRepository.deleteAll(deletePostAttachs);

        //첨부파일 저장
        for(String imageName: imageNameList){
            PostAttach postAttach = PostAttach.builder()
                    .postId(post)
                    .imageName(imageName)
                    .build();
            imageList.add(postAttach);
        }
        return imageList;
    }
}
