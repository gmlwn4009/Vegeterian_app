package com.example.vegeproject.news_and_guide;

public class guide_item {

    private String title, subtitle, content;
    private int photo1, photo2;

    private boolean expandable;

    public guide_item(){};

    public guide_item(String title, String subtitle, String content, int photo1, int photo2) {
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.expandable = false;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getPhoto1() {
        return photo1;
    }

    public void setPhoto1(int photo1) {
        this.photo1 = photo1;
    }

    public int getPhoto2() {
        return photo2;
    }

    public void setPhoto2(int photo2) {
        this.photo2 = photo2;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}
