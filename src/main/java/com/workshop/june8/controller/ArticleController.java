package com.workshop.june8.controller;

import com.workshop.june8.controller.model.Article;
import com.workshop.june8.controller.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api")
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public ArticleController( ArticleService articleService){
        this.articleService = articleService;
    }

    @GetMapping(value = "/article/getArticleById/{id}", produces = "application/json")
    public ResponseEntity<Article> getArticleById(@PathVariable final long id) {

        Article article = articleService.findById(id).orElse(null);

        return ResponseEntity.ok(article) ;
    }

    @GetMapping(value = "/article/getAllOddArticles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Article>> getAllOddArticles() {

        Iterable<Article> articles = articleService.findAllOdArticles();

        return ResponseEntity.ok(articles) ;
    }

    @PostMapping(value = "/article/addArticle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> addArticle( @RequestBody final Article articleIn) {

        Article articleOut = articleService.save(articleIn);

        return ResponseEntity.ok(articleOut) ;
    }
}
