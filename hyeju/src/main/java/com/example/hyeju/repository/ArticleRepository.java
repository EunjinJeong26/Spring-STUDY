package com.example.hyeju.repository;

import com.example.hyeju.controller.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Override
    ArrayList<Article> findAll();
//JPA에서 제공하는 인터페이스
    //id의 타입이 Long이므로 Long입력

}
