package com.bb.game.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;
import static com.bb.game.utils.Volume.*;

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
        Diz se o jogo foi resetado ou não.
     */
    private boolean gameReset;
    /*
        Instância da classe Chimp que controla o macaco.
     */
    private Chimp chimp;
    /*
        Variáveis responsáveis pelas animacões, posicionamento e texturas
        do cenário/fundo.
     */
    private static final Texture backgroundTexture = new Texture("chimp\\background.png");
    private static final Texture panelTexture = new Texture("memory\\panel.png");
    private static final Texture computerTexture = new Texture("chimp\\computer.png");
    /*
        Sons a serem usados no jogo.
     */
    private static final Music bgmusic = Gdx.audio.newMusic(Gdx.files.internal("chimp\\bgmusic.mp3"));
    private static final Sound sfxLose = Gdx.audio.newSound(Gdx.files.internal("chimp\\sfxLose.mp3"));
    private static final Sound sfxWin = Gdx.audio.newSound(Gdx.files.internal("chimp\\sfxWin.mp3"));
    private static final List<Sound> soundList = List.of(
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx1.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx2.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx3.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx4.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx5.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx6.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx1.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx2.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx3.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx4.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx5.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx6.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx1.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx2.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx3.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx4.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx5.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("chimp\\sfx6.mp3"))
    );

    /*
        Construtor que chama a parte lógica e funções para inicializar o jogo.
     */
    ChimpGraphics(Difficulty difficulty){
        this.logic = new ChimpLogic(difficulty);
        this.gameReset = false;

        bgmusic.play();
        bgmusic.setVolume(MUSIC_VOLUME);
        bgmusic.setLooping(true);

        reset();
        initializeButtons();
        setUpStage();
    }
    /*
        Chama o método de reiniciar da parte lógica e inicializa novamente os botões.
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
        setUpPanel();
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

        Actor panel = new Image(panelTexture);
        panel.setBounds(Constants.WORLD_WIDTH * 0.84f, Constants.WORLD_HEIGHT * 0.59f, Constants.WORLD_WIDTH * 0.19f, Constants.WORLD_HEIGHT * 0.41f);
        getStage().addActor(panel);

        Actor computer = new Image(computerTexture);
        computer.setBounds(Constants.WORLD_WIDTH * 0f, Constants.WORLD_HEIGHT * 0.01f, Constants.WORLD_WIDTH * 0.9f, Constants.WORLD_HEIGHT * 0.95f);
        getStage().addActor(computer);
        computer.setTouchable(Touchable.disabled);

        this.chimp = new Chimp(Constants.WORLD_WIDTH * 0.73f, Constants.WORLD_HEIGHT * 0f, Constants.WORLD_WIDTH * 0.3f, Constants.WORLD_HEIGHT * 0.6f);
        getStage().addActor(this.chimp);

        for(Actor button: this.buttons){
            getStage().addActor(button);
        }
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

        for(int i = 0; i < this.logic.getButtons().size(); i++){
            x = Constants.WORLD_WIDTH * (float)(minXValues.get(i) + Math.random() * (maxXValues.get(i) - minXValues.get(i)));
            y = Constants.WORLD_HEIGHT * (float)(minYValues.get(i) + Math.random() * (maxYValues.get(i) - minYValues.get(i)));

            if(!gameReset)
                this.buttons.add(new ButtonNumber(this.logic.getButtons().get(i), x, y));
            else
                this.buttons.get(this.logic.getButtons().get(i)-1).changePosition(x,y);
        }

        showButtons();
        gameReset = true;
    }
    /*
        Função que retorna uma lista com os valores mínimos de X possíveis
        dentre intervalos selecionados onde o número pode aparecer.
     */
    private List<Float> getMinXValues() {
        List<Float> minXValues = List.of(
                0.03f,
                0.13f,
                0.24f,
                0.35f,
                0.46f,
                0.57f,
                0.03f,
                0.13f,
                0.24f,
                0.35f,
                0.46f,
                0.57f,
                0.03f,
                0.13f,
                0.24f,
                0.35f,
                0.46f,
                0.57f
        );
        return minXValues;
    }
    /*
        Função que retorna uma lista com os valores máximos de X possíveis
        dentre intervalos selecionados onde o número pode aparecer.
     */
    private List<Float> getMaxXValues() {
        List<Float> maxXValues = List.of(
                0.12f,
                0.23f,
                0.34f,
                0.45f,
                0.56f,
                0.67f,
                0.12f,
                0.23f,
                0.34f,
                0.45f,
                0.56f,
                0.67f,
                0.12f,
                0.23f,
                0.34f,
                0.45f,
                0.56f,
                0.67f
        );
        return maxXValues;
    }
    /*
        Função que retorna uma lista com os valores mínimos de Y possíveis
        dentre intervalos selecionados onde o número pode aparecer.
     */
    private List<Float> getMinYValues() {
        List<Float> minYValues;
        if(this.logic.getDifficulty() == 0 || this.logic.getDifficulty() == 1) {
            minYValues = List.of(
                    0.15f,
                    0.15f,
                    0.15f,
                    0.15f,
                    0.15f,
                    0.15f,
                    0.42f,
                    0.42f,
                    0.42f,
                    0.42f,
                    0.42f,
                    0.42f
            );
        }
        else {
            minYValues = List.of(
                    0.15f,
                    0.15f,
                    0.15f,
                    0.15f,
                    0.15f,
                    0.15f,
                    0.28f,
                    0.28f,
                    0.28f,
                    0.28f,
                    0.28f,
                    0.28f,
                    0.55f,
                    0.55f,
                    0.55f,
                    0.55f,
                    0.55f,
                    0.55f
            );
        }
        return minYValues;
    }
    /*
        Função que retorna uma lista com os valores máximos de Y possíveis
        dentre intervalos selecionados onde o número pode aparecer.
     */
    private List<Float> getMaxYValues() {
        List<Float> maxYValues;
        if(this.logic.getDifficulty() == 0) {
            maxYValues = List.of(
                    0.82f,
                    0.82f,
                    0.82f,
                    0.82f,
                    0.82f,
                    0.82f
            );
        }
        else if(this.logic.getDifficulty() == 1) {
            maxYValues = List.of(
                    0.41f,
                    0.41f,
                    0.41f,
                    0.41f,
                    0.41f,
                    0.41f,
                    0.82f,
                    0.82f,
                    0.82f,
                    0.82f,
                    0.82f,
                    0.82f
            );
        }
        else {
            maxYValues = List.of(
                    0.27f,
                    0.27f,
                    0.27f,
                    0.27f,
                    0.27f,
                    0.27f,
                    0.54f,
                    0.54f,
                    0.54f,
                    0.54f,
                    0.54f,
                    0.54f,
                    0.81f,
                    0.81f,
                    0.81f,
                    0.81f,
                    0.81f,
                    0.81f
            );
        }
        return maxYValues;
    }
    /*
        Metodo responsável por identificar a tentativa do botão feita
        pelo jogador.
     */
    private void makePlay(final ButtonNumber clickedButton){
        int points = this.logic.tryButton(clickedButton.getId());

        float HIDE_DELAY = 0.25f;
        if(points == 0){
            sfxLose.play(SFX_VOLUME);
            this.chimp.laugh();
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    reset();
                    chimp.idle();
                }
            }, HIDE_DELAY);
        } else {
            updateScore(points);
            if(clickedButton.getId() == 1) {
                hideButtons();
            }
            clickedButton.empty();
            if(this.logic.noButtonsLeft()) {
                sfxWin.play(SFX_VOLUME);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        reset();
                    }
                }, HIDE_DELAY);
            } else
                soundList.get(clickedButton.getId()).play(SFX_VOLUME);
        }
    }
    /*
        Esconde todos os botões restantes para que
        não seja mais possível vê-los, apenas sua posição.
     */
    private void hideButtons() {
        for(ButtonNumber button : this.buttons) {
            button.hide();
        }
    }
    /*
        Mostra todos os botões, função utilizada para resetar o jogo e
        mostrar os botões que estavam escondidos.
     */
    private void showButtons() {
        for(ButtonNumber button : this.buttons) {
            button.show();
        }
    }

    @Override
    public void render(float delta) {
        this.logic.incrementTimer(delta);
        super.render(delta);
    }

    @Override
    public void dispose() {
        bgmusic.stop();
        super.dispose();
    }
}
