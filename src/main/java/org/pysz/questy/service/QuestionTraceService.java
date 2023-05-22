package org.pysz.questy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.Questions;
import org.pysz.questy.persistnce.QuestionTrace;
import org.pysz.questy.persistnce.QuestionTraceRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.zip.GZIPInputStream;

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