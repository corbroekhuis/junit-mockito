package com.workshop.june8.controller.repositories;

import com.workshop.june8.controller.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

//    Optional<Article> findById(Long aLong);


//    Iterable<Article> findAll();

    @Override
    <S extends Article> S save(S s);


//    <S extends Article> S save(S s);

}
