package com.example.kevinnguyen.mario;

import android.graphics.Rect;

/**
 * Created by Kevin Nguyen on 5/4/2016.
 */
public class Player extends Entity{
    int width, height;
    int bx, px;
    int powerUp;
    int jumpHeight;
    int takeDamageCount;
    int score;
    int lives;
    boolean clamping;
    boolean takeDamage;

    public Player(int width, int height) {
        lives = 3;
        score = 0;
        fall = true;
        faceRight = true;
        takeDamage = true;
        takeDamageCount = 0;
        runSpeed = 4;
        powerUp = 0;
        jumpHeight = Terrain.cellsize/4;
        this.width = width;
        this.height = height;
        vx = 0;
        area = new Rect(x1, y1, x2, y2);
    }

    public void setVelocity(float x, float y, boolean b, Fireball fb[]) {
        //button held down
        if (b) {
            if (x >= width/4 - width/6 && x <= width/4 - width/18 && y >= height*4/5 - width/9 && y <= height*4/5 + width/18) {
                if (vx == 0) {
                    faceRight = false;
                    vx = -runSpeed;
                }
            } else if (x <= width/4 + width/6 && x >= width/4 + width/18 && y >= height*4/5 - width/9 && y <= height*4/5 + width/18) {
                if (vx == 0) {
                    faceRight = true;
                    vx = runSpeed;
                }
            } else if (x >= width*9/10 - width/13 && x <= width*9/10 + width/13 && y >= height*3/4 - width/13 && y <= height*3/4 + width/13) {
                //a button
                if (vy == 0 && !fall) {
                    vy = -jumpHeight;
                    fall = true;
                }
            } else if (x >= width*7/10 - width/13 && x <= width*7/10 + width/13 && y >= height*17/20 - width/13 && y <= height*17/20 + width/13) {
                //b button
                if (vx > 0 && !fall) {
                    vx = 2*runSpeed;
                }
                else if (vx < 0 && !fall) {
                    vx = -2*runSpeed;
                }
                if (powerUp == 2){
                    //fire fireball
                    //player can only have two fireballs
                    for (int i = 0; i < 2; i++){
                        if (fb[i] == null){
                            if (faceRight){
                                fb[i] = new Fireball(width, height, x2, (y1+y2)/2, true, true);
                                break;
                            }
                            else{
                                fb[i] = new Fireball(width, height, x1, (y1+y2)/2, false, true);
                                break;
                            }

                        }
                    }
                }
            } else {
                vx = 0;
            }
        }
        //button released
        else {
            if ((x >= width/4 - width/6 && x <= width/4 - width/18 || x <= width/4 + width/6 && x >= width/4 + width/18) && y >= height*4/5 - width/9 && y <= height*4/5 + width/18){
                //release directional pad
                vx = 0;
            } else if (x >= width*7/10 - width/13 && x <= width*7/10 + width/13 && y >= height*17/20 - width/13 && y <= height*17/20 + width/13){
                //release b button
                if (vx > 0) {
                    vx = runSpeed;
                }
                else if (vx < 0) {
                    vx = -runSpeed;
                }
            }
        }
    }

