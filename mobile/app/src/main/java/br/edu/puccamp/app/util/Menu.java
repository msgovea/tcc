package br.edu.puccamp.app.util;

/**
 * Created by Mateus on 27/08/2017.
 */

public class Menu {
    private int id;
    private String img;
    private String optionTitle;
    private String optionSubTitle;


    public Menu(String option_title, String option_sub_title) {
        this.optionTitle = option_title;
        this.optionSubTitle = option_sub_title;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }

    public String getOptionSubTitle() {
        return optionSubTitle;
    }

    public void setOptionSubTitle(String optionSubTitle) {
        this.optionSubTitle = optionSubTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
