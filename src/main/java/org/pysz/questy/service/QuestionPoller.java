

package org.pysz.questy.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.Questions;
import org.springframework.scheduling.annotation.Scheduled;

@AllArgsConstructor
@Slf4j
public class QuestionPoller {

    public static final int MILLISEC_PER_12_HOUR = 12*3600000;
    private final QuestionService questionService;
    private final QuestionTraceService questionTraceService;

    @Scheduled(fixedRate = MILLISEC_PER_12_HOUR)
    public void reportCurrentTime() {
        Questions questions = questionService.questions();
        questionTraceService.propagateQuestions(questions);
        log.info("The questions from api   are: {}", questions.getItems());
    }
}