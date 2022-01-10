package com.pksoft.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.pksoft.util.Image;
import com.pksoft.util.ImageBitmap;
import com.pksoft.util.Mask;
import com.pksoft.util.MaskIterator;
import com.pksoft.util.Rectangle;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        int[] testImage=createTestImage();
        ImageBitmap imageBitmap=new ImageBitmap(testImage, 9,9);
        Image im=new Image(imageBitmap);
        im.process();
        List<Rectangle> rectangles=im.getRectangles();
        for (Rectangle r: rectangles) {
            System.out.println(r.toString());
        }

    }

    @Test
    public void testParseImage() throws FileNotFoundException {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Image im=new Image( appContext.getResources(), R.drawable.sample_numbers);
        im.process();
        List<Rectangle> rectangles=im.getRectangles();
        for (Rectangle r: rectangles) {
            System.out.println(r.toString());
        }

    }

    @Test
    public void testMaskIterator(){
        MaskIterator iterator=new MaskIterator(3, new ImageBitmap(createTestImage(), 9,9));
        while (iterator.hasNext()) {
            Mask m=iterator.next();
            System.out.println(m.toString());
        }
    }

    protected int[] createTestImage(){
        int width=9;
        int height=9;
        int[][] testImage=new int[width][height];
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                testImage[i][j]= Color.rgb(255,255,255);
            }
        }

        black(testImage, new Point(1,1));
        black(testImage, new Point(1,4));
        black(testImage, new Point(4,4));

        int[] flatTestImage=new int[width*height];
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                flatTestImage[i*height+j]=testImage[i][j];
            }
        }

        return flatTestImage;
    }

    protected void black(int[][] pixels, Point center){
        pixels[center.x-1][center.y-1]= Color.rgb(0,0,0);
        pixels[center.x][center.y-1]= Color.rgb(0,0,0);;
        pixels[center.x+1][center.y-1]= Color.rgb(0,0,0);;

        pixels[center.x-1][center.y]= Color.rgb(0,0,0);;
        pixels[center.x][center.y]= Color.rgb(0,0,0);;
        pixels[center.x+1][center.y]= Color.rgb(0,0,0);;

        pixels[center.x-1][center.y+1]= Color.rgb(0,0,0);;
        pixels[center.x][center.y+1]= Color.rgb(0,0,0);;
        pixels[center.x+1][center.y+1]= Color.rgb(0,0,0);;

    }

}