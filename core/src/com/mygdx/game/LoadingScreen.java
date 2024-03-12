package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadingScreen implements Screen, InputProcessor {
    Game games;
    AssetManager assetManager;
    SpriteBatch batch;
    BitmapFontCache text, anyKey;
    OrthographicCamera camera;
    Viewport viewport;

    //contructor
    public LoadingScreen() {
        games = (Game) Gdx.app.getApplicationListener();
        Initialize();
    }

    public LoadingScreen(Game game) {
        this.games = game;
        Initialize();
    }

    //dipanggil oleh constructor
    protected void Initialize() {
        //ngambil asset manager dari class Games
        assetManager = ((Games) games).getAssetManager();
        camera = new OrthographicCamera(Games.widht, Games.height);
        camera.setToOrtho(true, Games.widht, Games.height);
        viewport = new FitViewport(Games.widht, Games.height, camera);
        batch = new SpriteBatch();

        //menloading font
        text = new BitmapFontCache(((Games) games).getLoadingFont());
        anyKey = new BitmapFontCache(((Games) games).getLoadingFont());
        //set text jadi warna putih
        text.setColor(Color.WHITE);
        anyKey.setColor(Color.WHITE);
        //set isi text
        text.setText("Loading 0%", 185, 320);
        anyKey.setText("PRESS ANY KEY TO CONTINUE..", 145, 380);
        anyKey.setAlphas(0);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        //set batch
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        draw();
        batch.end();
        //untuk update
        update();
    }

    public void draw() {
        //drawt tulisan
        text.draw(batch);
        anyKey.draw(batch);
    }

    public void update() {
        //untuk memunculkan text press any key
        if (assetManager.update()) {
            anyKey.setAlphas(1);
        }
        //progess loadingnya
        float progress = assetManager.getProgress() * 100;
        String loadtext = String.format("Loading %.2f%%", progress);
        text.setText(loadtext, 185, 320);
    }

    public void SwitchToMenuScreen() {
        //kalau sudah finish bisa ke main menu screen
        if (assetManager.isFinished())
            games.setScreen(new MainMenuScreen(games));
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
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        this.SwitchToMenuScreen();
        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        this.SwitchToMenuScreen();
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
