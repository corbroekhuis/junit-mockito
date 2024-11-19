package com.workshop.june8.controller.service;

import com.workshop.june8.controller.model.Article;
import com.workshop.june8.controller.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ArticleServiceImpl implements ArticleService{

    ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Optional<Article> findById(long id) {
        Optional<Article> article = articleRepository.findById( id);
        return article;
    }

    @Override
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save( article);
    }

    @Override
    public Iterable<Article> findAllOdArticles() {
        Iterable<Article> articles = articleRepository.findAll();
        return removeEvenIds( articles );
    }

    private Iterable<Article> removeEvenIds( Iterable<Article> articles){

        List<Article> unevenList = StreamSupport.stream(articles.spliterator(), false)
                .filter( s -> s.getId() % 2 != 0)
                .collect(Collectors.toList());

        return( (Iterable)unevenList);

    }
}