    public void update(Terrain t[][], Entity e[], Fireball fb[], Hammer h[], Goal g, int levelX, int levelY, int levelNumber) {
        //walking off edge prevention
        if (x1 <= 0) {
            vx = 0;
            x1++;
            x2++;
        }
        if (x2 >= width) {
            vx = 0;
            x1--;
            x2--;
        }

        //detecting collision
        fall = true;
        for (int y = 0; y < levelY; y++){
            for (int x = 0; x < levelX; x++){
                if (t[x][y]!=null && (t[x][y].getClass() == Brick.class ||
                        t[x][y].getClass() == ItemBlock.class ||
                        t[x][y].getClass() == ItemBlockEmpty.class ||
                        t[x][y].getClass() == Ground.class)){
                    //check if player is already standing on block
                    if (y2 == t[x][y].y1 && (x1+x2)/2 >= t[x][y].x1 && (x1+x2)/2 <= t[x][y].x2){
                        fall = false;
                    }
                    //top
                    if (y2 < t[x][y].y2 && y2 > t[x][y].y1 && (x1+x2)/2 >= t[x][y].x1 && (x1+x2)/2 <= t[x][y].x2){
                        y2 = t[x][y].y1;
                        y1 = y2 - Terrain.cellsize;
                        vy = 0;
                        fall = false;
                    }
                    //bottom
                    else if (y1 < t[x][y].y2 && y1 > t[x][y].y1 && (x1+x2)/2 > t[x][y].x1 && (x1+x2)/2 < t[x][y].x2){
                        if (t[x][y].getClass() == Brick.class){
                            y1 = t[x][y].y2;
                            if (powerUp > 0) {
                                y2 = y1 + 2 * Terrain.cellsize;
                                t[x][y] = null;
                            }
                            else{
                                y2 = y1 + Terrain.cellsize;
                            }
                            vy = 0;
                        }
                        else if (t[x][y].getClass() == ItemBlock.class){
                            ItemBlock.spawnItem(t, x, y, this);
                            y1 = t[x][y].y2;
                            if (powerUp == 0){
                                y2 = y1 + Terrain.cellsize;
                            }
                            else{
                                y2 = y1 + 2*Terrain.cellsize;
                            }
                            vy = 0;
                        }
                        else{
                            y1 = t[x][y].y2;
                            if (powerUp == 0){
                                y2 = y1 + Terrain.cellsize;
                            }
                            else{
                                y2 = y1 + 2*Terrain.cellsize;
                            }
                            vy = 0;
                        }
                    }
                    //left
                    if (x2 >= t[x][y].x1 && x2 <= t[x][y].x2 && (y1+y2)/2 >= t[x][y].y1 && (y1+y2)/2 <= t[x][y].y2 && vx >= 0){
                        if (clamping){
                            int distance = x2 - t[x][y].x1;
                            for (int v = 0; v < levelY; v++){
                                for (int u = 0; u < levelX; u++){
                                    if (t[u][v] != null){
                                        t[u][v].x1 += distance;
                                        t[u][v].x2 += distance;
                                    }
                                }
                            }
                            for (int v = 0; v < MarioView.EnemyCount; v++){
                               if (e[v] != null){
                                   e[v].x1 += distance;
                                   e[v].x2 += distance;
                                   e[v].center += distance;
                               }
                            }
                            for (int v = 0; v < MarioView.FireballCount; v++){
                                if (fb[v] != null){
                                    fb[v].x1 += distance;
                                    fb[v].x2 += distance;
                                }
                            }
                            g.x1 += distance;
                            g.x2 += distance;
                        }
                        else{
                            x2 = t[x][y].x1;
                            x1 = x2 - Terrain.cellsize;
                        }
                        vx = 0;
                    }
                    //right
                    else if (x1 <= t[x][y].x2 && x1 >= t[x][y].x1 && (y1+y2)/2 >= t[x][y].y1 && (y1+y2)/2 <= t[x][y].y2 && vx <= 0){
                        if (clamping){
                            int distance = t[x][y].x2 - x1;
                            for (int v = 0; v < levelY; v++){
                                for (int u = 0; u < levelX; u++){
                                    if (t[u][v] != null){
                                        t[u][v].x1 -= distance;
                                        t[u][v].x2 -= distance;
                                    }
                                }
                            }
                            for (int v = 0; v < MarioView.EnemyCount; v++){
                                if (e[v] != null){
                                    e[v].x1 -= distance;
                                    e[v].x2 -= distance;
                                    e[v].center -= distance;
                                }
                            }
                            for (int v = 0; v < MarioView.FireballCount; v++){
                                if (fb[v] != null){
                                    fb[v].x1 -= distance;
                                    fb[v].x2 -= distance;
                                }
                            }
                            g.x1 -= distance;
                            g.x2 -= distance;
                        }
                        else{
                            x1 = t[x][y].x2;
                            x2 = x1 + Terrain.cellsize;
                        }
                        vx = 0;
                    }
                }
                if(t[x][y]!=null && t[x][y].getClass() == RedMushroom.class){
                    if (Rect.intersects(area, t[x][y].area)){
                        t[x][y] = null;
                        if (powerUp == 0){
                            powerUp = 1;
                            score+=1000;
                        }
                    }
                }
                else if (t[x][y]!=null && t[x][y].getClass() == FireFlower.class){
                    if (Rect.intersects(area, t[x][y].area)){
                        t[x][y] = null;
                        powerUp = 2;
                        score+=1000;
                    }
                }
                else if (t[x][y]!=null && t[x][y].getClass() == GreenMushroom.class){
                    if (Rect.intersects(area, t[x][y].area)){
                        t[x][y] = null;
                        score+=1000;
                        lives++;
                    }
                }
            }
        }
        if (fall){
            vy++;
        }
        if (!takeDamage){
            takeDamageCount++;
            //time to take damage again
            if (takeDamageCount == 150){
                takeDamageCount = 0;
                takeDamage = true;
            }
        }

        //update y coordinates
        y1 += vy;
        y2 += vy;

        px = vx;
        bx = vx;
        //clamping
        if (t[0][levelY-1].x1 >= 0 && x1+x2 <= width || t[levelX-1][levelY-1].x2 <= width && x1+x2 >= width){
            clamping = false;
            bx = 0;
            x1+=px;
            x2+=px;
        }
        else{
            clamping = true;
            x1 = width/2 - Terrain.cellsize/2;
            x2 = width/2 + Terrain.cellsize/2;
            for (int i = 0; i < MarioView.EnemyCount; i++){
                if (e[i] != null){
                    e[i].x1 -= vx;
                    e[i].x2 -= vx;
                    e[i].center -= vx;
                }
            }
            for (int i = 0; i < MarioView.FireballCount; i++){
                if (fb[i] != null){
                    fb[i].x1 -= vx;
                    fb[i].x2 -= vx;
                }
            }
        }

        //lose life, restart level
        if (powerUp < 0){
            lives--;
            powerUp = 0;
            takeDamage = true;
            //if all lives lost, restart game
            if (lives == 0){
                lives = 3;
                MarioView.levelNumber = 0;
                score = 0;
            }
            Terrain.loadLevel(t, levelX, levelY, this, g);
            Entity.loadEnemies(e);
            for (int x = 0; x < MarioView.FireballCount; x++){
                fb[x] = null;
            }
            for (int x = 0; x < MarioView.HammerCount; x++){
                h[x] = null;
            }
        }
        else if (powerUp == 0){
            y1 = y2 - Terrain.cellsize;
        }
        else{
            y1 = y2 - 2*Terrain.cellsize;
        }
        area.set(x1, y1, x2, y2);
    }
}