package com.pksoft.util;

import android.graphics.Color;

import java.util.Arrays;

public class Mask {
    final int MASK_LENGTH_X=3;
    final int MASK_LENGTH_Y=3;
    Point[][] points=new Point[MASK_LENGTH_X][MASK_LENGTH_Y];


    public Mask(ImageBitmap imageBitmap, Point center){
        int rangex=Math.floorDiv(MASK_LENGTH_X , 2);
        int rangey=Math.floorDiv(MASK_LENGTH_Y , 2);
        Point startPoint=new Point(center.x-rangex, center.y-rangey, 0);
        for(int i=0;i<MASK_LENGTH_X;i++){
            for (int j = 0; j < MASK_LENGTH_Y; j++) {
                points[i][j]=new Point(startPoint.x+i,startPoint.y+j,imageBitmap.getPixelValue(i+startPoint.x,j+startPoint.y));
            }
        }
    }

    public double avg(){
        double avg=0;
        for (int i = 0; i < MASK_LENGTH_X; i++) {
            for (int j = 0; j < MASK_LENGTH_Y; j++) {
                avg+=points[i][j].value;
            }
        }
        return avg/(MASK_LENGTH_Y*MASK_LENGTH_X);
    }

    @Override
    public String toString() {
        return "Mask{"+this.points[0][0].x+","+this.points[0][0].y+","+this.points[this.MASK_LENGTH_X-1][this.MASK_LENGTH_Y-1].x+","+this.points[this.MASK_LENGTH_X-1][this.MASK_LENGTH_Y-1].y+"}";
    }

    public Rectangle toRectangle(){
        return new Rectangle(points[0][0], MASK_LENGTH_X, MASK_LENGTH_Y);
    }
}
