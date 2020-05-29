package com.lkj.book.domain.posts;

import com.lkj.book.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //데이터베이스에 위임, MYSQL
    private Long id;

    @Column(length = 500, nullable = false) //기본 varchar(255)-> 500으로
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)    //타입 변경
    private String content;

    private String author;

    @Builder    //클래스에 쓸 경우 필드 값들로 이루어진 생성자 자동 생성, 생성자 상단 선언시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
