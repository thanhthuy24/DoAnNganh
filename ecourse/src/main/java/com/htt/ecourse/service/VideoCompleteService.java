package com.htt.ecourse.service;

import com.htt.ecourse.dtos.VideoCompleteDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Videocompleted;

public interface VideoCompleteService {
    Videocompleted createVideocompleted(VideoCompleteDTO videoCompleteDTO) throws DataNotFoundException;
}
