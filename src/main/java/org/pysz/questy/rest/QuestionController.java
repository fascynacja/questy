package org.pysz.questy.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.Questions;
import org.pysz.questy.service.QuestionService;
import org.pysz.questy.service.QuestionTraceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService service;
    private final QuestionTraceService questionTraceService;
    @Value("${question.ids}")
    List<String> questionIds;
    @GetMapping("/question")
    public @ResponseBody String question() {

        Questions questions = service.questions(questionIds);
        log.info("Manually triggered propagating questions " + questions);
        questionTraceService.propagateQuestions(questions);
        return questions.toString();
    }
}
