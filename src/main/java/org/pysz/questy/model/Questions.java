package org.pysz.questy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Questions {
    List<QuestionItem> items;

    @Data
    public static class QuestionItem {
        @JsonProperty("view_count")
        int viewCount;

        @JsonProperty("question_id")
        String questionId;
    }
}


