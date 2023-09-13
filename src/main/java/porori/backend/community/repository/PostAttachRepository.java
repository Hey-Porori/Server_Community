package porori.backend.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import porori.backend.community.domain.Post;
import porori.backend.community.domain.PostAttach;

import java.util.List;


@Repository
public interface PostAttachRepository extends JpaRepository<PostAttach, Long> {
    List<PostAttach> findAllByPostId(Post postId);
}
