package org.pysz.questy.persistnce;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface QuestionInfoRepository extends CrudRepository<QuestionInfo, String> {

    List<QuestionInfo> findAll();
}