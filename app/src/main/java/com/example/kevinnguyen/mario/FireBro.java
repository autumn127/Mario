package com.example.kevinnguyen.mario;

import android.graphics.Rect;
import java.util.Random;

/**
 * Created by Kevin Nguyen on 5/16/2016.
 */
public class FireBro extends Entity{
    public FireBro(int x, int y){
        runSpeed = 4;
        x1 = x*Terrain.cellsize;
        y1 = (y+1)*Terrain.cellsize;
        x2 = x1 + Terrain.cellsize;
        y2 = y1 + 2*Terrain.cellsize;
        area = new Rect(x1, y1, x2, y2);
        vx = runSpeed;
        center = x1;
    }

    public static void throwFireball(Fireball f[], int width, int height, Entity fb, boolean moveRight){
        int fireballSpawn = new Random().nextInt(100);
        //FireBro can have two fireballs
        if (fireballSpawn == 1){
            //start loop at two because first two are reserved for player fireballs
            for (int i = 2; i < 4; i++){
                if (f[i] == null){
                    f[i] = new Fireball(width, height, fb.x1, (fb.y1+fb.y2)/2, moveRight, false);
                    break;
                }
            }
        }
    }

    public static boolean update(Terrain t[][], int levelX, int levelY, Entity fb, Player p){
        //return true if FireBro is killed
        fb.fall = true;
        for (int y = 0; y < levelY; y++){
            for (int x = 0; x < levelX; x++){
                if (t[x][y]!=null && (t[x][y].getClass() == Brick.class ||
                        t[x][y].getClass() == ItemBlock.class ||
                        t[x][y].getClass() == ItemBlockEmpty.class ||
                        t[x][y].getClass() == Ground.class)){
                    //check if entity is already standing on block
                    if (fb.y2 == t[x][y].y1 && (fb.x1+fb.x2)/2 >= t[x][y].x1 && (fb.x1+fb.x2)/2 <= t[x][y].x2){
                        fb.fall = false;
                    }
                    //top
                    if (fb.y2 < t[x][y].y2 && fb.y2 > t[x][y].y1 && (fb.x1+fb.x2)/2 >= t[x][y].x1 && (fb.x1+fb.x2)/2 <= t[x][y].x2){
                        fb.y2 = t[x][y].y1;
                        fb.y1 = fb.y2 - Terrain.cellsize;
                        fb.vy = 0;
                        fb.fall = false;
                    }
                }
            }
        }

        //turns around depending on which side player is on
        if (p.x1 > fb.x1){
            fb.faceRight = true;
        }
        else{
            fb.faceRight = false;
        }

        //FireBro moves back and forth
        if (fb.x1 >= fb.center + Terrain.cellsize && fb.vx > 0 ||
                fb.x1 <= fb.center - Terrain.cellsize && fb.vx < 0){
            fb.vx = -fb.vx;
        }

        if (Rect.intersects(fb.area, p.area)){
            if (p.y2 < fb.y2 && p.vy > 0){
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
        if (fb.fall){
            return true;
        }
        Entity.updateCoordinates(fb);
        return false;
    }

}
