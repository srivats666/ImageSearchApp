package com.example.sjayaram.gridimagesearch.models;

import java.io.Serializable;

/**
 * Created by sjayaram on 5/12/2015.
 */
public class SearchFilter implements Serializable{
    public String imageSize;
    public String colorFilter;
    public String imageType;
    public String siteFilter;

    public SearchFilter(){}

    public SearchFilter(String imageSize, String colorFilter, String imageType, String siteFilter){
        this.imageSize = imageSize;
        this.colorFilter = colorFilter;
        this.imageType = imageType;
        this.siteFilter = siteFilter;
    }
}
