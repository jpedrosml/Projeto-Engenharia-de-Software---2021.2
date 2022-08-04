package com.bb.game.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;
import com.bb.game.utils.Fonts;
import static com.bb.game.utils.Volume.SFX_VOLUME;

import java.util.ArrayList;
import java.util.List;

// Classe que realiza a parte visual do jogo da sequência.
public class SequenceGraphics extends MiniGameGraphics {

    // Lista das cores disponíveis.
    private List<SequenceColor> colors;

    // Instância da classe que contém a parte lógica do jogo.
    private SequenceLogic logic;

    // Rótulo da pontuação.
    private Label scoreIndicator;

    // Rótulo do tempo.
    private Label timerIndicator;


    // Variável que guarda a textura da imagem de fundo.
    private static final Texture backgroundTexture = new Texture("sequence\\images\\stage.png");

    // Variável que guarda a textura do painel.
    private static final Texture panelTexture = new Texture("memory\\panel.png");

    // Variável que guarda a textura da plateia.
    private static final Texture crowdTexture = new Texture("sequence\\images\\crowd.png");

    // Lista que guarda as distâncias entre as cores para cada quantidade de cores possível.
    private static final List<Float> colorsDistance = List.of(0.32f, 0.213f, 0.16f);

    // Lista que guarda os sons de cada instrumento.
    private static final List<Sound> soundList = List.of(
            Gdx.audio.newSound(Gdx.files.internal("sequence\\music\\keyboard.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sequence\\music\\guitar.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sequence\\music\\singer.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sequence\\music\\bongo.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sequence\\music\\trumpet.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sequence\\music\\ukulele.mp3"))
    );

    SequenceGraphics(Difficulty difficulty) {
        this.logic = new SequenceLogic(difficulty);
        initializeColors();
        setUpStage();
        reset();
        disableTouch();
        showSequence();
    }

    private void reset() {
        this.logic.reset();
        for(int i = 0; i < this.logic.getDifficultyColors(); i++) {
            this.colors.get(i).setId(i);
        }
    }

