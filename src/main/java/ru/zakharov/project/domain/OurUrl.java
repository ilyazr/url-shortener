package ru.zakharov.project.domain;

import ru.zakharov.project.controllers.MainController;

import javax.persistence.*;

@Entity
@Table(name = "our_url")
public class OurUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "token")
    private String token;

    @Column(name = "long_url")
    private String longUrl;

    @Column(name = "visits")
    private int visits;

    public OurUrl() {
    }

    public OurUrl(String token, String longUrl) {
        this.token = token;
        this.longUrl = longUrl;
    }

    public String fullUrl() {
        return MainController.HOST+token;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String shortUrl) {
        this.token = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
