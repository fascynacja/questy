package org.pysz.questy.persistnce;

import org.pysz.questy.model.QuestionDailyVisits;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;


public interface QuestionTraceRepository extends CrudRepository<QuestionTrace, Integer> {

    @Query(" select new org.pysz.questy.model.QuestionDailyVisits(questionId, avg(viewCount) , date(timestamp) ) " +
            " from QuestionTrace " +
           " where date(timestamp) > :tresHoldDate " +
            " group by date(timestamp), questionId")
    List<QuestionDailyVisits> dailyAverageVisits(@RequestParam(name = "tresHoldDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tresHoldDate);


}