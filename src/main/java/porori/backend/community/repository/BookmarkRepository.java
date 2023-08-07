package porori.backend.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import porori.backend.community.domain.Bookmark;
import porori.backend.community.domain.Post;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByPostIdAndUserId(Post post, Long userId);
}
