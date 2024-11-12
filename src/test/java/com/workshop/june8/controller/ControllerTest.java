package com.workshop.june8.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.june8.controller.ArticleController;
import com.workshop.june8.controller.model.Article;
import com.workshop.june8.controller.repositories.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
public class ControllerTest {

    @InjectMocks
    private ArticleController articleController;

    @Mock
    private ArticleRepository articleRepository;

    private MockMvc mockMvc;

    private Article article;

    @BeforeEach
    public void setup () {
        this.mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }

    @Test
    public void testGetArticleById() throws Exception{

        Article article1 = new Article(1, "E-bike", 12);

        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/article/getArticleById/" + article.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.stock").value(11))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testAddArticle() throws Exception{

        Article article = new Article(1, "E-bike", 12);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(article);

//        article.setStock(1000);
//        when(articleRepository.save(ArgumentMatchers.any(Article.class))).thenReturn(article);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/article/addArticle")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON) //
                .accept(MediaType.APPLICATION_JSON)
                .content(json))

                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.stock").value(1000))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetAllOddArticles() throws Exception{
        List<Article> articles = new ArrayList<>();

        articles.add(new Article(1, "E-bike", 12));
        articles.add(new Article(2, "Opoe fiets", 22));
        articles.add(new Article(3, "Tandem", 42));
        articles.add(new Article(4, "Gestolen", 2));
        articles.add(new Article(5, "Mtb", 23));
        articles.add(new Article(6, "Tour fiets", 5));

        when(articleRepository.findAll()).thenReturn(articles);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/article/getAllOddArticles"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].name").value(articles.get(0).getName()))
                .andExpect(jsonPath("$.[0].stock").value(articles.get(0).getStock()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
