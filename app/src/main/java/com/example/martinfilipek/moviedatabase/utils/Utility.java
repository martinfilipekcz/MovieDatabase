package com.example.martinfilipek.moviedatabase.utils;

import com.example.martinfilipek.moviedatabase.App;
import com.example.martinfilipek.moviedatabase.R;

import java.util.Locale;

/**
 * Created by Martin Filipek on 08.04.2018.
 */
public class Utility {

    public static String getLanguage() {
        String systemLanguage = Locale.getDefault().getLanguage();
        String language;

        switch (systemLanguage) {
            case "cs":
                language = "cs";
                break;
            default:
                language = "en";
                break;
        }

        return language;
    }

    public static String getTMDbImageUrl(String imgUrl) {
        return imgUrl != null ? App.getInstance().getString(R.string.TMDB_images_url).concat(imgUrl) : null;
    }
}
