package com.codeup.springblog.models;
import javax.persistence.*;

@Entity
@Table(name="posts")

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 155)
    private String title;

    @Column(nullable = false, length = 155)
    private String body;

    public Post (long id, String title, String body){
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public Post(String title, String body){
        this.title = title;
        this.body = body;
    }

    public Post (){};

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
