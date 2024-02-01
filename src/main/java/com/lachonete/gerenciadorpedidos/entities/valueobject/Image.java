package com.lachonete.gerenciadorpedidos.entities.valueobject;

public class Image {
    public Image(String urlPath) {
        this.urlPath = urlPath;
    }

    public String urlPath;

    @Override
    public String toString() {
        return urlPath;
    }
}
