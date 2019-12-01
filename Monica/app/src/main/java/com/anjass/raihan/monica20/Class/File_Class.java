package com.anjass.raihan.monica20.Class;

public class File_Class {
    private String textView;
    private int imageResource;

    public File_Class(String textView, int imageResource) {
        this.textView = textView;
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getTextView() {
        return textView;
    }
    //    Return whether or not there is an image
}
