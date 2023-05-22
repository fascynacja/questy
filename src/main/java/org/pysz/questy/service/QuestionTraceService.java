package org.pysz.questy.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.Questions;
import org.pysz.questy.persistnce.QuestionTrace;
import org.pysz.questy.persistnce.QuestionTraceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionTraceService {

    QuestionTraceRepository questionTraceRepository;

    public void propagateQuestions(Questions questions) {
        questions.getItems().stream()
                .map(this::toTrace)
                .forEach(trace -> questionTraceRepository.save(trace));
    }

    private QuestionTrace toTrace(Questions.QuestionItem q) {
        return new QuestionTrace(q.getQuestionId(), q.getViewCount(), LocalDateTime.now());
    }
}