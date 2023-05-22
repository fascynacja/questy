package org.pysz.questy.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.Questions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionService {

    private static final String QUESTION_URL = "https://api.stackexchange.com/2.3/questions/%s?order=desc&sort=activity&site=stackoverflow";

    private final RestTemplate restTemplate;
    private final ResponseUnZipper responseUnZipper;

    public Questions questions(List<String> ids) {

        String url = String.format(QUESTION_URL, String.join(";", ids));
        log.debug("Retrieving questions from url: {} ", url);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(createHeaders()), byte[].class);
        log.info("Retrieved questions for ids: {} ", ids);
        return responseUnZipper.getEntity(response, Questions.class);
    }


    HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT_ENCODING, "gzip");
        return headers;
    }

}