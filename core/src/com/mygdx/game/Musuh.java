package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Musuh extends Rocket {
    //komponen yang dibutuhkan
    int speed;
    boolean zigzag;

    //constructor
    public Musuh(int speed, Texture gambar) {
        super(gambar);
        this.speed = speed;
    }


    public Musuh(float speed, Texture gambar) {
        super(gambar);
        this.speed = (int) speed;
    }

    public Musuh(int speed, Texture gambar, boolean zigzag) {
        super(gambar);
        this.speed = speed;
        this.zigzag = zigzag;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(gambar, hitbox.x, hitbox.y);
    }
}
