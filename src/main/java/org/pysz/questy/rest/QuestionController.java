package org.pysz.questy.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.Questions;
import org.pysz.questy.persistnce.QuestionTrace;
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
    private final List<String> questionIds = List.of("76251895", "76180420", "64360895", "59105688","76315960");

    @GetMapping("/questions/propagate")
    public @ResponseBody String propagate() {

        Questions questions = service.questions(questionIds);
        log.info("Manually triggered propagating questions " + questions);
        questionTraceService.propagateQuestions(questions);
        return questions.toString();
    }

    @GetMapping("/questions")
    public @ResponseBody Iterable<QuestionTrace> all() {
        return questionTraceService.all();
    }

    /*@GetMapping("/question/{id}")
    public @ResponseBody String questionById() {

        Questions questions = service.questions(questionIds);
        log.info("Manually triggered propagating questions " + questions);
        questionTraceService.propagateQuestions(questions);
        return questions.toString();
    }*/
}
