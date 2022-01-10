package com.pksoft.util;

public class ImageBitmap {
    int[] pixels;
    int width;
    int height;

    public ImageBitmap(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public int getPixelValue(int i, int j) {
        return pixels[getElementIndex(i, j)];
    }

    public void setPixelValue(int i, int j, int value) {
        pixels[getElementIndex(i, j)]=value;
    }

    protected int getElementIndex(int i, int j) {
        return i+j*width;
    }

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
