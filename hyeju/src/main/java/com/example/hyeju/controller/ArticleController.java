package com.example.hyeju.controller;

import com.example.hyeju.controller.entity.Article;
import com.example.hyeju.dto.ArticleForm;
import com.example.hyeju.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller

public class ArticleController {

    @Autowired //스프링 부트에 있는 기능으로 스프링부트가 미리 생성해놓은 리파지터리 객체 주입(DI)
    private ArticleRepository articleRepository; //articleRepository 객체 선언
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }


    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) { //articleForm(DTO)의 form 객체를 가져옴
        log.info(form.toString());
        //System.out.println(form.toString()); // 위의 것을 출력하는 코드
        Article article = form.toEntity();//toEntity -> form객체를 엔티티 객체로 변환 역할
        log.info(article.toString());
        //System.out.println(article.toString()); //DTO가 엔티티로 잘 변환되는지 확인 출력
        Article saved = articleRepository.save(article); //article 엔티티를 저장해 saved 객체에 반환(윗 줄 article)
        log.info(saved.toString());
        //System.out.println(saved.toString()); //article이 DB(데이터베이스)에 잘 저장되는지 출력
        return "redirect:/articles/"+saved.getId();
    }
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id="+ id);
        //Optional<Article> articleEntity = articleRepository.findById(id); //id를 조회해 데이터 가져오기(반환형:Optional<Article>)
        Article articleEntity = articleRepository.findById(id).orElse(null);//해당 id값이 없으면 null 반환
        //Optional<T>은 "값이 있을 수도 있고, 없을 수도 있는 T"를 감싸는 Wrapper 클래스야.
        model.addAttribute("article", articleEntity);//  model.addAttribute("name" , Object value); 모델에 데이터 등록하기
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model){
        ArrayList<Article> articleEntityList = articleRepository.findAll();//모든 Article 데이터 가져오기
        //위 반환 타입이 Iterable 인데 작성한 타입은 List이기 떄문에 오류발생 ->다운 캐스팅해 문제 해결 또는 오버라이딩(LIst도 가능)
        model.addAttribute("articleList", articleEntityList);//모델에 데이터 등록하기

        return "articles/index";//뷰페이지 설정하기
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/edit";

    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) { //폼에서 전송한 데이터는 DTO(form)을 매개변수로 받음
        log.info(form.toString());
        //1.DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        //2.엔티티를 DB에 저장하기
        //2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //2-2. 기존 데이터 값을 갱신하기
        if (target != null){
            articleRepository.save(articleEntity); //엔티티를 DB에 저장(갱신)
        }
        //수정 결과 페이지로 리다이렉트 하기
        return "redirect:/articles/"+articleEntity.getId();
    }
}

