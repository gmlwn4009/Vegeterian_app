package com.example.vegeproject.search;

public class RecyclerViewItem {
    public String prdlstNm;
    public String imgUrl;

    public RecyclerViewItem() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public RecyclerViewItem(String prdlstNm, String imgUrl) {
        this.prdlstNm = prdlstNm;
        this.imgUrl = imgUrl;
    }

    public String getPrdlstNm() {
        return prdlstNm;
    }

    public void setPrdlstNm(String prdlstNm) {
        this.prdlstNm = prdlstNm;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "prdlstNm='" + prdlstNm + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
