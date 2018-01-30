package com.example.kevinnguyen.mario;

import android.graphics.Rect;

/**
 * Created by autumnlee1 on 5/15/16.
 */
public class Fireball extends Entity {
    //friendly = fireball belongs to player
    boolean friendly;
    int width, height;
    public Fireball(int width, int height, int x, int y, boolean moveRight, boolean playerBall){
        this.width = width;
        this.height = height;
        friendly = playerBall;
        fall = true;
        runSpeed = 12;
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
        vy = 0;
        area = new Rect(x1, y1, x2, y2);
    }
    public boolean update(Player p, Terrain t[][], Entity e[], int levelX, int levelY){
        //return true if fireball collides with wall or enemy or goes off screen
        //return false if no collision
        for (int y = 0; y < levelY; y++){
            for (int x = 0; x < levelX; x++){
                if (t[x][y]!=null && (t[x][y].getClass() == Brick.class ||
                        t[x][y].getClass() == ItemBlock.class ||
                        t[x][y].getClass() == ItemBlockEmpty.class ||
                        t[x][y].getClass() == Ground.class)){
                    //top
                    if (y2 < t[x][y].y2 && y2 > t[x][y].y1 && (x1+x2)/2 >= t[x][y].x1 && (x1+x2)/2 <= t[x][y].x2){
                        vy = -10;
                    }
                    //left and right
                    if (x2 >= t[x][y].x1 && x2 <= t[x][y].x2 && (y1+y2)/2 >= t[x][y].y1 && (y1+y2)/2 <= t[x][y].y2 && vx >= 0 ||
                            x1 <= t[x][y].x2 && x1 >= t[x][y].x1 && (y1+y2)/2 >= t[x][y].y1 && (y1+y2)/2 <= t[x][y].y2 && vx <= 0 ||
                            x1 < 0 || x1 > width){
                        return true;
                    }
                }
            }
        }
        if(Rect.intersects(p.area, area) && !friendly && p.takeDamage){
            p.powerUp--;
            p.takeDamage = false;
            return true;
        }
        for (int x = 0; x < MarioView.EnemyCount; x++){
            if (e[x] != null && friendly && Rect.intersects(area, e[x].area)){
                e[x] = null;
                p.score += 200;
                return true;
            }
        }
        vy++;
        Entity.updateCoordinates(this);
        return false;
    }
}
