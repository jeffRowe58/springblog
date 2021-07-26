package com.codeup.springblog.models;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 140)
    private String title;
    @Column(nullable = false)
    private String description;
    @OneToOne
    private AdImage adImage;

    public Ad(){}

    public Ad(long id, String title, String description, AdImage adImage, User user, List<Category> categories) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.adImage = adImage;
        this.user = user;
        this.categories = categories;
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ads_categories",
            joinColumns = {@JoinColumn(name="ad_id")},
            inverseJoinColumns = {@JoinColumn(name="category_id")}
    )

    private List<Category> categories;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public AdImage getAdImage() {
        return adImage;
    }
    public void setAdImage(AdImage adImage) {
        this.adImage = adImage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}

