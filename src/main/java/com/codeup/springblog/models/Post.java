package com.codeup.springblog.models;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="posts")

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Posts must have a title.")
    @Size(min = 3, message = "A title must be at least 3 characters.")
    @Column(nullable = false, length = 155)
    private String title;

    @NotBlank(message = "Posts must have a body.")
    @Column(nullable = false, length = 155)
    private String body;

    @Column(nullable = true, length = 255)
    private String filePath = "empty";



    public Post(String title, String body, User user, String filePath) {
        this.title = title;
        this.body = body;
        this.user = user;
        this.filePath = filePath;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post(long id, String title, String body, User user, String filePath) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.user = user;
        this.filePath = filePath;
    }

    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;
    }

    public Post (long id, String title, String body){
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public Post(String title, String body){
        this.title = title;
        this.body = body;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

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

    public User getUser() {
        return user;
    }

}
