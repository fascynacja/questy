package org.pysz.questy.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.QuestionStats;
import org.pysz.questy.model.Questions;
import org.pysz.questy.persistnce.QuestionTrace;
import org.pysz.questy.persistnce.QuestionTraceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionTraceService {

    private final QuestionTraceRepository questionTraceRepository;


    public void propagateQuestions(Questions questions) {
        LocalDateTime now = LocalDateTime.now();
        questions.getItems().stream()
                .map(q -> toTrace(q, now))
                .forEach(trace -> questionTraceRepository.save(trace));
    }

    public Iterable<QuestionTrace> all() {
        return questionTraceRepository.findAll();
    }


    private QuestionTrace toTrace(Questions.QuestionItem q, LocalDateTime now) {
        return new QuestionTrace(q.getQuestionId(), q.getViewCount(), now);
    }


    private TreeMap<Date, Map<String, Integer>> getViewCountForQuestionIdAndForDates() {
        LocalDate tresHoldDate = LocalDate.of(2023, 6, 13);
        List<QuestionStats> stats = questionTraceRepository.stats(tresHoldDate);
        return stats.stream()
                .collect(groupingBy(
                        QuestionStats::getCommonDate,
                        TreeMap::new,
                        toMap(QuestionStats::getQuestionId, s -> s.getViewCount().intValue())));
    }

    public TreeMap<Date, Map<String, Integer>> getTrendViewCountForQuestionIdAndForDates() {
        TreeMap<Date, Map<String, Integer>> viewCountForQuestionIdAndForDates = getViewCountForQuestionIdAndForDates();
        Map<String, Integer> minValues = new HashMap<>(viewCountForQuestionIdAndForDates.firstEntry().getValue());
        viewCountForQuestionIdAndForDates.values().forEach(v -> {
            v.replaceAll((s, integer) -> integer - minValues.getOrDefault(s, 0));
        });
        return viewCountForQuestionIdAndForDates;
    }

}