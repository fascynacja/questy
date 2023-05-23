

package org.pysz.questy.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.Questions;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@AllArgsConstructor
@Slf4j
public class QuestionPoller {

    public static final int MILLISEC_PER_HOUR = 3600000;
    private final QuestionService questionService;
    private final QuestionTraceService questionTraceService;
    private final List<String> questionIds;

    @Scheduled(fixedRate = MILLISEC_PER_HOUR)
    public void reportCurrentTime() {
        Questions questions = questionService.questions(questionIds);
        questionTraceService.propagateQuestions(questions);
        log.info("The questions from api for ids {} are: {}", questionIds, questions.getItems());
    }
}