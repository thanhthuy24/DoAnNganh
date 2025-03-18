package com.htt.ecourse.service;

import com.htt.ecourse.dtos.LanguageToolResponseDTO;

import java.util.List;

public interface SpellCheckService {
    List<LanguageToolResponseDTO> checkSpelling(String text, String language);
}
