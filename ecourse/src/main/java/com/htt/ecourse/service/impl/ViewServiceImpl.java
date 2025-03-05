package com.htt.ecourse.service.impl;

import com.google.api.Http;
import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.pojo.View;
import com.htt.ecourse.repository.CourseRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.repository.ViewRepository;
import com.htt.ecourse.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ViewServiceImpl implements ViewService {
    private final ViewRepository viewRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public View createView(Long courseId) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.isEmpty()) {
            return null;
        }
        User user = userRepository.getUserByUsername(username);

        View existingView = viewRepository.findByUserIdAndCourseId(user.getId(), courseId);
        if (existingView != null) {
            existingView.setCourse(existingCourse);
            existingView.setUser(user);
            existingView.setViewCount(existingView.getViewCount() + 1);

            viewRepository.save(existingView);
            return existingView;
        }

        View view = View.builder()
                .createdDate(new Date())
                .course(existingCourse)
                .user(user)
                .viewCount(1L)
                .build();

        viewRepository.save(view);
        return view;
    }
}
