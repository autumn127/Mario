package com.example.kevinnguyen.mario;

import android.graphics.Rect;

/**
 * Created by Kevin Nguyen on 5/17/2016.
 */
public class Goal extends Terrain{
    public Goal(){
    }

    public boolean update(Player p){
        //return true if player reaches goal
        super.update(p.bx);
        if (Rect.intersects(p.area, area)){
            p.vx = 0;
            p.vy = 0;
            return true;
        }
        else{
            return false;
        }
    }
}
