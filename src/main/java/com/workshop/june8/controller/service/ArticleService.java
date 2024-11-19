package com.workshop.june8.controller.service;

import com.workshop.june8.controller.model.Article;

import java.util.Optional;

public interface ArticleService {

    Optional<Article> findById(long id);
    Iterable<Article> findAll();
    Article save(Article article);
    Iterable<Article> findAllOdArticles();
}
