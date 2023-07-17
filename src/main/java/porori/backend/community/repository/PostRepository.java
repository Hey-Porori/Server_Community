package porori.backend.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import porori.backend.community.domain.Post;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
