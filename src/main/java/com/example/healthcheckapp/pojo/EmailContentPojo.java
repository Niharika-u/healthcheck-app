package com.example.healthcheckapp.pojo;

/**
 * Created By MMT6540 on 03 Apr, 2018
 */
public class EmailContentPojo {
    private String text;

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [text = "+text+"]";
    }
}