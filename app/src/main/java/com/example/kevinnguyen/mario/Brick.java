package com.example.kevinnguyen.mario;

/**
 * Created by autumnlee1 on 5/7/16.
 */
public class Brick extends Terrain{
    public Brick(int x, int y){
        x1 = x * cellsize;
        y1 = y * cellsize;
        x2 = x1 + cellsize;
        y2 = y1 + cellsize;
        area.set(x1, y1, x2, y2);
    }
}

