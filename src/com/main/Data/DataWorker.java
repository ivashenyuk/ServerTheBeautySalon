package com.main.Data;

import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataWorker {
    private String nameWorker;
    private String kingOfServiceWorker;
    private String priceWorker;
    private int idButtonWorker;
    //private Image imgWorker;
    private String imgWorker;
    private static int tmp = 0;

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

    public String getImgWorker() {
        return imgWorker;
    }

    public DataWorker() {
        this.idButtonWorker = 1;
        if ((tmp % 2) == 0)
            this.imgWorker = getImage("img.jpg");
        else
            this.imgWorker = getImage("img2.jpg");
        tmp++;
        this.kingOfServiceWorker = "Масаж";
        this.nameWorker = "Івашенюк Юрій";
        this.priceWorker = "100$";
    }

    public DataWorker(String nameWorker, String kingOfServiceWorker, String priceWorker, int idButtonWorker, String imgWorker) {
        this.nameWorker = nameWorker;
        this.kingOfServiceWorker = kingOfServiceWorker;
        this.priceWorker = priceWorker;
        this.idButtonWorker = idButtonWorker;
        this.imgWorker = imgWorker;
    }

    /*temporary function, delete her*/
    public static String getImage(String name) {
        try {
            String dirName = "E://" + name;
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
            BufferedImage img = ImageIO.read(new File(dirName));
            ImageIO.write(img, "jpg", baos);
            baos.flush();

            String base64String = Base64.encode(baos.toByteArray());
            baos.close();
            byte[] bytearray = Base64.decode(base64String);
            return new Gson().toJson(bytearray);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
