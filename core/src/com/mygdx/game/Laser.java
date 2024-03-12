package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Laser {
    //komponen yang dibutuhkan
    Texture gambarLaser;
    Rectangle hitboxLaser;

    //constructor
    public Laser(Texture gambarLaser, Rectangle hitboxLaser) {
        this.gambarLaser = gambarLaser;
        this.hitboxLaser = hitboxLaser;
    }

    //untuk batch draw
    public void draw(Batch batch) {
        batch.draw(gambarLaser,hitboxLaser.x,hitboxLaser.y);
    }

    public Texture getGambarLaser() {
        return gambarLaser;
    }

    public void setGambarLaser(Texture gambarLaser) {
        this.gambarLaser = gambarLaser;
    }

    public Rectangle getHitboxLaser() {
        return hitboxLaser;
    }

    public void setHitboxLaser(Rectangle hitboxLaser) {
        this.hitboxLaser = hitboxLaser;
    }
}
