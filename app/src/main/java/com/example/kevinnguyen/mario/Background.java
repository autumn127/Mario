package com.example.kevinnguyen.mario;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


/**
 * Created by autumnlee1 on 5/7/16.
 */
public class Background extends Terrain{
    Bitmap image;
    int x1, y1, x2, y2, vx; //vx = player velocity

    public Background(Bitmap reference) {
        image = reference;
        x1 = 0;
        x2 = MarioView.WIDTH;
        y1 = 0;
        y2 = MarioView.HEIGHT;
    }

    public void update(int bx) {
        vx = bx/4; // player velocity
        x1 += vx;
        x2 += vx;
        if(x1 < -MarioView.WIDTH){
           x1 = 0;
            x2 = MarioView.WIDTH;

        }
        if(x1 >= MarioView.WIDTH){
            x1 = 0;
            x2 = MarioView.WIDTH;
        }
}
    public void draw(int width, int height, Canvas canvas){
        Rect dst = new Rect();
        dst.set(0, 0, width, height * 27/40);
        Rect src = new Rect();
        src.set(x1, y1, x2, y2);
        canvas.drawBitmap(image, src, dst, null);
        if(x1<0){
            src.set(x1 + MarioView.WIDTH, y1, x2 + MarioView.WIDTH, y2);
            canvas.drawBitmap(image, src, dst, null);
        }
        if(x1>=0){
            src.set(x1 - MarioView.WIDTH, y1, x2 - MarioView.WIDTH, y2);
            canvas.drawBitmap(image, src, dst, null);
        }
    }
}

