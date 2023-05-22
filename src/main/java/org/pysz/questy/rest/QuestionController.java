package org.pysz.questy.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.Questions;
import org.pysz.questy.service.QuestionService;
import org.pysz.questy.service.QuestionTraceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService service;
    private final QuestionTraceService questionTraceService;

    // temporary static list just for preprod testing
    private final List<String> questionIds = List.of("76251895", "76180420");

    @GetMapping("/question")
    public @ResponseBody String question() {

        Questions questions = service.questions(questionIds);
        log.info("Manually triggered propagating questions " + questions);
        questionTraceService.propagateQuestions(questions);
        return questions.toString();
    }
}
