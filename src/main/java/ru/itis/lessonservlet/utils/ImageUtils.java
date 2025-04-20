package ru.itis.lessonservlet.utils;
import lombok.experimental.UtilityClass;

import java.util.Base64;

@UtilityClass
public class ImageUtils {

    public String encodeToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

}
