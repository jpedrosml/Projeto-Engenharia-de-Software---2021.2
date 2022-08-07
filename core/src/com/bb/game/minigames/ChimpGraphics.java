package com.bb.game.minigames;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;
import com.bb.game.utils.Fonts;

import java.util.ArrayList;
import java.util.List;
    /*
        Classe responsável pela parte gráfica do Jogo do Macaco ~ Chimp Game
    */
public class ChimpGraphics extends MiniGameGraphics{
    /*
        Lista de botões.
     */
    private List<ButtonNumber> buttons;
    /*
        Instância da classe lógica.
     */
    private ChimpLogic logic;
    /*
        Guarda o indicador de pontos.
     */
    private Label scoreIndicator;
    /*
        Guarda o indicador do temporizador.
     */
    private Label timerIndicator;

    private boolean gameReset;

    /*
        Variáveis responsáveis pelas animacoes, posicionamento e texturas
        do cenário/fundo.
     */

    private static final Texture backgroundTexture = new Texture("memory\\table.png");
    private static final Texture panelTexture = new Texture("memory\\panel.png");

    /*
        Construtor que chama a parte lógica para inicializar o jogo.
     */
    ChimpGraphics(Difficulty difficulty){
        this.logic = new ChimpLogic(difficulty);
        this.gameReset = false;
        initializeButtons();
        setUpStage();
    }

    /*
        Chama o método de reiniciar da parte lógica.
     */
    private void reset() {
        this.logic.reset();
        initializeButtons();
    }

    /*
        Inicializa o cenário do jogo.
     */
    private void setUpStage() {
        setUpActors();
        setUpText();
        getStage().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(event.getTarget() instanceof ButtonNumber){
                    ButtonNumber clickedButton = (ButtonNumber) event.getTarget();
                    makePlay(clickedButton);
                }
            }
        });
    }

    /*
        Inicializa os objetos do jogo, os Atores.
     */
    private void setUpActors() {
        Actor background = new Image(backgroundTexture);
        background.setBounds(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getStage().addActor(background);

        for(Actor button: this.buttons){
            getStage().addActor(button);
        }

        Actor panel = new Image(panelTexture);
        panel.setBounds(Constants.WORLD_WIDTH * 0.84f, Constants.WORLD_HEIGHT * 0.59f, Constants.WORLD_WIDTH * 0.19f, Constants.WORLD_HEIGHT * 0.41f);
        getStage().addActor(panel);
    }

    /*
        Inicializa a parte textual que sera exibida na tela.
     */
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

    /*
        Inicializa os botões, onde seu número varia de acordo
        com a dificuldade.
     */
    private void initializeButtons() {
        if(this.buttons == null)
            this.buttons = new ArrayList<>();

        List<Float> minXValues = getMinXValues();
        List<Float> maxXValues = getMaxXValues();
        List<Float> minYValues = getMinYValues();
        List<Float> maxYValues = getMaxYValues();

        float x;
        float y;
        float width = Constants.WORLD_WIDTH * 0.05f;
        float height = Constants.WORLD_HEIGHT * 0.125f;

        for(int i = 0; i < this.logic.getButtons().size(); i++){
            x = Constants.WORLD_WIDTH * (float)(minXValues.get(i) + Math.random() * (maxXValues.get(i) - minXValues.get(i)));
            y = Constants.WORLD_HEIGHT * (float)(minYValues.get(i) + Math.random() * (maxYValues.get(i) - minYValues.get(i)));

            if(!gameReset)
                this.buttons.add(new ButtonNumber(this.logic.getButtons().get(i), x, y, width, height));
            else
                this.buttons.get(this.logic.getButtons().get(i)-1).changePosition(x,y,width,height);
        }

        showButtons();
        gameReset = true;
    }

    private List<Float> getMinXValues() {
        List<Float> minXValues = List.of(
                0f,
                0.16f,
                0.32f,
                0.48f,
                0.64f,
                0.80f,
                0f,
                0.13f,
                0.26f,
                0.39f,
                0.52f,
                0.65f,
                0f,
                0.13f,
                0.26f,
                0.39f,
                0.52f,
                0.65f
        );
        return minXValues;
    }

    private List<Float> getMaxXValues() {
        List<Float> maxXValues = List.of(
                0.15f,
                0.31f,
                0.47f,
                0.63f,
                0.79f,
                0.95f,
                0.12f,
                0.25f,
                0.38f,
                0.51f,
                0.64f,
                0.77f,
                0.12f,
                0.25f,
                0.38f,
                0.51f,
                0.64f,
                0.77f
        );
        return maxXValues;
    }

    private List<Float> getMinYValues() {
        List<Float> minYValues;
        if(this.logic.getDifficulty() == 0 || this.logic.getDifficulty() == 1) {
            minYValues = List.of(
                    0f,
                    0f,
                    0f,
                    0f,
                    0f,
                    0f,
                    0.43f,
                    0.43f,
                    0.43f,
                    0.43f,
                    0.43f,
                    0.43f
            );
        } else {
            minYValues = List.of(
                    0f,
                    0f,
                    0f,
                    0f,
                    0f,
                    0f,
                    0.29f,
                    0.29f,
                    0.29f,
                    0.29f,
                    0.29f,
                    0.29f,
                    0.58f,
                    0.58f,
                    0.58f,
                    0.58f,
                    0.58f,
                    0.58f
            );
        }
        return minYValues;
    }

    private List<Float> getMaxYValues() {
        List<Float> maxYValues;
        if(this.logic.getDifficulty() == 0) {
            maxYValues = List.of(
                    0.85f,
                    0.85f,
                    0.85f,
                    0.85f,
                    0.85f,
                    0.48f
            );
        }
        else if(this.logic.getDifficulty() == 1) {
            maxYValues = List.of(
                    0.42f,
                    0.42f,
                    0.42f,
                    0.42f,
                    0.42f,
                    0.42f,
                    0.85f,
                    0.85f,
                    0.85f,
                    0.85f,
                    0.85f,
                    0.85f
            );
        }
        // y: 0-28;  x: 0-15,16-31,32-47,48-63,64-79,80-95
        // y: 29-57; x: 0-12,13-25,26-38,39-51,52-64,65-77
        // y: 58-86; x: 0-12,13-25,26-38,39-51,52-64,65-77
        else {
            maxYValues = List.of(
                    0.28f,
                    0.28f,
                    0.28f,
                    0.28f,
                    0.28f,
                    0.28f,
                    0.57f,
                    0.57f,
                    0.57f,
                    0.57f,
                    0.57f,
                    0.57f,
                    0.86f,
                    0.86f,
                    0.86f,
                    0.86f,
                    0.86f,
                    0.86f
            );
        }
        return maxYValues;
    }

    private void hideButtons() {
        for(ButtonNumber button : this.buttons) {
            button.hide();
        }
    }

    private void showButtons() {
        for(ButtonNumber button : this.buttons) {
            button.show();
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

    /*
        Metodo responsável por identificar a tentativa do botão feita
        pelo jogador.
     */
    private void makePlay(final ButtonNumber clickedButton){
        int points = this.logic.tryButton(clickedButton.getId());

        float HIDE_DELAY = 0.25f;
        if(points == 0){
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    reset();
                }
            }, HIDE_DELAY);
        } else {
            updateScore(points);
            if(clickedButton.getId() == 1) {
                hideButtons();
            }
            clickedButton.empty();
            if(this.logic.noButtonsLeft())
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        reset();
                    }
                }, HIDE_DELAY);
        }
    }
}
