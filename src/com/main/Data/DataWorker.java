package com.main.Data;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataWorker {
    private String nameWorker;
    private String kingOfServiceWorker;
    private String priceWorker;
    private int idButtonWorker;
    private Image imgWorker;

    public String getNameWorker() {
        return nameWorker;
    }

    public String getKingOfServiceWorker() {
        return kingOfServiceWorker;
    }

    public String getPriceWorker() {
        return priceWorker;
    }

    public int getIdButtonWorker() {
        return idButtonWorker;
    }

    public Image getImgWorker() {
        return imgWorker;
    }

    public DataWorker() {
        this.idButtonWorker = 1;
        this.imgWorker = getImage("img.jpg");
        this.kingOfServiceWorker = "Масаж";
        this.nameWorker = "Івашенюк Юрій";
        this.priceWorker = "100$";
    }

    public DataWorker(String nameWorker, String kingOfServiceWorker, String priceWorker, int idButtonWorker, Image imgWorker) {
        this.nameWorker = nameWorker;
        this.kingOfServiceWorker = kingOfServiceWorker;
        this.priceWorker = priceWorker;
        this.idButtonWorker = idButtonWorker;
        this.imgWorker = imgWorker;
    }

    /*temporary function, delete her*/
    public static Image getImage(String name) {
        String filename = "E://" + name;
          Image  gras = Toolkit.getDefaultToolkit().getImage(filename);
        return gras;
    }
}
