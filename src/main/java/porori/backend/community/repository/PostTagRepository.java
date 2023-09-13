package porori.backend.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import porori.backend.community.domain.Post;
import porori.backend.community.domain.PostTag;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    List<PostTag> findAllByPostId(Post postId);
}
