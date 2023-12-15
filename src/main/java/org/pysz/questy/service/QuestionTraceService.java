package org.pysz.questy.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pysz.questy.model.QuestionDailyVisits;
import org.pysz.questy.model.Questions;
import org.pysz.questy.persistnce.QuestionTrace;
import org.pysz.questy.persistnce.QuestionTraceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiFunction;

import static java.util.stream.Collectors.*;

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
        List<QuestionDailyVisits> stats = questionTraceRepository.dailyAverageVisits(tresHoldDate);
        return stats.stream()
                .collect(groupingBy(
                        QuestionDailyVisits::getCommonDate,
                        TreeMap::new,
                        toMap(QuestionDailyVisits::getQuestionId, s -> s.getViewCount().intValue())));
    }

    public TreeMap<Date, Map<String, Integer>> getTrendViewCountForQuestionIdAndForDates() {
        TreeMap<Date, Map<String, Integer>> viewCountForQuestionIdAndForDates = getViewCountForQuestionIdAndForDates();
        Map<String, Integer> minValues = new HashMap<>(viewCountForQuestionIdAndForDates.firstEntry().getValue());
        viewCountForQuestionIdAndForDates.values().forEach(v -> {
            v.replaceAll((s, integer) -> integer - minValues.getOrDefault(s, 0));
        });
        return viewCountForQuestionIdAndForDates;
    }

    public List<Map<String, String>> getTrendViewCountForQuestionIdAndForDates2() {
        LocalDate tresHoldDate = LocalDate.of(2024, 1,13);
        List<QuestionDailyVisits> dailyVisits = questionTraceRepository.dailyAverageVisits(tresHoldDate);
        BiFunction<? super Map<String, ArrayList<Integer>>, ? super QuestionDailyVisits, ? extends Map<String, ArrayList<Integer>>> bifun
                = (BiFunction<Map<String, ArrayList<Integer>>, QuestionDailyVisits, Map<String, ArrayList<Integer>>>) (initial, questionDailyVisits) ->
        {

            initial.computeIfAbsent(questionDailyVisits.getQuestionId(), k-> new ArrayList<>())
                    .add(questionDailyVisits.getViewCount().intValue());
            return initial;
        };

        TreeMap<Date, Map<String, String>> dailyVisitsGroupedByDate = dailyVisits.stream()

                .collect(groupingBy(
                        QuestionDailyVisits::getCommonDate,
                        TreeMap::new,
                        toMap(QuestionDailyVisits::getQuestionId, s -> s.getViewCount().intValue() + "")));


        List<Map<String, String>> rows = new ArrayList<>();
        dailyVisitsGroupedByDate.forEach((date, questionVisitsPerDay) ->
        {
            questionVisitsPerDay.put("date", date.toString());
            rows.add(questionVisitsPerDay);
        });

        return rows.reversed();

    }
}