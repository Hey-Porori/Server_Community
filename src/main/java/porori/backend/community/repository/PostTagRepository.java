package porori.backend.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import porori.backend.community.domain.PostTag;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
}
