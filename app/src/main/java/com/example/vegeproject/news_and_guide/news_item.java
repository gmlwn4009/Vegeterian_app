package com.example.vegeproject.news_and_guide;

public class news_item {
    String title;//제목
    String company;//언론사
    String link;//링크
    String pubDate;//발행일자
    String photo;//사진

    public news_item(){};

    public news_item(String title, String company, String link, String pubDate, String photo) {
        this.title = title;
        this.link = link;
        this.company = company;
        this.pubDate = pubDate;
        this.photo = photo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCompany(String company) { this.company = company; }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLink() { return link; }

    public String getTitle() {
        return title;
    }

    public String getCompany() { return company; }

    public String getPubDate() {
        return pubDate;
    }

    public String getPhoto() {
        return photo;
    }
}