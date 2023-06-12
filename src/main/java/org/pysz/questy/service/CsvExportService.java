package org.pysz.questy.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.pysz.questy.persistnce.QuestionTrace;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;

@Service
@Slf4j
@AllArgsConstructor
public class CsvExportService {

    public void writeEmployeesToCsv(Writer writer, Iterable<QuestionTrace> all) {
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("questionId", "timestamp", "viewCount");
            for (QuestionTrace question : all) {
                csvPrinter.printRecord(question.getQuestionId(), question.getTimestamp(), question.getViewCount());
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }
}