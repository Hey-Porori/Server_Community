package porori.backend.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.community.domain.Tag;
import porori.backend.community.repository.TagRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> saveTag(List<String> tagNameList){
        List<Tag> tagList = new ArrayList<>();
        for(String tagName: tagNameList){
            Tag tag = tagRepository.findByName(tagName).orElseGet(()->tagRepository.save(Tag.builder().name(tagName).build()));

            tagList.add(tag);
        }
        return tagList;
    }
}
