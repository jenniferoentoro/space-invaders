package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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

import javax.xml.soap.Text;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;

public class MainMenuScreen implements Screen, InputProcessor {
    Game games;
    AssetManager assetManager;
    SpriteBatch batch;
    OrthographicCamera camera, stageCamera;
    Viewport viewport;

    //komponen - komponen yang dibutuhkan
    Stage stage;
    Label title;
    Label subtitle;
    Label settingLabel;
    Label easy;
    Label hard;
    Label highscores;
    TextButton playBut;
    TextButton settingButton;
    TextButton backSetting;
    TextButton highScoreButton;
    TextButton backHigh;
    TextButton onButton;
    TextButton offButton;
    TextButton easyButt;
    TextButton hardButt;
    TextButton backPlay;
    Window playWindow;
    Window highWindow;
    Window settingWindow;

    InputMultiplexer multiPlexer;

    //constructor
    public MainMenuScreen() {
        games = (Game) Gdx.app.getApplicationListener();
        Initialize();
    }

    public MainMenuScreen(Game games) {
        this.games = games;
        Initialize();
    }

    //dipanggil pada constructor
    protected void Initialize() {
        //ambil asset manager dari class games
        assetManager = ((Games) games).getAssetManager();

        //set camera
        camera = new OrthographicCamera(Games.widht, Games.height);
        camera.setToOrtho(true, Games.widht, Games.height);
        viewport = new FitViewport(Games.widht, Games.height, camera);
        batch = new SpriteBatch();

        multiPlexer = new InputMultiplexer();
        multiPlexer.addProcessor(this);
        //set camera stage
        stageCamera = new OrthographicCamera(Games.widht, Games.height);
        stageCamera.setToOrtho(false, Games.widht, Games.height);
        stage = new Stage(new FitViewport(Games.widht, Games.height, stageCamera));
        multiPlexer.addProcessor(stage);

        Skin uiskin = assetManager.get("uiskin.json", Skin.class);
        //set title
        title = new Label("SPACE INVADERS", uiskin);
        Label.LabelStyle style = new Label.LabelStyle(title.getStyle());
        style.font = assetManager.get("bigfontui.ttf", BitmapFont.class);
        title.setStyle(style);
        title.setWidth(480);
        title.setX(0);
        title.setY(600);
        title.setAlignment(Align.center);
        title.setColor(Color.WHITE);
        stage.addActor(title);
        
        //set subtitle
        subtitle = new Label("To Infinity and Beyond", uiskin);
        Label.LabelStyle styles = new Label.LabelStyle(subtitle.getStyle());
        styles.font = assetManager.get("bigfont.ttf", BitmapFont.class);
        subtitle.setStyle(styles);
        subtitle.setWidth(480);
        subtitle.setX(0);
        subtitle.setY(550);
        subtitle.setAlignment(Align.center);
        subtitle.setColor(Color.WHITE);
        stage.addActor(subtitle);

        //set play button
        playBut = new TextButton("Play", uiskin);
        playBut.setHeight(64);
        playBut.setWidth(180);
        playBut.setPosition(150, 450);
        playBut.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    playWindow.setVisible(true);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playBut);

        //set play window
        playWindow = new Window("Pick Level", uiskin);
        playWindow.setHeight(270);
        playWindow.setWidth(350);
        playWindow.setPosition(stage.getWidth() / 2 - playWindow.getWidth() / 2, 270);
        playWindow.setMovable(false);
        playWindow.setModal(true);
        playWindow.setResizable(false);
        playWindow.setVisible(false);
        playWindow.getTitleLabel().setAlignment(Align.center);
        stage.addActor(playWindow);

        //set back button
        backPlay = new TextButton("Back", uiskin);
        backPlay.setWidth(120);
        backPlay.setHeight(36);
        backPlay.setPosition(playWindow.getWidth() / 2 - backPlay.getWidth() / 2, 32);
        backPlay.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    playWindow.setVisible(false);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        playWindow.addActor(backPlay);

        //set easy label
        easy = new Label("Easy :", uiskin);
        easy.setAlignment(Align.right);
        easy.setY(200);
        easy.setX(0);
        easy.setWidth((float) (playWindow.getWidth() / 2.5));
        playWindow.addActor(easy);

        //set hard label
        hard = new Label("Hard :", uiskin);
        hard.setAlignment(Align.right);
        hard.setY(150);
        hard.setX(0);
        hard.setWidth((float) (playWindow.getWidth() / 2.5));
        playWindow.addActor(hard);
        
