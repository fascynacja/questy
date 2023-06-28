

package org.pysz.questy.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.Questions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class QuestionPoller {

    public static final int MILLISEC_PER_24_HOUR = 24 * 3600000;
    private final QuestionService questionService;
    private final QuestionTraceService questionTraceService;
    private final QuestionInfoService questionInfoService;

    @Scheduled(fixedRate = MILLISEC_PER_24_HOUR)
    public void reportCurrentTime() {
        Questions questions = questionService.questions();
        questionTraceService.propagateQuestions(questions);
        questionInfoService.propagateQuestions(questions);
        log.info("The questions from api are: {}", questions.getItems());
    }
}