package org.pysz.questy.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Slf4j
@AllArgsConstructor
public class CsvExportService {
    private final QuestionTraceService questionTraceService;
    private final QuestionInfoService questionInfoService;

    public void csv(HttpServletResponse servletResponse) {

        TreeMap<Date, Map<String, Integer>> viewCountForQuestionIdAndForDates = questionTraceService.getTrendViewCountForQuestionIdAndForDates();
        List<String> orderedQuestionIds = viewCountForQuestionIdAndForDates.firstEntry().getValue().keySet().stream().sorted().toList();
        Map<String, String> titles = questionInfoService.titlesByIds();

        try (CSVPrinter csvPrinter = new CSVPrinter(servletResponse.getWriter(), CSVFormat.DEFAULT)) {
            csvPrinter.print("date");
            for (String orderedQuestionId : orderedQuestionIds) {
                csvPrinter.print(orderedQuestionId + " " + titles.get(orderedQuestionId));
            }
            csvPrinter.println();

            viewCountForQuestionIdAndForDates.forEach((date, questioinIdsMapping) -> {

                try {
                    csvPrinter.print(date);
                    for (String orderedQuestionId : orderedQuestionIds) {
                        csvPrinter.print(questioinIdsMapping.get(orderedQuestionId));
                    }
                    csvPrinter.println();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }


}