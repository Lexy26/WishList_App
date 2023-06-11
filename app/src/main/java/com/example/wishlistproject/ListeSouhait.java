package com.example.wishlistproject;

public class ListeSouhait {
    public int iDList;
    public String etat, listName, imageList;

    public ListeSouhait(int iDList, String etat, String listName, String imageList) {
        this.iDList = iDList;
        this.etat = etat;
        this.listName = listName;
        this.imageList = imageList;
    }

    public ListeSouhait() {
    }

    public int getiDList() {
        return iDList;
    }

    public void setiDList(int iDList) {
        this.iDList = iDList;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getImageList() {
        return imageList;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }
}
