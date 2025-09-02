package com.example.hyeju.api;

import com.example.hyeju.controller.entity.Article;
import com.example.hyeju.dto.ArticleForm;
import com.example.hyeju.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired //생성 객체를 가져와 연결(객체 주입)
    private ArticleService articleService; //서비스 객체 주입

    //GET(데이터 조회)
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index(); //데이터는 리파지터리,articleRepository.findAll(); 로 DB에서 조회한 결과 반환
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    //POST(데이터 생성)
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) { //create() 메서드 정의
        Article created = articleService.create(dto); //dto를 엔티티로 변환해  created변수에 저장 ->서비스로 게시글 생성
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();//생성하면 정상,실패하면 오류 응답
    }

    //PATCH(데이터 수정)
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                          @RequestBody ArticleForm dto) {
        Article updated = articleService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    //DELETE(데이터 삭제)
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> Delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){ //articleForm을 List로 묶은 dtos
        List<Article> createdList = articleService.createArticles(dtos);
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
