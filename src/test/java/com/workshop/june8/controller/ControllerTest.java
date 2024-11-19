package com.workshop.june8.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workshop.june8.controller.model.Article;
import com.workshop.june8.controller.repositories.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.IF_MODIFIED_SINCE;
import static org.springframework.http.HttpHeaders.LAST_MODIFIED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ControllerTest {

    @Autowired
    private ArticleController articleController;

    // Geen InjectMocks: we gebruiken Spring autowiring
    @MockBean
    private ArticleRepository articleRepository;

    private MockMvc mockMvc;
    private Article article;

    @BeforeEach
    public void setup () {
        this.mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
        article = new Article(1, "E2-bike", 22);
        System.out.println(article);
    }

    @Test
    public void testGetArticleById() throws Exception{

        when(articleRepository.findById(article.getId())).thenReturn(Optional.of(article));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/article/getArticleById/{id}", article.getId()))
                .andDo(print())
                .andExpect(jsonPath("$.stock").value(22))
                .andExpect(status().isOk());

    }

    @Test
    public void testAddArticle() throws Exception{

        Article article2 = new Article(1, "E-bike", 12);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(article);

        when(articleRepository.save(ArgumentMatchers.any(Article.class))).thenReturn(article2);

        this.mockMvc.perform(post("/api/article/addArticle")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON) //
                .accept(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(jsonPath("$.stock").value(12))
                .andExpect(status().isOk());

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
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].name").value(articles.get(0).getName()))
                .andExpect(jsonPath("$.[0].stock").value(articles.get(0).getStock()))
                .andExpect(status().isOk());
    }
}
