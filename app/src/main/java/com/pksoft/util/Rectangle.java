package com.pksoft.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Rectangle implements Parcelable {

    Point p1;
    int width;
    int height;

    public Rectangle(Point p1, int width, int height) {
        this.p1 = p1;
        this.width = width;
        this.height = height;
    }

    protected Rectangle(Parcel in) {
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<Rectangle> CREATOR = new Creator<Rectangle>() {
        @Override
        public Rectangle createFromParcel(Parcel in) {
            return new Rectangle(in);
        }

        @Override
        public Rectangle[] newArray(int size) {
            return new Rectangle[size];
        }
    };

    enum NEIGHBOUR {
        NEIGHBOUR_Y, NEIGHBOUR_X, NEIGHBOUR_NONE
    }

    public NEIGHBOUR isNeighbour(Mask m){
        Rectangle r=m.toRectangle();
        boolean nr=this.p1.x+this.width == r.p1.x && in(r.p1.y, this.p1.y, this.p1.y+width);
        boolean nd=this.p1.y+this.height == r.p1.y && in(r.p1.x, this.p1.x, this.p1.x+height);
        if(nr)
            return NEIGHBOUR.NEIGHBOUR_X;
        else if(nd)
            return NEIGHBOUR.NEIGHBOUR_Y;
        else
            return NEIGHBOUR.NEIGHBOUR_NONE;
    }

    protected boolean in(int val, int from, int to){
        return from<=val&&to>=val;
    }


    public void extend(Mask m){
        if(p1==null)
            p1=m.points[0][0];
        if(isNeighbour(m) == NEIGHBOUR.NEIGHBOUR_X)
            width+=m.MASK_LENGTH_X;
        if(isNeighbour(m) == NEIGHBOUR.NEIGHBOUR_Y)
            height+=m.MASK_LENGTH_Y;
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
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

    @Override
    public String  toString() {
        return "{" +
                "p1:" + p1.x + "," + p1.y+
                ", width:" + width +
                ", height:" + height +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(toString());
    }
}
