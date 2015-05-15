package com.example.sjayaram.gridimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by sjayaram on 5/12/2015.
 */
public class SearchFilter implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageSize);
        dest.writeString(this.colorFilter);
        dest.writeString(this.imageType);
        dest.writeString(this.siteFilter);
    }

    private SearchFilter(Parcel in) {
        this.imageSize = in.readString();
        this.colorFilter = in.readString();
        this.imageType = in.readString();
        this.siteFilter = in.readString();
    }

    public static final Parcelable.Creator<SearchFilter> CREATOR = new Parcelable.Creator<SearchFilter>() {
        public SearchFilter createFromParcel(Parcel source) {
            return new SearchFilter(source);
        }

        public SearchFilter[] newArray(int size) {
            return new SearchFilter[size];
        }
    };
}
