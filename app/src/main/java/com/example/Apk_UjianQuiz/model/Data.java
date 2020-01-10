
package com.example.Apk_UjianQuiz.model;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable, Parcelable
{

    @SerializedName("soal_quiz_android")
    @Expose
    private List<SoalQuizAndroid> soalQuizAndroid = null;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6490058661086225723L;

    protected Data(Parcel in) {
        in.readList(this.soalQuizAndroid, (SoalQuizAndroid.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param soalQuizAndroid
     */
    public Data(List<SoalQuizAndroid> soalQuizAndroid) {
        super();
        this.soalQuizAndroid = soalQuizAndroid;
    }

    public List<SoalQuizAndroid> getSoalQuizAndroid() {
        return soalQuizAndroid;
    }

    public void setSoalQuizAndroid(List<SoalQuizAndroid> soalQuizAndroid) {
        this.soalQuizAndroid = soalQuizAndroid;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(soalQuizAndroid);
    }

    public int describeContents() {
        return  0;
    }

}
