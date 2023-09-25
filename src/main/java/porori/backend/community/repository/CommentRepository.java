package porori.backend.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import porori.backend.community.domain.Comment;
import porori.backend.community.domain.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostIdOrderByCreatedAt(Post post);

    Long countByPostIdAndUserId(Post postId, Long userId);
}
