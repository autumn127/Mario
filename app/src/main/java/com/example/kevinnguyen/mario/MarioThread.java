package com.example.kevinnguyen.mario;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Kevin Nguyen on 5/4/2016.
 */
public class MarioThread extends Thread{
    MarioView mv;
    public MarioThread(MarioView sv) {
        this.mv=sv;
    }
    public void run() {
        SurfaceHolder sh = mv.getHolder();
        // Main game loop.
        while( !Thread.interrupted() ) {
            //You might want to do game specific processing in a method you call here
            Canvas c = sh.lockCanvas(null);
            try {
                synchronized(sh) {
                    mv.draw(c);
                }
            } catch (Exception e) {
            } finally {
                if ( c != null ) {
                    sh.unlockCanvasAndPost(c);
                } }
            // Set the frame rate by setting this delay
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // Thread was interrupted while sleeping.
                return; }
        } }
}