    private void setUpStage() {
        setUpActors();
        setUpText();
        getStage().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(event.getTarget() instanceof SequenceColor) {
                    SequenceColor clickedColor = (SequenceColor) event.getTarget();
                    tryColor(clickedColor);
                }
            }
        });
    }

    private void setUpActors() {
        Actor background = new Image(backgroundTexture);
        background.setTouchable(Touchable.disabled);
        background.setBounds(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getStage().addActor(background);

        for(Actor color: this.colors) {
            getStage().addActor(color);
        }

        Actor panel = new Image(panelTexture);
        panel.setTouchable(Touchable.disabled);
        panel.setBounds(Constants.WORLD_WIDTH * 0.84f, Constants.WORLD_HEIGHT * 0.59f, Constants.WORLD_WIDTH * 0.19f, Constants.WORLD_HEIGHT * 0.41f);
        getStage().addActor(panel);

        Actor crowd = new Image(crowdTexture);
        crowd.setTouchable(Touchable.disabled);
        crowd.setBounds(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getStage().addActor(crowd);
    }

    private void setUpText() {
        final Color PALE_YELLOW = new Color(243f/255f, 248f/255f, 146f/255f, 1);
        final float FONT_SCALE = 1.5f;
        final Label.LabelStyle STYLE_1 = new Label.LabelStyle(Fonts.COMIC_NEUE, PALE_YELLOW);

        Label scoreText = new Label("Score: ", STYLE_1);
        scoreText.setPosition(Constants.WORLD_WIDTH*0.86f, Constants.WORLD_HEIGHT*0.85f);
        scoreText.setFontScale(FONT_SCALE);
        getStage().addActor(scoreText);

        Label timeLeftText = new Label("Time left: ", STYLE_1);
        timeLeftText.setPosition(Constants.WORLD_WIDTH*0.86f, Constants.WORLD_HEIGHT*0.75f);
        timeLeftText.setFontScale(FONT_SCALE);
        getStage().addActor(timeLeftText);

        this.scoreIndicator = new Label(getScore().toString(), new Label.LabelStyle(Fonts.COMIC_NEUE, Color.WHITE));
        this.scoreIndicator.setPosition(scoreText.getX() + (scoreText.getWidth() * FONT_SCALE), scoreText.getY());
        this.scoreIndicator.setFontScale(FONT_SCALE);
        getStage().addActor(this.scoreIndicator);

        this.timerIndicator = new Label(String.format("%.0f", getTimer()), new Label.LabelStyle(Fonts.COMIC_NEUE, Color.GREEN));
        this.timerIndicator.setPosition(timeLeftText.getX() + (timeLeftText.getWidth() * FONT_SCALE), timeLeftText.getY());
        this.timerIndicator.setFontScale(FONT_SCALE);
        getStage().addActor(this.timerIndicator);
    }

    private void initializeColors() {
        this.colors = new ArrayList<>();

        float x = Constants.WORLD_WIDTH * 0.09f;
        float y = Constants.WORLD_HEIGHT * 0.05f;
        float width = Constants.WORLD_WIDTH * 0.2f;
        float height = Constants.WORLD_HEIGHT * 0.5f;

        for(int i = 0; i < this.logic.getDifficultyColors(); i++) {
            this.colors.add(new SequenceColor(i, x,y,width,height));
            x = x + Constants.WORLD_WIDTH * colorsDistance.get(this.logic.getDifficultyColors()-3);
        }
    }

    private void showSequence() {
        float HIDE_DELAY_BRIGHT = 0.5f;
        float HIDE_DELAY_DARK = 1f;
        float HIDE_DELAY_INCREMENT = 1f;
        float TOTAL_DELAY = 0f;

        for(int i = 0; i < this.logic.getSequenceSize(); i++) {
            int colorId = this.logic.getFromSequence(i);
            final SequenceColor clr = this.colors.get(colorId);

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    clr.setBright();
                    soundList.get(colorId).play(SFX_VOLUME);
                }
            }, HIDE_DELAY_BRIGHT + HIDE_DELAY_INCREMENT * i);

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    clr.setDark();
                }
            }, HIDE_DELAY_DARK + HIDE_DELAY_INCREMENT * i);
            TOTAL_DELAY = HIDE_DELAY_DARK + HIDE_DELAY_INCREMENT * i;
        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                enableTouch();
            }
        }, TOTAL_DELAY);
    }

    private void tryColor(final SequenceColor clickedColor) {
        soundList.get(clickedColor.getId()).play(SFX_VOLUME);
        int points = this.logic.tryColor(clickedColor.getId());

        if(points == 0) {
            this.logic.wrongColor();
            disableTouch();
            float HIDE_DELAY_END_SEQUENCE = 0.5f;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    showSequence();
                }
            }, HIDE_DELAY_END_SEQUENCE);
        }
        else {
            float HIDE_DELAY_END_SEQUENCE = 2f;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if(logic.noColorsLeft())
                        highlightAllColors();
                    endSequence(points);
                }
            }, HIDE_DELAY_END_SEQUENCE);
        }
    }

    private void endSequence(int points) {
        updateScore(points);
        if(this.logic.noColorsLeft()) {
            // soundList.get(5).play(SFX_VOLUME);
            playAllSounds();
            this.logic.incrementSequenceSize();
            reset();
            disableTouch();

            float HIDE_DELAY_END_SEQUENCE = 1f;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    darkenAllColors();
                    showSequence();
                }
            }, HIDE_DELAY_END_SEQUENCE);
        }
    }

    private void enableTouch() {
        for(SequenceColor color : this.colors) {
            color.setTouchable(Touchable.enabled);
        }
    }

    private void disableTouch() {
        for(SequenceColor color : this.colors) {
            color.setTouchable(Touchable.disabled);
        }
    }

    private void highlightAllColors() {
        for(SequenceColor color : this.colors) {
            color.setBright();
        }
    }

    private void darkenAllColors() {
        for(SequenceColor color : this.colors) {
            color.setDark();
        }
    }

    private void playAllSounds() {
        for(int i = 0; i < soundList.size()-1; i++) {
            soundList.get(i).play(SFX_VOLUME);
        }
    }

    @Override
    public void render(float delta) {
        this.logic.incrementTimer(delta);
        this.timerIndicator.setText(String.format("%.0f", TIME_LIMIT - getTimer()));

        if(getTimer()>TIME_LIMIT - 10f){
            timerIndicator.setStyle(new Label.LabelStyle(Fonts.COMIC_NEUE, Color.RED));
        }else if(getTimer()>TIME_LIMIT/2f){
            timerIndicator.setStyle(new Label.LabelStyle(Fonts.COMIC_NEUE, Color.YELLOW));
        }

        this.scoreIndicator.setText(getScore().toString());
        super.render(delta);
    }

}
