package com.example.vegeproject.search;

import java.io.Serializable;

public class FirebaseData implements Serializable {
    public String prdlstNm;
    public String barcode;
    public String allergy;
    public String imgUrl;
    public String prdKind;

    public FirebaseData() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public FirebaseData(String prdlstNm, String barcode, String allergy, String imgUrl, String prdKind) {
        this.prdlstNm = prdlstNm;
        this.barcode = barcode;
        this.allergy = allergy;
        this.imgUrl = imgUrl;
        this.prdKind = prdKind;
    }

    public String getPrdlstNm() {
        return prdlstNm;
    }

    public void setPrdlstNm(String prdlstNm) {
        this.prdlstNm = prdlstNm;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPrdKind() {
        return prdKind;
    }

    public void setPrdKind(String prdKind) {
        this.prdKind = prdKind;
    }

    @Override
    public String toString() {
        return "User{" +
                "prdlstNm='" + prdlstNm + '\'' +
                ", barcode='" + barcode + '\'' +
                ", allergy='" + allergy + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", prdKind='" + prdKind + '\'' +
                '}';
    }
}
