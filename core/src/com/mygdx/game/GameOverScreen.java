package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;

public class GameOverScreen implements Screen, InputProcessor {
    //highscore
    HighScores highScores;
    String stringHighScore = "";
    DecimalFormat df = new DecimalFormat("0.00");

    Game games;
    AssetManager assetManager;
    SpriteBatch batch;
    OrthographicCamera camera;
    OrthographicCamera stageCamera;
    Viewport viewport;

    Stage stage;
    Label gameover;
    Label scoreLabel;
    TextButton playAgain;
    InputMultiplexer multiPlexer;

    //constructor
    public GameOverScreen() {
        games = (Game) Gdx.app.getApplicationListener();
        Initialize();
    }

    public GameOverScreen(Game game) {
        this.games = game;
        Initialize();
    }

    //dipanggil saat memanggil constructor
    private void Initialize() {
        assetManager = ((Games) games).getAssetManager();
        camera = new OrthographicCamera(Games.widht, Games.height);
        camera.setToOrtho(true, Games.widht, Games.height);
        viewport = new FitViewport(Games.widht, Games.height, camera);
        batch = new SpriteBatch();

        multiPlexer = new InputMultiplexer();
        multiPlexer.addProcessor(this);

        //memanggil method untuk membaca highscore
        reading();

        //set stage camera
        stageCamera = new OrthographicCamera(Games.widht, Games.height);
        stageCamera.setToOrtho(false, Games.widht, Games.height);
        stage = new Stage(new FitViewport(Games.widht, Games.height, stageCamera));

        multiPlexer.addProcessor(stage);

        //set text gameover
        Skin mySkin = assetManager.get("uiskin.json", Skin.class);
        gameover = new Label("GAME OVER", mySkin);
        Label.LabelStyle style = new Label.LabelStyle(gameover.getStyle());
        style.font = assetManager.get("bigfontui.ttf", BitmapFont.class);
        gameover.setStyle(style);
        gameover.setWidth(480);
        gameover.setX(0);
        gameover.setY(600);
        gameover.setAlignment(Align.center);
        gameover.setColor(Color.RED);
        stage.addActor(gameover);

        //set text score
        scoreLabel = new Label("Your Score = " + df.format(highScores.lastScore), mySkin);
        Label.LabelStyle styles = new Label.LabelStyle(scoreLabel.getStyle());
        styles.font = assetManager.get("bigfont.ttf", BitmapFont.class);
        scoreLabel.setStyle(styles);
        scoreLabel.setWidth(480);
        scoreLabel.setX(0);
        scoreLabel.setY(550);
        scoreLabel.setAlignment(Align.center);
        scoreLabel.setColor(Color.WHITE);
        stage.addActor(scoreLabel);

        //set button play again
        playAgain = new TextButton("Play Again", mySkin);
        playAgain.setHeight(64);
        playAgain.setWidth(180);
        playAgain.setPosition(155, 350);
        playAgain.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    games.setScreen(new MainMenuScreen(games));
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playAgain);


    }

    //method untuk membaca highscore
    public void reading() {
        String filename = "highscores.ser";
        try {
            //Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            //method for deserialization of object
            highScores = (HighScores) in.readObject();
            in.close();
            file.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        for (Float higshcore : highScores.highScoresList) {
            stringHighScore = stringHighScore + df.format(higshcore) + "\n";
        }
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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiPlexer);
        Sound sound = assetManager.get("gameover.wav", Sound.class);
        sound.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        draw();
        batch.end();
        update();
        stage.act();
        stage.draw();
    }

    public void update() {

    }

    public void draw() {
        Texture background = assetManager.get("wppMain.png", Texture.class);
        batch.draw(background, 1, 1);


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height);
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

    }
}
