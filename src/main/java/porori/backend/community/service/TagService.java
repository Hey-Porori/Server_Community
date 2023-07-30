package porori.backend.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.community.domain.Tag;
import porori.backend.community.repository.TagRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> saveTag(List<String> tagNameList){
        return tagNameList.stream()
                .map(tagName -> tagRepository.findByName(tagName).orElseGet(()->tagRepository.save(Tag.builder().name(tagName).build())))
                .collect(Collectors.toList());
    }
}
