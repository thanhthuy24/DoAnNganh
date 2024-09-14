package com.htt.ecourse.service;

import com.htt.ecourse.dtos.ChoiceDTO;
import com.htt.ecourse.pojo.Choice;

public interface ChoiceService {
    Choice createChoice(ChoiceDTO choiceDTO);
    Choice updateChoice(Long choiceId, ChoiceDTO choiceDTO);
}
