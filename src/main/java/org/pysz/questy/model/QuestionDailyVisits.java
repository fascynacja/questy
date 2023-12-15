package org.pysz.questy.model;

import lombok.Data;

import java.util.Date;

@Data
public class QuestionDailyVisits {

    public QuestionDailyVisits(String questionId, Double viewCount, Date commonDate) {
        this.questionId = questionId;
        this.viewCount = viewCount;
        this.commonDate = commonDate;
    }

    public QuestionDailyVisits() {
    }

    private String questionId;

    private Double viewCount;

    private Date commonDate;
}