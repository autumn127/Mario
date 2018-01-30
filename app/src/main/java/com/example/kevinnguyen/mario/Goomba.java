package com.example.kevinnguyen.mario;

import android.graphics.Rect;

/**
 * Created by Kevin Nguyen on 5/14/2016.
 */
public class Goomba extends Entity{
    public Goomba(int x, int y){
        faceRight = false;
        runSpeed = 2;
        x1 = x*Terrain.cellsize;
        y1 = y*Terrain.cellsize;
        x2 = x1 + Terrain.cellsize;
        y2 = y1 + Terrain.cellsize;
        vx = -runSpeed;
        area = new Rect(x1, y1, x2, y2);
    }

    public static boolean update(Terrain t[][], int levelX, int levelY, Entity g, Player p){
        //return true if goomba is killed
        g.fall = true;
        for (int y = 0; y < levelY; y++){
            for (int x = 0; x < levelX; x++){
                if (t[x][y]!=null && (t[x][y].getClass() == Brick.class ||
                        t[x][y].getClass() == ItemBlock.class ||
                        t[x][y].getClass() == ItemBlockEmpty.class ||
                        t[x][y].getClass() == Ground.class)){
                    //check if entity is already standing on block
                    if (g.y2 == t[x][y].y1 && (g.x1+g.x2)/2 >= t[x][y].x1 && (g.x1+g.x2)/2 <= t[x][y].x2){
                        g.fall = false;
                    }
                    //top
                    if (g.y2 < t[x][y].y2 && g.y2 > t[x][y].y1 && (g.x1+g.x2)/2 >= t[x][y].x1 && (g.x1+g.x2)/2 <= t[x][y].x2){
                        g.y2 = t[x][y].y1;
                        g.y1 = g.y2 - Terrain.cellsize;
                        g.vy = 0;
                        g.fall = false;
                    }
                    //left
                    if (g.x2 >= t[x][y].x1 && g.x2 <= t[x][y].x2 && (g.y1+g.y2)/2 >= t[x][y].y1 && (g.y1+g.y2)/2 <= t[x][y].y2 && g.vx >= 0){
                        g.x2 = t[x][y].x1;
                        g.x1 = g.x2 - Terrain.cellsize;
                        g.faceRight = false;
                        g.vx = -g.runSpeed;
                    }
                    //right
                    else if (g.x1 <= t[x][y].x2 && g.x1 >= t[x][y].x1 && (g.y1+g.y2)/2 >= t[x][y].y1 && (g.y1+g.y2)/2 <= t[x][y].y2 && g.vx <= 0){
                        g.x1 = t[x][y].x2;
                        g.x2 = g.x1 + Terrain.cellsize;
                        g.faceRight = true;
                        g.vx = g.runSpeed;
                    }
                }
            }
        }
        if (Rect.intersects(g.area, p.area)){
            if (p.y2 < g.y2 && p.vy > 0){
                p.score+=200;
                return true;
            }
            else{
                if (p.takeDamage) {
                    p.powerUp--;
                    p.takeDamage = false;
                }
            }
        }
        if (g.fall){
            g.vy++;
        }
        Entity.updateCoordinates(g);
        return false;
    }
}
