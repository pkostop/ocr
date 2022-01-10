package com.pksoft.activities;

import android.graphics.BitmapFactory;
import android.net.Uri;

import com.pksoft.util.Image;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Test
    public void testIndexing1(){

        assertEquals(0, index(0,0,9));
    }

    @Test
    public void testIndexing2(){
        assertEquals(13, index(4,1,9));
    }

    @Test
    public void testIndexing3(){
        assertEquals(23, index(5,2,9));
    }

    @Test
    public void testIndexing4(){
        assertEquals(48, index(3,5,9));
    }

    @Test
    public void testIndexing5(){
        assertEquals(80, index(8,8,9));
    }

    @Mock
    Uri uri;
    @Test
    public void testFileSystem() throws IOException {
        File f=new File("C:\\Users\\admin\\AndroidStudioProjects\\MainActivity\\app\\src\\test\\java\\com\\pksoft\\activities\\sample-numbers.jpg");
        when(uri.getPath()).thenReturn(f.getPath());
        System.out.println(uri.getPath());
    }

    protected int index(int i,int j, int width){
        return i+j*width;
    }
}