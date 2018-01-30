package com.example.kevinnguyen.mario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
*Created by Kevin Nguyen on 5/4/2016.
 */
// This is the "game engine".
public class MarioView extends SurfaceView
    implements SurfaceHolder.Callback{
    public MarioView(Context context){
        super(context);
        // Notify the SurfaceHolder that you'd like to receive
        // SurfaceHolder callbacks.
        getHolder().addCallback(this);
        setFocusable(true);
        //Initialize game state variables. DON'T RENDER THE GAME YET.

    }
    MarioThread mt;
    Background bg;
    Rect bmp = new Rect();
    Player p;
    Goal flagpole;
    int levelY = 12;
    int levelX = 25;
    static int levelNumber;
    static int FireballCount = 4;
    static int HammerCount = 2;
    static int EnemyCount = 10;
    static int WIDTH, HEIGHT;
    String scoreString;
    Terrain level[][] = new Terrain[levelX][levelY];
    Entity enemies[] = new Entity[EnemyCount];
    Fireball Fireballs[] = new Fireball[FireballCount];
    Hammer Hammers[] = new Hammer[HammerCount];
    
    Bitmap PlayerNormalRight = BitmapFactory.decodeResource(getResources(), R.drawable.luiginormalright);
    Bitmap PlayerNormalLeft = BitmapFactory.decodeResource(getResources(), R.drawable.luiginormalleft);
    Bitmap PlayerRunningRight = BitmapFactory.decodeResource(getResources(), R.drawable.luigirunright);
    Bitmap PlayerRunningLeft = BitmapFactory.decodeResource(getResources(), R.drawable.luigirunleft);
    Bitmap FirePlayerNormalRight = BitmapFactory.decodeResource(getResources(), R.drawable.fireluiginormalright);
    Bitmap FirePlayerNormalLeft = BitmapFactory.decodeResource(getResources(), R.drawable.fireluiginormalleft);
    Bitmap FirePlayerRunningRight = BitmapFactory.decodeResource(getResources(), R.drawable.fireluigirunright);
    Bitmap FirePlayerRunningLeft = BitmapFactory.decodeResource(getResources(), R.drawable.fireluigirunleft);
    Bitmap GoombaLeft = BitmapFactory.decodeResource(getResources(), R.drawable.goombafaceleft);
    Bitmap GoombaRight = BitmapFactory.decodeResource(getResources(), R.drawable.goombafaceright);
    Bitmap FireBroLeft = BitmapFactory.decodeResource(getResources(), R.drawable.firebrofaceleft);
    Bitmap FireBroRight = BitmapFactory.decodeResource(getResources(), R.drawable.firebrofaceright);
    Bitmap HammerBroLeft = BitmapFactory.decodeResource(getResources(), R.drawable.hammerbrofaceleft);
    Bitmap HammerBroRight = BitmapFactory.decodeResource(getResources(), R.drawable.hammerbrofaceright);
    Bitmap EmptyItemBlock = BitmapFactory.decodeResource(getResources(), R.mipmap.questionblockempty);
    Bitmap directionalPad = BitmapFactory.decodeResource(getResources(), R.drawable.dpad);
    Bitmap aButton = BitmapFactory.decodeResource(getResources(), R.drawable.abutton);
    Bitmap bButton = BitmapFactory.decodeResource(getResources(), R.drawable.bbutton);
    Bitmap lifeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.luigihead);
    Bitmap Flag = BitmapFactory.decodeResource(getResources(), R.mipmap.flagpole);
    Bitmap brick = BitmapFactory.decodeResource(getResources(), R.mipmap.brick);
    Bitmap ground = BitmapFactory.decodeResource(getResources(), R.mipmap.groundblock);
    Bitmap background = BitmapFactory.decodeResource(getResources(), R.mipmap.grass);
    Bitmap luigibackground = BitmapFactory.decodeResource(getResources(), R.mipmap.luigiemblem);
    Bitmap question = BitmapFactory.decodeResource(getResources(), R.mipmap.questionblock);
    Bitmap FireFlower = BitmapFactory.decodeResource(getResources(), R.mipmap.fireflower);
    Bitmap RedMushroom = BitmapFactory.decodeResource(getResources(), R.mipmap.redmushroom);
    Bitmap GreenMushroom = BitmapFactory.decodeResource(getResources(), R.mipmap.greenmushroom);
    Bitmap FireBall = BitmapFactory.decodeResource(getResources(), R.drawable.fireball);
    Bitmap HammerFaceLeft = BitmapFactory.decodeResource(getResources(), R.drawable.hammerfaceleft);
    Bitmap HammerFaceRight = BitmapFactory.decodeResource(getResources(), R.drawable.hammerfaceright);

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        // Construct game initial state
        // Launch animator thread
        WIDTH = background.getWidth();
        HEIGHT = background.getHeight();
        p = new Player(getWidth(), getHeight());
        flagpole = new Goal();
        mt = new MarioThread(this);
        levelNumber = 0;
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.mipmap.grass));
        Terrain.loadLevel(level, levelX, levelY, p, flagpole);
        Entity.loadEnemies(enemies);
        mt.start();
    }

    public void draw(Canvas c) {
        bmp.set(0, getHeight()*27/40, getWidth(), getHeight());
        c.drawBitmap(luigibackground, null, bmp, null);
        bmp.set(getWidth()/4 - getWidth()/6, getHeight()*4/5 - getWidth()/6, getWidth()/4 + getWidth()/6, getHeight()*4/5 + getWidth()/6);
        c.drawBitmap(directionalPad, null, bmp, null);
        bmp.set(getWidth()*7/10 - getWidth()/13, getHeight()*17/20 - getWidth()/13, getWidth()*7/10 + getWidth()/13, getHeight()*17/20 + getWidth()/13);
        c.drawBitmap(bButton, null, bmp, null);
        bmp.set(getWidth()*9/10 - getWidth()/13, getHeight()*3/4 - getWidth()/13, getWidth()*9/10 + getWidth()/13, getHeight()*3/4 + getWidth()/13);
        c.drawBitmap(aButton, null, bmp, null);
        bg.draw(getWidth(), getHeight(), c);
        bg.update(p.bx);
        //draw player
        bmp.set(p.area);
        if (p.powerUp == 0 || p.powerUp == 1) {
            if (p.vx == 0 && p.faceRight) {
                c.drawBitmap(PlayerNormalRight, null, bmp, null);
            }
            else if (p.vx == 0 && !p.faceRight) {
                c.drawBitmap(PlayerNormalLeft, null, bmp, null);
            }
            if (p.vx > 0) {
                c.drawBitmap(PlayerRunningRight, null, bmp, null);
            }
            else if (p.vx < 0) {
                c.drawBitmap(PlayerRunningLeft, null, bmp, null);
            }
        }
        if (p.powerUp == 2) {
            if (p.vx == 0 && p.faceRight) {
                c.drawBitmap(FirePlayerNormalRight, null, bmp, null);
            }
            else if (p.vx == 0 && !p.faceRight) {
                c.drawBitmap(FirePlayerNormalLeft, null, bmp, null);
            }
            if (p.vx > 0) {
                c.drawBitmap(FirePlayerRunningRight, null, bmp, null);
            }
            else if (p.vx < 0) {
                c.drawBitmap(FirePlayerRunningLeft, null, bmp, null);
            }
        }
        p.update(level, enemies, Fireballs, Hammers, flagpole, levelX, levelY, levelNumber);

        //draw enemies
        for (int i = 0; i < EnemyCount; i++) {
            if (enemies[i] != null) {
                bmp.set(enemies[i].area);
                if (enemies[i].getClass() == Goomba.class) {
                    if (enemies[i].faceRight) {
                        c.drawBitmap(GoombaRight, null, bmp, null);
                    } else {
                        c.drawBitmap(GoombaLeft, null, bmp, null);
                    }
                    if (Goomba.update(level, levelX, levelY, enemies[i], p)) {
                        enemies[i] = null;
                    }
                }
                else if (enemies[i].getClass() == FireBro.class){
                    if (enemies[i].faceRight){
                        c.drawBitmap(FireBroRight, null, bmp, null);
                        FireBro.throwFireball(Fireballs, getWidth(), getHeight(), enemies[i], true);
                    } else{
                        c.drawBitmap(FireBroLeft, null, bmp, null);
                        FireBro.throwFireball(Fireballs, getWidth(), getHeight(), enemies[i], false);
                    }
                    if(FireBro.update(level, levelX, levelY, enemies[i], p)){
                        enemies[i] = null;
                    }
                }
                else if (enemies[i].getClass() == HammerBro.class){
                    if (enemies[i].faceRight){
                        c.drawBitmap(HammerBroRight, null, bmp, null);
                        HammerBro.throwHammer(Hammers, getWidth(), getHeight(), enemies[i], true);
                    } else {
                        c.drawBitmap(HammerBroLeft, null, bmp, null);
                        HammerBro.throwHammer(Hammers, getWidth(), getHeight(), enemies[i], false);
                    }
                    if(HammerBro.update(level, levelX, levelY, enemies[i], p)){
                        enemies[i] = null;
                    }
                }
            }
        }

        //draw terrain
        for (int y = 0; y < levelY; y++) {
            for (int x = 0; x < levelX; x++) {
                if (level[x][y] != null) {
                    if ((level[x][y].x1 > 0 || level[x][y].x1 < getWidth()) && (level[x][y].x2 > 0 || level[x][y].x2 < getWidth())){
                        bmp.set(level[x][y].area);
                        if (level[x][y].getClass() == Brick.class) {
                            c.drawBitmap(brick, null, bmp, null);
                        } else if (level[x][y].getClass() == Ground.class) {
                            c.drawBitmap(ground, null, bmp, null);
                        } else if (level[x][y].getClass() == ItemBlock.class) {
                            c.drawBitmap(question, null, bmp, null);
                        } else if (level[x][y].getClass() == ItemBlockEmpty.class) {
                            c.drawBitmap(EmptyItemBlock, null, bmp, null);
                        } else if (level[x][y].getClass() == FireFlower.class) {
                            c.drawBitmap(FireFlower, null, bmp, null);
                        } else if (level[x][y].getClass() == RedMushroom.class) {
                            c.drawBitmap(RedMushroom, null, bmp, null);
                        } else if (level[x][y].getClass() == GreenMushroom.class){
                            c.drawBitmap(GreenMushroom, null, bmp, null);
                        }
                    }
                    level[x][y].update(p.bx);
                }
            }
        }
        bmp.set(flagpole.area);
        c.drawBitmap(Flag, null, bmp, null);
        if(flagpole.update(p)){
            levelNumber++;
            levelNumber%=3;
            Terrain.loadLevel(level, levelX, levelY, p, flagpole);
            Entity.loadEnemies(enemies);
            for (int i = 0; i < FireballCount; i++){
                Fireballs[i] = null;
            }
            for (int i = 0; i < HammerCount; i++){
                Hammers[i] = null;
            }
        }

        //draw fire
        for (int i = 0; i < FireballCount; i++){
            if (Fireballs[i] != null){
                bmp.set(Fireballs[i].area);
                c.drawBitmap(FireBall, null, bmp, null);
                if (Fireballs[i].update(p, level, enemies, levelX, levelY)){
                    Fireballs[i] = null;
                }
            }
        }

        //draw hammers
        for (int i = 0; i < HammerCount; i++){
            if (Hammers[i] != null){
                bmp.set(Hammers[i].area);
                if(Hammers[i].faceRight){
                    c.drawBitmap(HammerFaceRight, null, bmp, null);
                }
                else{
                    c.drawBitmap(HammerFaceLeft, null, bmp, null);
                }
                if (Hammers[i].update(p, getHeight())){
                    Hammers[i] = null;
                }
            }
        }

        //draw lives
        for (int i = 0; i < p.lives; i++){
            bmp.set(getWidth()/12*i,0, getWidth()/12*(i+1), getHeight()/20);
            c.drawBitmap(lifeIcon, null, bmp, null);
        }

        //display score
        scoreString = Integer.toString(p.score);
        Paint score = new Paint();
        score.setColor(Color.WHITE);
        score.setTextSize(70);
        c.drawText(scoreString, getWidth()/100, getHeight()/11, score);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        // Respond to surface changes, e.g., aspect ratio changes.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        // The cleanest way to stop a thread is by interrupting it.
        // MarioThread regularly checks its interrupt flag.
        mt.interrupt();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        // Update game state in response to events
        // touch-down, touch-up, and touch-move
        float x, y;
        switch(e.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_MOVE:
                x = e.getX(e.getActionIndex());
                y = e.getY(e.getActionIndex());
                p.setVelocity(x, y, true, Fireballs);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                x = e.getX(e.getActionIndex());
                y = e.getY(e.getActionIndex());
                p.setVelocity(x, y, false, Fireballs);
                break;
        }
        return true;
    }
}
