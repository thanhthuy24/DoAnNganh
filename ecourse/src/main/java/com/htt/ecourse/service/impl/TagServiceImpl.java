package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.CategoryDTO;
import com.htt.ecourse.dtos.TagDTO;
import com.htt.ecourse.pojo.Category;
import com.htt.ecourse.pojo.Tag;
import com.htt.ecourse.repository.CategoryRepository;
import com.htt.ecourse.repository.TagRepository;
import com.htt.ecourse.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Tag createTag(TagDTO tagDTO) {
        Tag newTag = Tag.builder()
                .name(tagDTO.getName())
                .build();
        return tagRepository.save(newTag);
    }
}
