package com.pksoft.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;

import com.pksoft.activities.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class Image {
    ImageBitmap imageBitmap;
    List<Rectangle> rectangles;
    Bitmap processedBitmap;


    public Image(Uri uri) throws FileNotFoundException {
        File file=new File(uri.getPath());
        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        imageBitmap=new ImageBitmap(pixels, width, height);
        processedBitmap=Bitmap.createBitmap(width, height, bitmap.getConfig());
    }

    public Image(Resources resources, int id) throws FileNotFoundException {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, id);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        imageBitmap=new ImageBitmap(pixels, width, height);
        processedBitmap=Bitmap.createBitmap(width, height, bitmap.getConfig());
    }

    public Image(ImageBitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public Iterator<Mask> maskIterator(int maskSize, ImageBitmap imageBitmap) {
        return new MaskIterator(maskSize, imageBitmap);
    }

    public void process() {
            /*
            greyscale
             */
            double[] greyscale = Arrays.stream(imageBitmap.getPixels()).mapToDouble((x) -> {
                Color c = Color.valueOf(x);
                return ((c.red() + c.green() + c.blue()) / 3);
            }).toArray();
            /*
            segment
             */
            double avg = Arrays.stream(greyscale).average().getAsDouble();
            int[] segmented = Arrays.stream(greyscale).mapToInt((x) -> x > avg ? 1 : 0).toArray();

            rectangles = new ArrayList<>();

            for (Iterator<Mask> it = maskIterator(3,new ImageBitmap(segmented,imageBitmap.getWidth(), imageBitmap.getHeight())); it.hasNext(); ) {
                Mask mask = it.next();
                if (mask.avg() <= 0.25) {
                    boolean added = false;
                    for (Rectangle rectangle : rectangles) {
                        if (Arrays.asList(Rectangle.NEIGHBOUR.NEIGHBOUR_X, Rectangle.NEIGHBOUR.NEIGHBOUR_Y).contains(rectangle.isNeighbour(mask)) ) {
                            rectangle.extend(mask);
                            added = true;
                            break;
                        }
                    }
                    if(!added) {
                        rectangles.add(mask.toRectangle());
                    }
                }
            }
        try {
            ImageBitmap _imageBitmap=new ImageBitmap(Arrays.stream(segmented).map((x)->Color.rgb(x,x,x)).toArray(),  imageBitmap.getWidth(), imageBitmap.getHeight());
           /*
            for (Rectangle r:rectangles) {
                drawRectangle(_imageBitmap,r);
            }
            
            */
            save(_imageBitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void drawRectangle(ImageBitmap imageBitmap, Rectangle r){
        for(int i=0;i<r.getWidth();i++){
            for(int j=0;j<r.getHeight();j++){
                imageBitmap.setPixelValue(i+r.getP1().getX(), j+r.getP1().getY(), Color.GREEN);
            }
        }
    }

    public void save(ImageBitmap imageBitmap) throws FileNotFoundException {
        processedBitmap.setPixels(imageBitmap.pixels, 0, imageBitmap.getWidth(), 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight());
        File processedFile = MainActivity.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        processedBitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(processedFile));
    }

    public ImageBitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(ImageBitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    public void setRectangles(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }
}
