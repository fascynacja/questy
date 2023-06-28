package org.pysz.questy.persistnce;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class QuestionInfo {

    public QuestionInfo(String questionId, String title) {
        this.questionId = questionId;
        this.title = title;
    }

    public QuestionInfo() {
    }

    @Id
    private String questionId;
    private String title;


}