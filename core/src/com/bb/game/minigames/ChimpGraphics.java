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
        initializeButtons();
        setUpStage();
    }

    /*
        Chama o método de reiniciar da parte lógica.
     */
    private void reset() {
        this.logic.reset();
        initializeButtons();
        setUpStage();
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
        float x = 0f;
        float y = Constants.WORLD_HEIGHT * 0.37f;
        float width = Constants.WORLD_WIDTH * 0.1f;
        float height = Constants.WORLD_HEIGHT * 0.27f;

        for(int i = 0; i < this.logic.getButtons().size(); i++){
            this.buttons.add(new ButtonNumber(this.logic.getButtons().get(i), x, y, width, height));
            x += Constants.WORLD_WIDTH * 0.14f;
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

        float HIDE_DELAY = 0.5f;
        if(points == 0){
            System.out.println("Reset");
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    reset();
                }
            }, HIDE_DELAY);
        } else {
            updateScore(points);
            clickedButton.hide();
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
