package com.htt.ecourse.service;

import com.htt.ecourse.dtos.CategoryDTO;
import com.htt.ecourse.dtos.TagDTO;
import com.htt.ecourse.pojo.Category;
import com.htt.ecourse.pojo.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();
    Tag getTagById(Long id);
    Tag createTag(TagDTO tagDTO);
}
