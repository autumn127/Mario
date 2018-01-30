package com.example.kevinnguyen.mario;

import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Kevin Nguyen on 5/17/2016.
 */
public class HammerBro extends Entity {
    public HammerBro(int x, int y){
        runSpeed = 4;
        x1 = x*Terrain.cellsize;
        y1 = (y+1)*Terrain.cellsize;
        x2 = x1 + Terrain.cellsize;
        y2 = y1 + 2*Terrain.cellsize;
        area = new Rect(x1, y1, x2, y2);
        vx = runSpeed;
        center = x1;
    }

    public static void throwHammer(Hammer h[], int width, int height, Entity hb, boolean moveRight){
        int hammerSpawn = new Random().nextInt(100);
        //HammerBro can have two hammers
        if (hammerSpawn == 1){
            for (int i = 0; i < MarioView.HammerCount; i++){
                if (h[i] == null){
                    h[i] = new Hammer(width, height, hb.x1, (hb.y1+hb.y2)/2, moveRight, hb.faceRight);
                    break;
                }
            }
        }
    }
    
    public static boolean update(Terrain t[][], int levelX, int levelY, Entity hb, Player p){
        //return true if HammerBro is killed
        hb.fall = true;
        for (int y = 0; y < levelY; y++){
            for (int x = 0; x < levelX; x++){
                if (t[x][y]!=null && (t[x][y].getClass() == Brick.class ||
                        t[x][y].getClass() == ItemBlock.class ||
                        t[x][y].getClass() == ItemBlockEmpty.class ||
                        t[x][y].getClass() == Ground.class)){
                    //check if entity is already standing on block
                    if (hb.y2 == t[x][y].y1 && (hb.x1+hb.x2)/2 >= t[x][y].x1 && (hb.x1+hb.x2)/2 <= t[x][y].x2){
                        hb.fall = false;
                    }
                    //top
                    if (hb.y2 < t[x][y].y2 && hb.y2 > t[x][y].y1 && (hb.x1+hb.x2)/2 >= t[x][y].x1 && (hb.x1+hb.x2)/2 <= t[x][y].x2){
                        hb.y2 = t[x][y].y1;
                        hb.y1 = hb.y2 - Terrain.cellsize;
                        hb.vy = 0;
                        hb.fall = false;
                    }
                }
            }
        }

        //turns around depending on which side player is on
        if (p.x1 > hb.x1){
            hb.faceRight = true;
        }
        else{
            hb.faceRight = false;
        }

        //HammerBro moves back and forth
        if (hb.x1 >= hb.center + Terrain.cellsize && hb.vx > 0 ||
                hb.x1 <= hb.center - Terrain.cellsize && hb.vx < 0){
            hb.vx = -hb.vx;
        }

        if (Rect.intersects(hb.area, p.area)){
            if (p.y2 < hb.y2 && p.vy > 0){
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

        //killed if he does not have floor to stand on
        if (hb.fall){
            return true;
        }
        Entity.updateCoordinates(hb);
        return false;
    }
}
