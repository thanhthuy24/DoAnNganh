package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Choice;
import com.htt.ecourse.pojo.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    List<Choice> findByQuestionIn(List<Question> questions);
}
