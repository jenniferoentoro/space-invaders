package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

//abstract class
abstract class Rocket {
    //aggregation laser
    Laser laser;

    //komponen yang dibutuhkan
    Texture gambar;
    Rectangle hitbox;

    //constructor
    public Rocket(Texture gambar) {
        this.gambar = gambar;
    }

    public void setLaser(Laser laser) {
        this.laser = laser;
    }

    public Texture getGambar() {
        return gambar;
    }

    public void setGambar(Texture gambar) {
        this.gambar = gambar;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox, float height, float width) {
        this.hitbox = hitbox;
        this.hitbox.height = height;
        this.hitbox.width = width;
    }


    public void draw(Batch batch) {
        batch.draw(gambar, hitbox.x, hitbox.y);

    }
}
