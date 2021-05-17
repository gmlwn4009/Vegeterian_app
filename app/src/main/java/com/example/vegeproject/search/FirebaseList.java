package com.example.vegeproject.search;

public class FirebaseList {
    public String prdlstNm;
    public String barcode;
    public String allergy;

    public FirebaseList() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public FirebaseList(String prdlstNm, String barcode, String allergy) {
        this.prdlstNm = prdlstNm;
        this.barcode = barcode;
        this.allergy = allergy;
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

    @Override
    public String toString() {
        return "User{" +
                "prdlstNm='" + prdlstNm + '\'' +
                ", barcode='" + barcode + '\'' +
                ", allergy='" + allergy + '\'' +
                '}';
    }
}
