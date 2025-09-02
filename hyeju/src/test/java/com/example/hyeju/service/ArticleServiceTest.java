package com.example.hyeju.service;

import com.example.hyeju.controller.entity.Article;
import com.example.hyeju.dto.ArticleForm;

import org.junit.jupiter.api.Test; //test 패키지 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*; //앞으로 사용할 수 있는 패키지 임포트

@SpringBootTest //해당 클래스르 스프링 부트와 연동해 테스트
class ArticleServiceTest {
    @Autowired
    ArticleService articleService; //articleService 객체 주입

    @Test //해당 메서드가 테스트 코드임을 선언
    void index() {
        //1.예상 데이터
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333"); //예상 데이터 객체로 저장
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c)); //a,b,c 합치기
        //2.실제 데이터
        List<Article> articles = articleService.index();
        //3.비교 및 검증
        assertEquals(expected.toString(),articles.toString()); //각각을 문자열로 변환하여 비교
    }

    @Test
    void show_성공_존재하는_id_입력() {
        //1.예상 데이터
        Long id = 1L; //id가 1인 데이터를 조회했다고 가정
        Article expected = new Article(id,"가가가가","1111"); //예상 데이터 저장
        //2.실제 데이터
        Article article = articleService.show(id); //실제 데이터 저장
        //3.비교 및 검증
        assertEquals(expected.toString(),article.toString());
    }
    @Test
    void show_실패_존재하지_않는_id_입력() {
        //1.예상 데이터
        Long id =-1L;
        Article expected = null;
        //2.실제 데이터
        Article article = articleService.show(id); //조회되는 것이 없기 때문에 null값이 도출
        //3.비교 및 검증
        assertEquals(expected , article);
    }

    @Test
    @Transactional
    void create_성공_title과_content만_있는_dto_입력() {
        //1.예상 데이터
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(title,content,null); //id는 자동으로 생성
        Article expected = new Article(4L, title, content);
        //2.실제 데이터
        Article article = articleService.create(dto);
        //3.비교 및 검증
        assertEquals(expected.toString(),article.toString());
    }
    @Test
    @Transactional
    void create_실패_id가_포함된_dto_입력() {
        //1.예상 데이터
        Long id = 4L;
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(title,content,id);//id는 자동으로 생성
        Article expected = null;
        //2.실제 데이터
        Article article = articleService.create(dto);
        //3.비교 및 검증
        assertEquals(expected,article); //null은 toString 메서드를 호출할 수 없음

    }

    @Test
    @Transactional
    void update_성공1() {
        Long id = 1L;
        String title = "가가가가123";
        String content = "1111123";
        ArticleForm dto = new ArticleForm(title,content,id);
        Article expected = new Article(1L,"가가가가123","1111123");
        Article article = articleService.update(id,dto);
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    @Transactional
    void update_성공2() {
        Long id = 1L;
        String title = "가가가가123";
        String content = null;
        ArticleForm dto = new ArticleForm(title,content,id);
        Article expected = new Article(1L,"가가가가123","1111");
        Article article = articleService.update(id,dto);
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    @Transactional
    void update_실패() {
        Long id = -1L;
        String title = "가가가가123";
        String content ="1111123";
        ArticleForm dto = new ArticleForm(title,content,id);
        Article expected = null;
        Article article = articleService.update(id,dto);
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void delete_성공() {
        Long id = 1L;
        Article expected = new Article(id,"가가가가","1111");
        Article article = articleService.delete(id);
        assertEquals(expected.toString(),article.toString());
    }
    @Test
    @Transactional
    void delete_실패() {
        Long id = -1L;;
        Article expected = null;
        Article article = articleService.delete(id);
        assertEquals(expected,article);
    }
}