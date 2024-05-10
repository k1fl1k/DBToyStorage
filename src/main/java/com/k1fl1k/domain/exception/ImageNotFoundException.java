package com.k1fl1k.domain.exception;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException() {
        super("Помилка при збереженні файлу картинки");
    }
}
