package org.pysz.questy.rest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.Questions;
import org.pysz.questy.model.htmlgrid.ColumnDefinition;
import org.pysz.questy.persistnce.QuestionTrace;
import org.pysz.questy.service.QuestionInfoService;
import org.pysz.questy.service.QuestionService;
import org.pysz.questy.service.QuestionTraceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
@AllArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService service;
    private final QuestionTraceService questionTraceService;
    private final QuestionInfoService questionInfoService;

    @GetMapping("/questions/propagate")
    public @ResponseBody String propagate() {

        Questions questions = service.questions();
        log.info("Manually triggered propagating questions " + questions);
        questionTraceService.propagateQuestions(questions);
        return questions.toString();
    }

    @GetMapping("/questions")
    public @ResponseBody Iterable<QuestionTrace> all() {
        return questionTraceService.all();
    }


    public Questions getQuestions() {
        return null;
    }

    @GetMapping("/questions/headers")
    public List<ColumnDefinition> tableHeaders() {
        log.debug("Retrieving headers for questions table");
        Map<String, String> titles = questionInfoService.titlesByIds();
        List<ColumnDefinition> headerDefinitions = new ArrayList<>();
        headerDefinitions.add(new ColumnDefinition("date", "date", "date", true));

        titles.forEach((id, title) -> {
            ColumnDefinition columnDefinition = new ColumnDefinition(id, title, title, false);
            headerDefinitions.add(columnDefinition);
        });
        return headerDefinitions.reversed();

    }

    @GetMapping("/questions/data")
    public List<Map<String, String>> tableData() {
        log.debug("Retrieving data for questions table");
        return questionTraceService.getTrendViewCountForQuestionIdAndForDates2();
    }
}
