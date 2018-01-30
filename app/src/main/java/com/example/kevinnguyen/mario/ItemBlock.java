package com.example.kevinnguyen.mario;

/**
 * Created by autumnlee1 on 5/7/16.
 */
public class ItemBlock extends Terrain {
    public ItemBlock(int x, int y) {
        x1 = x * cellsize;
        y1 = y * cellsize;
        x2 = x1 + cellsize;
        y2 = y1 + cellsize;
        area.set(x1, y1, x2, y2);
    }

    public static void spawnItem(Terrain t[][], int x, int y, Player p){
        int a = t[x][y].x1;
        int b = t[x][y].y1;
        t[x][y] = new ItemBlockEmpty(x, y);
        t[x][y].x1 = a;
        t[x][y].y1 = b;
        t[x][y].x2 = a + cellsize;
        t[x][y].y2 = b + cellsize;
        t[x][y].area.set(a, b, a+Terrain.cellsize, b+Terrain.cellsize);
        if (p.powerUp == 0){
            t[x][y-1] = new RedMushroom(x, y-1);
        }
        else if(p.powerUp == 1){
            t[x][y-1] = new FireFlower(x, y-1);
        }
        else {
            t[x][y-1] = new GreenMushroom(x, y-1);

        }
        t[x][y-1].y2 = t[x][y].y1;
        t[x][y-1].y1 = t[x][y-1].y2 - Terrain.cellsize;
        t[x][y-1].x2 = t[x][y].x2;
        t[x][y-1].x1 = t[x][y].x1;
    }
}
