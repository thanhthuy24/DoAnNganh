package com.htt.ecourse.service;

import com.htt.ecourse.dtos.LikeDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Commentlike;
import com.htt.ecourse.responses.LikeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface LikeService {
    Commentlike createLike(LikeDTO likeDTO) throws DataNotFoundException;
    Page<LikeResponse> getLikeByCommentId(Long commentId, PageRequest pageRequest) throws DataNotFoundException;
    Long countLike(Long commentId);
}
