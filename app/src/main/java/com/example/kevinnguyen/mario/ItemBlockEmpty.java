package com.example.kevinnguyen.mario;

/**
 * Created by Kevin Nguyen on 5/12/2016.
 */
public class ItemBlockEmpty extends Terrain{
    public ItemBlockEmpty(int x, int y){
        x1 = x * cellsize;
        y1 = y * cellsize;
        x2 = x1 + cellsize;
        y2 = y1 + cellsize;
        area.set(x1, y1, x2, y2);
    }
}
