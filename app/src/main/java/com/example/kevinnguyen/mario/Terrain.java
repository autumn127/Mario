package com.example.kevinnguyen.mario;

import android.graphics.Rect;

/**
 * Created by autumnlee1 on 5/6/16.
 */
public class Terrain {
    static int cellsize = 100;
    int x1, y1, x2, y2;
    Rect area = new Rect();
    public Terrain(){
    }

    static void loadLevel(Terrain t[][], int levelX, int levelY, Player p, Goal fp){
        //placing blocks
        //clear all previous blocks
        for (int y = 0; y < levelY; y++){
            for(int x= 0; x < levelX; x++){
                t[x][y] = null;
            }
        }

        //add in floor
        for (int x = 0; x < levelX; x++){
            t[x][11] = new Ground(x, 11);
        }

        switch(MarioView.levelNumber){
            case 0:
                t[5][8] = new ItemBlock(5, 8);
                t[10][8] = new ItemBlock(10, 8);
                for (int x = 8; x < 13; x++){
                    if (x != 10){
                        t[x][8] = new Brick(x, 8);
                    }
                }
                t[9][5] = new Brick(9, 5);
                t[10][5] = new ItemBlock(10, 5);
                t[11][5] = new Brick(11, 5);
                t[15][10] = new Brick(15, 10);
                for (int x = 17; x < 20; x++){
                    t[x][5] = new Brick(x, 5);
                }
                t[21][10] = new Brick(21, 10);
                break;

            case 1:
                for (int x = 2; x < 4; x++) {
                    t[x][8] = new ItemBlock(x, 8);
                }
                for (int x = 6; x < 8; x++) {
                    t[x][6] = new ItemBlock(x, 6);
                }
                for (int x = 10; x < 12; x++) {
                    t[x][4] = new ItemBlock(x, 4);
                }
                for (int y = 2; y < levelY - 1; y++) {
                    t[15][y] = new Brick(15, y);
                }
                t[17][2] = new Brick(17, 2);
                t[18][2] = new Brick(18, 2);
                t[16][5] = new Brick(16, 5);
                t[17][5] = new Brick(17, 5);
                t[17][8] = new Brick(17, 8);
                t[18][8] = new Brick(18, 8);
                for (int y = 0; y < levelY - 3; y++) {
                    t[19][y] = new Brick(19, y);
                }

                break;

            case 2:
                t[4][8] = new Brick(4, 8);
                t[5][8] = new ItemBlock(5, 8);
                t[6][8] = new Brick(6, 8);
                t[7][6] = new Brick(7, 6);
                //bottom mountain
                for(int x = 8; x < 12; x++){
                    t[x][4] = new Brick(x, (4));
                }
                for (int y = 10; y > 9; y--){
                    t[9][y] = new Brick(9, y);
                }
                for (int y = 10; y > 8; y--){
                    t[10][y] = new Brick(10, y);
                }
                for (int y = 10; y > 7; y--){
                    t[11][y] = new Brick(11, y);
                    t[12][y] = new Brick(12, y);
                    t[13][y] = new Brick(13, y);
                }
                for (int y = 10; y > 8; y--){
                    t[14][y] = new Brick(14, y);
                }
                for (int y = 10; y > 9; y--){
                    t[15][y] = new Brick(15, y);
                }

                for (int y = 1; y < 8; y+=3){
                    for (int x = 17; x < 20; x++){
                        t[x][y] = new Brick(x, y);
                    }
                }
                for (int x = 10; x < 21; x+=2){
                    t[x][1] = new Brick(x, 1);
                    t[x+1][1] = null;
                }
                break;
        }
        //position flagpole
        fp.x1 = 22 * cellsize;
        fp.x2 = fp.x1 + 2*cellsize;
        fp.y1 = 0;
        fp.y2 = 11 * cellsize;
        fp.area.set(fp.x1, fp.y1, fp.x2, fp.y2);

        //position player
        p.x1 = t[2][11].x1;
        p.x2 = p.x1 + Terrain.cellsize;
        p.y2 = t[2][11].y1;
        p.y1 = p.y2 - Terrain.cellsize;
    }

    public void update(int vx){
        x1+=-vx;
        x2+=-vx;
        area.set(x1, y1, x2, y2);
    }
}
