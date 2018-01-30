package com.example.kevinnguyen.mario;

import android.graphics.Rect;

/**
 * Created by Kevin Nguyen on 5/4/2016.
 */
public class Entity {
    int x1, y1, x2, y2;
    int vx, vy;
    int runSpeed;
    int center;
    boolean fall;
    boolean faceRight;
    Rect area;
    public Entity(){

    }

    public static void loadEnemies(Entity e[]){
        //placing enemies in levels
        for (int x = 0; x < MarioView.EnemyCount; x++){
            e[x] = null;
        }
        switch (MarioView.levelNumber){
            case 0:
                e[0] = new Goomba(12, 7);
                e[1] = new HammerBro(10, 2);
                e[2] = new Goomba(17, 10);
                e[3] = new FireBro(18, 2);
                break;
            case 1:
                e[0] = new Goomba(7, 5);
                e[1] = new Goomba(11, 3);
                e[2] = new HammerBro(10, 8);
                break;
            case 2:
                e[0] = new HammerBro(12, 5);
                e[1] = new Goomba(9, 3);
                e[2] = new Goomba(11, 3);
                e[3] = new FireBro(18, 1);
                e[4] = new HammerBro(18, 4);
                break;
        }
    }

    public static void updateCoordinates(Entity e){
        e.x1+=e.vx;
        e.x2+=e.vx;
        e.y1+=e.vy;
        e.y2+=e.vy;
        e.area.set(e.x1, e.y1, e.x2, e.y2);
    }

}
