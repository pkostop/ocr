package com.pksoft.util;

import java.util.Iterator;

public class MaskIterator implements Iterator<Mask> {
    int margin=0;
    int i,j;
    int maskSize=0;
    ImageBitmap imageBitmap;
    public MaskIterator(int maskSize, ImageBitmap imageBitmap) {
        this.maskSize=maskSize;
        margin=Math.floorDiv(maskSize,2);
        i=margin;
        j=margin;
        this.imageBitmap=imageBitmap;
    }

    @Override
    public boolean hasNext() {
        return i+margin<imageBitmap.getWidth()&&j+margin<imageBitmap.getHeight();
    }

    @Override
    public Mask next() {
        Mask m= new com.pksoft.util.Mask(imageBitmap, new Point(i,j, imageBitmap.getPixelValue(i,j)));
        i+=maskSize;
        if(i>imageBitmap.getWidth()){
            i=margin;
            j+=maskSize;
        }
        return m;
    }
}
