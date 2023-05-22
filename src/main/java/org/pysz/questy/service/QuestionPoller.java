

package org.pysz.questy.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.Questions;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@AllArgsConstructor
@Slf4j
public class QuestionPoller {

    private final QuestionService questionService;
    private final QuestionTraceService questionTraceService;
    private final List<String> questionIds;

    @Scheduled(fixedRate = 3600000)
    public void reportCurrentTime() {
        Questions questions = questionService.questions(questionIds);
        questionTraceService.propagateQuestions(questions);
        log.info("The questions from api for ids {} are: {}", questionIds, questions.getItems());
    }
}