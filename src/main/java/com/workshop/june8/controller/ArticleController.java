package com.workshop.june8.controller;

import com.workshop.june8.controller.model.Article;
import com.workshop.june8.controller.repositories.ArticleRepository;
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

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleController( ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    @GetMapping(value = "/article/getArticleById/{id}", produces = "application/json")
    public ResponseEntity<Article> getArticleById(@PathVariable final long id) {

        Article article = articleRepository.findById(id).orElse(null);
        System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
        // Business logic to test, lower stock by one
        if(article.getStock() > 0){
            article.setStock( article.getStock() -1);
        }

        return ResponseEntity.ok(article) ;

    }

    @GetMapping(value = "/article/getAllOddArticles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Article>> getAllArticles() {

        Iterable<Article> articles = articleRepository.findAll();

        // Business logic to test
        // limit the list to three articles
        articles = removeEvenIds(articles);

        return ResponseEntity.ok(articles) ;

    }

    @PostMapping(value = "/article/addArticle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> addArticle( @RequestBody final Article articleIn) {

        System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ" + (articleIn == null ? "NULL": "OK"));

        // Business logic to test
        // Set initial stock to 1000
        articleIn.setStock(2000);
        Article articleOut = articleRepository.save(articleIn);

        return ResponseEntity.ok(articleOut) ;

    }

    private Iterable<Article> removeEvenIds( Iterable<Article> in){

        List<Article> unevenList = StreamSupport.stream(in.spliterator(), false)
                .filter( s -> s.getId() % 2 != 0)
                .collect(Collectors.toList());

        return( (Iterable)unevenList);

    }

}
