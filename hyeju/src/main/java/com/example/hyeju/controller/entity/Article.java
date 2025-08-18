package com.example.hyeju.controller.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 id 자동 생성
    private Long id; //article에서 기본키는 id
    @Column
    private String title;
    @Column
    private String content;

//    public Long getId() {
//        return id;} 위 롬복을 사용해 어노테이션 Getter추가했으므로 생략

    public void patch(Article article){
        if (article.title != null)
            this.title = article.title;
        if (article.content != null)
            this.content = article.content;
    }
}

