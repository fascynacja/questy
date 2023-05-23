package org.pysz.questy.persistnce;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class QuestionTrace {

    public QuestionTrace(String questionId, int viewCount, LocalDateTime timestamp) {
        this.questionId = questionId;
        this.viewCount = viewCount;
        this.timestamp = timestamp;
    }

    public QuestionTrace() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String questionId;

    private int viewCount;

    private LocalDateTime timestamp;
}