package com.example.kevinnguyen.mario;

import android.graphics.Rect;

/**
 * Created by Kevin Nguyen on 5/17/2016.
 */
public class Hammer extends Entity {
    int width, height;
    public Hammer(int width, int height, int x, int y, boolean moveRight, boolean faceRight){
        this.width = width;
        this.height = height;
        this.faceRight = faceRight;
        runSpeed = 8;
        x1 = x;
        y1 = y;
        x2 = x1 + Terrain.cellsize/2;
        y2 = y1 + Terrain.cellsize/2;
        if (moveRight){
            vx = runSpeed;
        }
        else{
            vx = -runSpeed;
        }
        vy = -Terrain.cellsize/4;
        area = new Rect(x1, y1, x2, y2);
    }

    public boolean update(Player p, int height){
        //return true if collision or travels off screen
        //hammers do not interact with blocks and will pass through them
        if (x1 < 0 || x1 > width || y1 > height*27/40){
            return true;
        }
        if(Rect.intersects(p.area, area) && p.takeDamage){
            p.powerUp--;
            p.takeDamage = false;
            return true;
        }
        vy++;
        Entity.updateCoordinates(this);
        return false;
    }
}
