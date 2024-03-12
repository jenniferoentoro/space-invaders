package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Games extends Game implements InputProcessor {
    //ukuran widht dan height
    final public static int widht = 480;
    final public static int height = 800;

    //asset manager (untuk menyimpan asset)
    AssetManager assetManager;
    //untuk font
    BitmapFont loadingFont;

    @Override
    public void create() {
        //membuat assetmanager
        assetManager = new AssetManager();

        //sound
        assetManager.load("laser.wav", Sound.class);
        assetManager.load("tabrakmusuh.wav", Sound.class);
        assetManager.load("kenalasermusuh.wav", Sound.class);
        assetManager.load("explosion.wav", Sound.class);
        assetManager.load("gameover.wav", Sound.class);

        //music
        assetManager.load("music.mp3", Music.class);

        //player
        assetManager.load("ship.png", Texture.class);
        assetManager.load("laser.png", Texture.class);

        //musuh
        assetManager.load("musuh1.png", Texture.class);
        assetManager.load("musuh2.png", Texture.class);
        assetManager.load("musuh3.png", Texture.class);
        assetManager.load("lasermusuh.png", Texture.class);
        assetManager.load("lasermusuhmerah.png", Texture.class);

        //background
        assetManager.load("bgMain.jpg", Texture.class);
        assetManager.load("bgMain2.jpg", Texture.class);
        assetManager.load("wppMain.png", Texture.class);


        //font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("VT323-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        parameter.flip = true;
        loadingFont = generator.generateFont(parameter);
        generator.dispose();

        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));


        FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mySmallFont.fontFileName = "VT323-Regular.ttf";
        mySmallFont.fontParameters.size = 32;
        mySmallFont.fontParameters.flip = true;
        assetManager.load("smallfont.ttf", BitmapFont.class, mySmallFont);


        FreetypeFontLoader.FreeTypeFontLoaderParameter myBigFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        myBigFont.fontFileName = "VT323-Regular.ttf";
        myBigFont.fontParameters.size = 35;
        assetManager.load("bigfont.ttf", BitmapFont.class, myBigFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter myBigFontui = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        myBigFontui.fontFileName = "VT323-Regular.ttf";
        myBigFontui.fontParameters.size = 64;
        assetManager.load("bigfontui.ttf", BitmapFont.class, myBigFontui);

        SkinLoader.SkinParameter skinParam = new SkinLoader.SkinParameter("uiskin.atlas");
        assetManager.load("uiskin.json", Skin.class, skinParam);

        this.setScreen(new LoadingScreen(this));

    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public BitmapFont getLoadingFont() {
        return loadingFont;
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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
