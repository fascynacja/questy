package org.pysz.questy.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.Questions;
import org.pysz.questy.persistnce.QuestionInfo;
import org.pysz.questy.persistnce.QuestionInfoRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionInfoService {

    QuestionInfoRepository repository;


    public void propagateQuestions(Questions questions) {
        questions.getItems().stream()
                .map(this::toInfo)
                .forEach(info -> repository.save(info));
    }

    public Map<String, String> titlesByIds() {
        return repository.findAll()
                .stream()
                .collect(Collectors.toMap(QuestionInfo::getQuestionId, QuestionInfo::getTitle));
    }

    private QuestionInfo toInfo(Questions.QuestionItem q) {
        return QuestionInfo.builder()
                .title(q.getTitle())
                .questionId(q.getQuestionId())
                .build();
    }

}