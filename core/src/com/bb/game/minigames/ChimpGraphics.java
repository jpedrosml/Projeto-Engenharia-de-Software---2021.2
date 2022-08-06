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
        Guarda a imagem da mão do Bean.
     */
    private Image hand;
    /*
        Guarda o último Botão clicado.
     */
    private ButtonNumber lastClickedButton;
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
    private final float HAND_ANIMATION_DURATION = 0.25f;
    private final float HAND_ORIGINAL_POSX = Constants.WORLD_WIDTH * 0.95f;
    private final float HAND_ORIGINAL_POSY = Constants.WORLD_HEIGHT * (-0.72f);
    private static final Texture handTexture = new Texture("memory\\arm.png");
    private static final Texture backgroundTexture = new Texture("memory\\table.png");
    private static final Texture panelTexture = new Texture("memory\\panel.png");

    /*
        Construtor que chama a parte lógica para inicializar o jogo.
     */
    ChimpGraphics(Difficulty difficulty){
        this.logic = new ChimpLogic(difficulty);
        this.hand = new Image(handTexture);
        this.hand = new Image(handTexture);
        this.hand.setBounds(HAND_ORIGINAL_POSX, HAND_ORIGINAL_POSY, Constants.WORLD_WIDTH * 0.26f, Constants.WORLD_HEIGHT * 1.3f);
        this.hand.setRotation(15f);
        this.hand.setTouchable(Touchable.disabled);
        initializeButtons();
        setUpStage();
        reset();
    }

    /*
        Chama o método de reiniciar da parte lógica.
     */
    private void reset() {
        this.logic.reset();
        for(int i = 0; i < this.logic.getButtons().size(); i++){
            this.buttons.get(i).setId(this.logic.getButtons().get(i));
            this.buttons.get(i).hide();
        }
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
                    if(!clickedButton.isRevealed()){
                        hand.clearActions();
                        hand.addAction(Actions.moveTo(HAND_ORIGINAL_POSX, HAND_ORIGINAL_POSY, HAND_ANIMATION_DURATION));
                        hand.setPosition(clickedButton.getCenterX() - hand.getImageWidth()*0.25f, clickedButton.getCenterY() - hand.getImageHeight()*0.8f);
                        clickedButton.reveal();
                        if(lastClickedButton == null) {
                            lastClickedButton = clickedButton;
                        } else {
                            makePlay(clickedButton);
                        }
                    }
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
        getStage().addActor(this.hand);
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
        this.buttons = new ArrayList<>();
        float x = 0f;
        float y = Constants.WORLD_HEIGHT * 0.37f;
        float width = Constants.WORLD_WIDTH * 0.1f;
        float height = Constants.WORLD_HEIGHT * 0.27f;

        for(int i = 0; i < this.logic.getButtons().size(); i++){
            if(i % 6 == 0)
                x = Constants.WORLD_WIDTH * 0.03f;
            this.buttons.add(new ButtonNumber(x, y, width, height));
            x = x + Constants.WORLD_WIDTH * 0.14f;
            if(i == 5){
                y = Constants.WORLD_HEIGHT * 0.68f;
            }else if(i == 11){
                y = Constants.WORLD_HEIGHT * 0.05f;
            }
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
            this.logic.wrongNumber();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {

                }
            }, HIDE_DELAY);
        } else {
            updateScore(points);
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
