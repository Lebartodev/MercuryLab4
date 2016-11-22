package com.lebartodev.mercurylab4.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by Александр on 22.11.2016.
 */

public class Article extends SugarRecord implements Parcelable{
    Long id;
    private String title;
    @Unique
    private String url;
    private String source;
    public Article(String title, String url, String sourceUrl) {
        this.title = title;
        this.url = url;
        this.source = sourceUrl;
    }

    public Article() {
    }

    protected Article(Parcel in) {
        title = in.readString();
        url = in.readString();
        source = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSourceUrl() {
        return source;
    }

    public void setSourceUrl(String sourceUrl) {
        this.source = sourceUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeString(source);
    }
}
