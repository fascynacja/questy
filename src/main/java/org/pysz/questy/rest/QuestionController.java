package org.pysz.questy.rest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.Questions;
import org.pysz.questy.persistnce.QuestionTrace;
import org.pysz.questy.service.CsvExportService;
import org.pysz.questy.service.QuestionService;
import org.pysz.questy.service.QuestionTraceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService service;
    private final QuestionTraceService questionTraceService;
    private final CsvExportService csvExportService;

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


    @GetMapping("/questions/csv")
    public void allCSV(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"questy.csv\"");
        csvExportService.csv(servletResponse);


    }

}