        //sett highscore button
        highScoreButton = new TextButton("High Score", uiskin);
        highScoreButton.setHeight(64);
        highScoreButton.setWidth(150);
        highScoreButton.setPosition(stage.getWidth() / 2 - highScoreButton.getWidth() / 2, 350);
        highScoreButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    highWindow.setVisible(true);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(highScoreButton);
        
        //set highscore window
        highWindow = new Window("Highscore", uiskin);
        highWindow.setHeight(320);
        highWindow.setWidth(400);
        highWindow.setPosition(40, 230);
        highWindow.setMovable(false);
        highWindow.setModal(true);
        highWindow.setResizable(false);
        highWindow.setVisible(false);
        highWindow.getTitleLabel().setAlignment(Align.center);
        stage.addActor(highWindow);

        //set back button di highscore
        backHigh = new TextButton("Back", uiskin);
        backHigh.setWidth(120);
        backHigh.setHeight(36);
        backHigh.setPosition(highWindow.getWidth() / 2 - backHigh.getWidth() / 2, 35);
        backHigh.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    highWindow.setVisible(false);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        highWindow.addActor(backHigh);

        //melakukan deserialization untuk mengambil data highscore
        reading();
        //set highscore
        highscores = new Label(stringHighScore, uiskin);
        highscores.setAlignment(Align.right);
        highscores.setY(70);
        highscores.setX(85);
        highscores.setWidth((float) (playWindow.getWidth() / 2.5));
        highWindow.addActor(highscores);
        
        //set setting button
        settingButton = new TextButton("Settings", uiskin);
        settingButton.setHeight(48);
        settingButton.setWidth(100);
        settingButton.setPosition(355, 700);
        settingButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    settingWindow.setVisible(true);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(settingButton);

        //set setting window
        settingWindow = new Window("Settings", uiskin);
        settingWindow.setHeight(220);
        settingWindow.setWidth(300);
        settingWindow.setPosition(88, 500);
        settingWindow.setMovable(false);
        settingWindow.setModal(true);
        settingWindow.setResizable(false);
        settingWindow.setVisible(false);
        settingWindow.getTitleLabel().setAlignment(Align.center);
        stage.addActor(settingWindow);

        //set setting label
        settingLabel = new Label("Music :", uiskin);
        settingLabel.setAlignment(Align.right);
        settingLabel.setY(150);
        settingLabel.setX(0);
        settingLabel.setWidth((float) (settingWindow.getWidth() / 2.5));
        settingWindow.addActor(settingLabel);

        //set on button
        onButton = new TextButton("On", uiskin);
        onButton.setWidth(60);
        onButton.setHeight(30);
        onButton.setPosition(130, 150);
        onButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    Gdx.input.setInputProcessor(multiPlexer);
                    Music music = assetManager.get("music.mp3", Music.class);
                    music.setLooping(true);

                    if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                        music.play();
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        settingWindow.addActor(onButton);

        //set off button
        offButton = new TextButton("Off", uiskin);
        offButton.setWidth(60);
        offButton.setHeight(30);
        offButton.setPosition(190, 150);
        offButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    Gdx.input.setInputProcessor(multiPlexer);
                    Music music = assetManager.get("music.mp3", Music.class);
                    music.setLooping(true);
                    if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                        music.stop();
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        settingWindow.addActor(offButton);


        backSetting = new TextButton("Back", uiskin);
        backSetting.setWidth(120);
        backSetting.setHeight(36);
        backSetting.setPosition(settingWindow.getWidth() / 2 - backSetting.getWidth() / 2, 42);
        backSetting.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    settingWindow.setVisible(false);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        settingWindow.addActor(backSetting);

        easyButt = new TextButton("Easy", uiskin);
        easyButt.setWidth(60);
        easyButt.setHeight(30);
        easyButt.setPosition(playWindow.getWidth() / 2 - onButton.getWidth() / 2, 200);
        easyButt.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    games.setScreen(new GameScreen(games));
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        playWindow.addActor(easyButt);

        hardButt = new TextButton("Hard", uiskin);
        hardButt.setWidth(60);
        hardButt.setHeight(30);
        hardButt.setPosition(playWindow.getWidth() / 2 - onButton.getWidth() / 2, 150);
        hardButt.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    games.setScreen(new GameScreenHard(games));
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        playWindow.addActor(hardButt);


    }

    HighScores highScores;
    String stringHighScore = "";
    DecimalFormat df = new DecimalFormat("0.00");

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
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiPlexer);
        Music music = assetManager.get("music.mp3", Music.class);
        music.setLooping(true);
        if (!music.isPlaying())
            music.play();
    }

    @Override
    public void render(float v) {
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

    public void draw() {
        Texture background = assetManager.get("wppMain.png", Texture.class);
        batch.draw(background, 1, 1);
    }

    public void update() {

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
