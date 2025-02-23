package com.htt.ecourse.service;

import com.htt.ecourse.pojo.Progress;

import java.util.Optional;

public interface ProgressService {
    float calculateProgress(Long courseId);
    Optional<Progress> getProgressByAdmin(Long userId, Long courseId);
    Boolean checkProgressForCertificate(Long userId, Long courseId);
    Optional<Progress> getProgressByUser(Long courseId);
}
