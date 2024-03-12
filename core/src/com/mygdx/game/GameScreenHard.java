package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import javax.xml.soap.Text;
import java.io.*;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GameScreenHard implements Screen, InputProcessor {

    SpriteBatch batch;
    AssetManager assetManager;
    BitmapFontCache textHP;
    BitmapFontCache textScore;

    Game games;
    //rocket player
    Rocket rocketPlayer;
    //musuh
    ArrayList<Musuh> musuhList = new ArrayList<Musuh>();
    //score
    private float score = 0f;

    //sound
    Sound killSound;
    Sound receiveLaserSound;
    Sound hitEnemySound;
    Sound laserSound;

    Texture lasermusuh;
    Texture lasermusuh2;

    //camera
    private OrthographicCamera camera;

    private Array<Rectangle> lasersPlayer;
    private Array<Rectangle> lasersMusuh;
    private Array<Rectangle> lasersMusuh2;
    private Array<Rectangle> musuhs;
    private Array<Rectangle> musuhs2;
    private Array<Rectangle> musuhs3;


    private long musuhDropTime;
    private long musuhDropTime2;
    private long musuhDropTime3;
    private long laserPlayerDropTime;
    private long laserMusuhDropTime;
    private long laserMusuh2DropTime;
    Music music;
    long zigzagTimer;
    int zigzag = 0;
    int HP = 100;
    DecimalFormat df = new DecimalFormat("#.00");

    Viewport viewport;

    //Background
    Texture background, background2;
    float bg_max, bg_1, bg_2;
    final int bg_speed = 65;


    public GameScreenHard(Game game) {
        this.games = game;
        //casting games menjadi class Games agar dapat memanggil asset manager
        assetManager = ((Games) games).getAssetManager();

        //load background
        background = assetManager.get("bgMain2.jpg");
        background2 = assetManager.get("bgMain2.jpg");
        //atur coordinat background
        bg_max = 800;
        bg_1 = 0;
        bg_2 = bg_max * (1);


        //set camera, viewport,batch
        camera = new OrthographicCamera(Games.widht, Games.height);
        viewport = new FitViewport(Games.widht, Games.height, camera);
        batch = new SpriteBatch();

        //set text
        textHP = new BitmapFontCache(assetManager.get("bigfont.ttf", BitmapFont.class));
        textHP.setColor(Color.WHITE);
        textScore = new BitmapFontCache(assetManager.get("bigfont.ttf", BitmapFont.class));
        textScore.setColor(Color.WHITE);

        // set sound
        laserSound = assetManager.get("laser.wav", Sound.class);
        hitEnemySound = assetManager.get("tabrakmusuh.wav", Sound.class);
        receiveLaserSound = assetManager.get("kenalasermusuh.wav", Sound.class);
        killSound = assetManager.get("explosion.wav", Sound.class);

        //Rocket player
        rocketPlayer = new Rocket(assetManager.<Texture>get("ship.png")) {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
        rocketPlayer.setHitbox(new Rectangle().setPosition((480 / 2 - 64 / 2), 20), 100, 130);
        rocketPlayer.setLaser(new Laser(assetManager.<Texture>get("laser.png"), new Rectangle()));

        //get texture laser musuh
        lasermusuh = assetManager.get("lasermusuh.png");
        lasermusuh2 = assetManager.get("lasermusuhmerah.png");

        //set camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Games.widht, Games.height);

        //timer untuk mengatur zigzag
        zigzagTimer = TimeUtils.millis();

        //arraylist musuh
        musuhs = new Array<Rectangle>();
        musuhs2 = new Array<Rectangle>();
        musuhs3 = new Array<Rectangle>();

        //arraylist laser
        lasersPlayer = new Array<Rectangle>();
        lasersMusuh = new Array<Rectangle>();
        lasersMusuh2 = new Array<Rectangle>();

        //memanggil method yang dibutuhkan
        addMusuh();
        spawnMusuh();
        spawnMusuh2();
        spawnMusuh3();
    }

    //untuk memasukan list musuh
    private void addMusuh() {
        musuhList.add(new Musuh(100, assetManager.<Texture>get("musuh1.png"), true));
        musuhList.add(new Musuh(90.5f, assetManager.<Texture>get("musuh2.png")));
        musuhList.add(new Musuh(800, assetManager.<Texture>get("musuh3.png")));
    }

    //temp untuk membatasi agar musuh yang terspawn tidak bertumpukan
    double temp = 0;

    //spawn musuh 1
    private void spawnMusuh() {
        Rectangle musuh = new Rectangle();
        //menentukan letak
        musuh.x = MathUtils.random(50, 480 - 140);
        musuh.y = 800;
        //menentukan besar
        musuh.width = 90;
        musuh.height = 90;
        //memasukan dalam list
        musuhs.add(musuh);
        musuhDropTime = TimeUtils.millis();
        temp = musuh.x;

    }

    private void spawnMusuh2() {
        Rectangle musuh = new Rectangle();

        Boolean ulang = true;
        while (ulang) {
            musuh.x = MathUtils.random(0, 480 - 90);

            if (musuh.x != temp) {
                temp = musuh.x;
                ulang = false;
            }
        }

        musuh.y = 800;
        musuh.width = 90;
        musuh.height = 90;

        musuhs2.add(musuh);
        musuhDropTime2 = TimeUtils.millis();

    }

    private void spawnMusuh3() {
        Rectangle musuh = new Rectangle();
        Boolean ulang = true;
        while (ulang) {
            musuh.x = MathUtils.random(0, 480 - 90);

            if (musuh.x != temp) {
                temp = musuh.x;
                ulang = false;
            }
        }
        musuh.y = 800;
        musuh.width = 90;
        musuh.height = 90;


        musuhs3.add(musuh);
        musuhDropTime3 = TimeUtils.millis();

    }

    private void spawnLaserPlayer() {
        Rectangle laser = new Rectangle();
        laser.y = rocketPlayer.hitbox.y + 63;
        laser.x = rocketPlayer.hitbox.x + 60;

        lasersPlayer.add(laser);

        laserPlayerDropTime = TimeUtils.millis();
    }

    private void spawnLaserMusuh(Rectangle musuh) {

        Rectangle laser = new Rectangle();


        laser.y = musuh.y - 30;
        laser.x = musuh.x + 28;


        lasersMusuh.add(laser);
        laserMusuhDropTime = TimeUtils.millis();
    }

    private void spawnLaserMusuh2(Rectangle musuh) {

        Rectangle laser = new Rectangle();


        laser.y = musuh.y - 30;
        laser.x = musuh.x + 23;


        lasersMusuh2.add(laser);
        laserMusuh2DropTime = TimeUtils.millis();
    }


    @Override
    public void show() {
        MainMenuScreen menuscreen = new MainMenuScreen();
        Gdx.input.setInputProcessor(this);
        music = assetManager.get("music.mp3", Music.class);
        music.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0.21f, 0.53f, 1);

        //menambahkan score
        score += Gdx.graphics.getRawDeltaTime();

        //mengatur pergerakan background
        bg_1 -= bg_speed * Gdx.graphics.getDeltaTime();
        bg_2 = bg_1 + bg_max;
        if (bg_2 <= 0) {
            bg_1 = 0;
            bg_2 = bg_max * (1);
        }

        //update camera
        camera.update();

        //memulai batch
        batch.begin();
        batch.draw(background, 0, bg_1);
        batch.draw(background2, 0, bg_2);

        rocketPlayer.draw(batch);

        textHP.setText("HP = " + HP, 10, 780);
        textHP.draw(batch);
        textScore.setText("Score = " + df.format(score), 10, 750);
        textScore.draw(batch);

        for (Rectangle musuhdrop1 : musuhs2) {
            batch.draw(musuhList.get(1).gambar, musuhdrop1.x, musuhdrop1.y);
        }

        for (Rectangle musuhdrop : musuhs) {
            batch.draw(musuhList.get(0).gambar, musuhdrop.x, musuhdrop.y);
        }

        for (Rectangle musuhdrop3 : musuhs3) {
            batch.draw(musuhList.get(2).gambar, musuhdrop3.x, musuhdrop3.y);
        }

        for (Rectangle laserPlayer : lasersPlayer) {
            rocketPlayer.laser.hitboxLaser.x = laserPlayer.x;
            rocketPlayer.laser.hitboxLaser.y = laserPlayer.y;
            rocketPlayer.laser.draw(batch);
        }

        for (Rectangle laserMusuh : lasersMusuh) {
            batch.draw(lasermusuh, laserMusuh.x, laserMusuh.y);
        }

        for (Rectangle laserMusuh2 : lasersMusuh2) {
            batch.draw(lasermusuh2, laserMusuh2.x, laserMusuh2.y);
        }


        batch.end();

        camera.update();

        // membuat rocketPlayer bisa dikendalikan dengan mouse atau touchsreen
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            rocketPlayer.hitbox.x = touchPos.x - 75;
            rocketPlayer.hitbox.y = touchPos.y - 64 / 2;

        }

        // membuat apabila rocketPlayer dikendarilan arrowkeys
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            rocketPlayer.hitbox.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            rocketPlayer.hitbox.x += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            rocketPlayer.hitbox.y += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            rocketPlayer.hitbox.y -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            games.setScreen(new MainMenuScreen(games));
        }

        // membatasi posisi player dilayar (sumbu x)
        if (rocketPlayer.hitbox.x < 0) rocketPlayer.hitbox.x = 0;
        if (rocketPlayer.hitbox.x > 400 - 64) rocketPlayer.hitbox.x = 400 - 64;

        // membatasi posisi player dilayar (sumbu y)
        if (rocketPlayer.hitbox.y < 0) rocketPlayer.hitbox.y = 0;
        if (rocketPlayer.hitbox.y > 745 - 64) rocketPlayer.hitbox.y = 745 - 64;

        //mengatur pergerakan zigzag
        if (TimeUtils.millis() - zigzagTimer > 2000) {
            zigzagTimer = TimeUtils.millis();
            if (zigzag == 0) {
                zigzag = 1;
            } else {
                zigzag = 0;
            }
        }


        //mengatur spawn musuh
        if (TimeUtils.millis() - musuhDropTime > 2000) {
            spawnMusuh();
        }

        //mengatur spawn musuh 2
        if (TimeUtils.millis() - musuhDropTime2 > 3000) {
            spawnMusuh2();
        }

        //mengatur spawn musuh 3
        if (TimeUtils.millis() - musuhDropTime3 > 3500) {
            spawnMusuh3();
        }

        //mengatur spawn laser musuh
        if (TimeUtils.millis() - laserMusuhDropTime > 2000) {
            for (Rectangle musuh : musuhs) {
                spawnLaserMusuh(musuh);
            }
        }

        //mengatur spawn laser musuh 2
        if (TimeUtils.millis() - laserMusuh2DropTime > 2500) {
            for (Rectangle musuh2 : musuhs2) {
                spawnLaserMusuh2(musuh2);
            }
        }


        //mengatur spawn laser player
        if (TimeUtils.millis() - laserPlayerDropTime > 700) {
            spawnLaserPlayer();
            laserSound.play();
        }

        //jika hp habis (player mati)
        if (HP <= 0) {
            HP = 0;
            //memanggil method untuk membaca file dan menambahkan highscore, lalu menyimpannya kembali
            readingSaving();


            this.dispose();
            games.setScreen(new GameOverScreen(games));
        }

        //maksimum HP
        if (HP > 200) {
            HP = 200;
        }

        //iterator player
        Iterator<Rectangle> iterPlayer = lasersPlayer.iterator();

        while (iterPlayer.hasNext()) {
            Rectangle laserdrop = iterPlayer.next();
            laserdrop.y += 1000 * Gdx.graphics.getDeltaTime();
            if (laserdrop.y + 15 > 800) {
                iterPlayer.remove();
            }
        }

        //iterator laser musuh
        Iterator<Rectangle> laserMusuhIterator = lasersMusuh.iterator();
        while (laserMusuhIterator.hasNext()) {
            Rectangle laserdrop = laserMusuhIterator.next();
            laserdrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (laserdrop.y + 20 < 0) {
                laserMusuhIterator.remove();
            }
            if (laserdrop.overlaps(rocketPlayer.hitbox)) {
                laserMusuhIterator.remove();
                receiveLaserSound.play();
                HP -= 40;
            }
        }

        //iterator laser musuh 2
        Iterator<Rectangle> laserMusuh2Iterator = lasersMusuh2.iterator();
        while (laserMusuh2Iterator.hasNext()) {
            Rectangle laserdrop = laserMusuh2Iterator.next();
            laserdrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (laserdrop.y + 20 < 0) {
                laserMusuh2Iterator.remove();
            }
            if (laserdrop.overlaps(rocketPlayer.hitbox)) {
                laserMusuh2Iterator.remove();
                receiveLaserSound.play();
                HP -= 50;
            }
        }

        //iterator musuh 1
        Iterator<Rectangle> iterMusuh1 = musuhs.iterator();
        while (iterMusuh1.hasNext()) {
            Rectangle musuhdrop = iterMusuh1.next();
            if (zigzag % 2 == 0) {
                musuhdrop.x -= 25 * Gdx.graphics.getDeltaTime();
            } else if (zigzag % 2 == 1) {
                musuhdrop.x += 25 * Gdx.graphics.getDeltaTime();
            }
            musuhdrop.y -= (musuhList.get(0).speed * Gdx.graphics.getDeltaTime());
            if (musuhdrop.y + 64 < 0)
                iterMusuh1.remove();

            for (Rectangle laser : lasersPlayer) {
                if (musuhdrop.overlaps(laser)) {
                    iterMusuh1.remove();
                    killSound.play();
                    score += 40;
                    HP += 10;
                } else if (musuhdrop.overlaps(rocketPlayer.hitbox)) {
                    iterMusuh1.remove();
                    hitEnemySound.play();
                    HP -= 200;
                }
            }

        }

        //iterator musuh 2
        Iterator<Rectangle> iterMusuh2 = musuhs2.iterator();
        while (iterMusuh2.hasNext()) {
            Rectangle musuhdrop = iterMusuh2.next();
            musuhdrop.y -= musuhList.get(1).speed * Gdx.graphics.getDeltaTime();
            if (musuhdrop.y + 64 < 0)
                iterMusuh2.remove();

            for (Rectangle laser : lasersPlayer) {
                if (musuhdrop.overlaps(laser)) {
                    iterMusuh2.remove();
                    killSound.play();
                    score += 40;
                    HP += 10;
                } else if (musuhdrop.overlaps(rocketPlayer.hitbox)) {
                    iterMusuh2.remove();
                    hitEnemySound.play();
                    HP -= 200;
                }
            }
        }

        //iterator musuh 3
        Iterator<Rectangle> iterMusuh3 = musuhs3.iterator();
        while (iterMusuh3.hasNext()) {
            Rectangle musuhdrop = iterMusuh3.next();
            musuhdrop.y -= musuhList.get(2).speed * Gdx.graphics.getDeltaTime();
            if (musuhdrop.y + 64 < 0)
                iterMusuh3.remove();
            for (Rectangle laser : lasersPlayer) {
                if (musuhdrop.overlaps(laser)) {
                    iterMusuh3.remove();
                    killSound.play();
                    score += 40;
                    HP += 10;
                } else if (musuhdrop.overlaps(rocketPlayer.hitbox)) {
                    iterMusuh3.remove();
                    hitEnemySound.play();
                    HP -= 200;
                }
            }
        }


    }

    public void readingSaving() {
        //deserialization
        String filename = "highscores.ser";
        HighScores oldHighScores = new HighScores(new ArrayList<Float>(), -1f);
        try {
            //Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            //method for deserialization of object
            oldHighScores = (HighScores) in.readObject();
            in.close();
            file.close();
        } catch (Exception e) {
            System.out.println("Belum ada Data");
        }

        //tambahkan highscore
        oldHighScores.lastScore = score;
        if (oldHighScores.highScoresList.size() < 10) {
            oldHighScores.highScoresList.add(score);
        } else {
            if (oldHighScores.highScoresList.get(9) < score) {
                oldHighScores.highScoresList.set(9, score);
            }
        }
        //sort arraylist highscores
        Collections.sort(oldHighScores.highScoresList, Collections.<Float>reverseOrder());


        //Save data
        //Serialization
        try {
            //saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(oldHighScores);
            out.close();
            file.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        music.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

