package com.example.hyeju.service;

import com.example.hyeju.controller.entity.Article;
import com.example.hyeju.dto.ArticleForm;
import com.example.hyeju.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service //서비스 객체 생성
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository; //게시글 리파지터리 객체 주입

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();//DTO->엔티티로 변환 후 article에 저장
        if (article.getId() != null) //article의 id가 있을 때
            return null;
        return articleRepository.save(article); //article을 DB에 저장
    }

    public Article update(Long id, ArticleForm dto) {


        //1. DTO -> 엔티티 변환
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());
        //2. 타깃 조회하기
        Article target = articleRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리하기
        if (target == null || article.getId() != id) {
            //400, 잘못된 요청 응답
            log.info("잘못된 요청! id: {},article: {}", id, article.toString());
            return null;

        }
        //4. 업데이트 및 정상 응답(200)하기
        target.patch(article);
        Article updated = articleRepository.save(target); //수정한 article 엔티티를 DB에 저장(기존 데이터에 새 데이터 넣음)
        return updated;
    }

    public Article delete(Long id) {
        //1.삭제 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        //2.잘못된 요청 처리하기
        if (target == null)
            return null;
        //3.대상 삭제하기
        articleRepository.delete(target);
        return target;

    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //1. dto 묶음 엔티티 묶음으로 변환하기
        List<Article>articleList = dtos.stream() //stream 문법
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
        //2. 엔티티 묶음을 DB에 저장하기
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        //3. 강제 예외 발생시키기
        articleRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패!"));
    //orElseThrow : 값이 존재하면 그 값을 반환하고 존재하지 않으면 전달값을 보낸 예외를 발생시킴.
        //Ille~ : 전달값이 없거나 유효하지 않는 경우
        //4. 결과 값 반환하기
        return articleList;
    }
}