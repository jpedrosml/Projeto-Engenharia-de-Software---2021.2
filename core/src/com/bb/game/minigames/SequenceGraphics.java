package com.bb.game.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;
import static com.bb.game.utils.Volume.SFX_VOLUME;

import java.util.ArrayList;
import java.util.List;

// Classe que realiza a parte visual do jogo da sequência.
public class SequenceGraphics extends MiniGameGraphics {

    // Instância da classe que contém a parte lógica do jogo.
    private SequenceLogic logic;

    // Lista das cores disponíveis.
    private List<SequenceColor> colors;

    // Variável que guarda a textura da imagem de fundo.
    private static final Texture backgroundTexture = new Texture("sequence\\stage.png");

    // Variável que guarda a textura do painel.
    private static final Texture panelTexture = new Texture("memory\\panel.png");

    // Variável que guarda a textura da plateia.
    private static final Texture crowdTexture = new Texture("sequence\\crowd.png");

    // Lista que guarda as distâncias entre as cores para cada quantidade de cores possível.
    private static final List<Float> colorsDistance = List.of(0.32f, 0.213f, 0.16f);


    // Lista que guarda os sons de cada instrumento.
    private static final List<Sound> soundList = List.of(
            Gdx.audio.newSound(Gdx.files.internal("sequence\\keyboard.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sequence\\guitar.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sequence\\singer.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sequence\\bongo.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sequence\\trumpet.mp3"))
    );

    // Inicializa a classe de lógica e chama funções para inicializar o jogo.
    SequenceGraphics(Difficulty difficulty) {
        this.logic = new SequenceLogic(difficulty);
        initializeColors();
        setUpStage();
        reset();
        disableTouch();
        showSequence();
    }

    // Chama a função de reset da classe de lógica e coloca os IDs das cores que estarão no jogo.
    private void reset() {
        this.logic.reset();
        for(int i = 0; i < this.logic.getDifficultyColors(); i++) {
            this.colors.get(i).setId(i);
        }
    }

    // Função que inicializa o cenário do jogo. Chama as funções para inicializar os textos e atores,
    // e após adiciona um listener ao cenário que notifica quando o usuário tocar em uma cor e
    // então chama a função que verifica a tentativa do usuário.
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

    // Inicializa os atores, que são os objetos do jogo.
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

    // Inicializa cada cor do jogo, variando pelo número de cores da dificuldade.
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

    // Função que mostra a sequência de cores para o usuário, uma cor e seu som por vez.
    private void showSequence() {

        // Delays a serem utilizados na execução das ações para que não ocorra sobreposição de uma sobre outra.
        float HIDE_DELAY_BRIGHT = 0.5f;
        float HIDE_DELAY_DARK = 1f;
        float HIDE_DELAY_INCREMENT = 1f;
        float TOTAL_DELAY = 0f;

        for(int i = 0; i < this.logic.getSequenceSize(); i++) {
            int colorId = this.logic.getFromSequence(i);
            final SequenceColor clr = this.colors.get(colorId);

            // Destaca a cor da sequência e toca o seu som após o delay.
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    clr.setBright();
                    soundList.get(colorId).play(SFX_VOLUME);
                }
            }, HIDE_DELAY_BRIGHT + HIDE_DELAY_INCREMENT * i);

            // Volta a imagem da cor sem destaque após o delay.
            TOTAL_DELAY = HIDE_DELAY_DARK + HIDE_DELAY_INCREMENT * i;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    clr.setDark();
                }
            }, TOTAL_DELAY);
        }

        // Habilita as cores a serem tocadas após a sequência ter sido mostrada.
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                enableTouch();
            }
        }, TOTAL_DELAY);
    }

    // Função que verifica a tentativa do usuário ao tocar uma cor.
    private void tryColor(final SequenceColor clickedColor) {
        soundList.get(clickedColor.getId()).play(SFX_VOLUME);
        int points = this.logic.tryColor(clickedColor.getId());

        // Se o usuário escolheu a cor errada e não ganhou nenhum ponto, chama a função que atualiza
        // os valores associados à sequência para contabilizar o erro, desabilita as cores de serem tocadas
        // para mostrar a sequência de cores novamente após um delay.
        if(points == 0) {
            disableTouch();
            this.logic.wrongColor();
            float HIDE_DELAY_END_SEQUENCE1 = 0.5f;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    showSequence();
                }
            }, HIDE_DELAY_END_SEQUENCE1);
        }
        // Se o usuário acertou a cor da sequência, chama a função que realiza as ações necessárias
        // ao usuário terminar uma sequência.
        else {
            if(this.logic.noColorsLeft())
                disableTouch();
            float HIDE_DELAY_END_SEQUENCE2 = 2f;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    endSequence(points);
                }
            }, HIDE_DELAY_END_SEQUENCE2);
        }
    }

    // Função chamada quando o usuário termina a sequência. Chama funções para atualizar o placar,
    // destacar todas as cores, tocar todos os sons da dificuldade, incrementar o tamanho da sequência,
    // resetar a sequência para então chamar as funções que retornam todas as cores
    // para a sua imagem normal e mostram a próxima sequência, após o delay.
    private void endSequence(int points) {
        updateScore(points);
        if(this.logic.noColorsLeft()) {
            highlightAllColors();
            playAllSounds();
            this.logic.incrementSequenceSize();
            reset();

            float HIDE_DELAY_END_SEQUENCE3 = 1f;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    darkenAllColors();
                    showSequence();
                }
            }, HIDE_DELAY_END_SEQUENCE3);
        }
    }

    // Habilita todas as cores a serem tocadas pelo usuário.
    private void enableTouch() {
        for(SequenceColor color : this.colors) {
            color.setTouchable(Touchable.enabled);
        }
    }

    // Desabilita todas as cores de serem tocadas pelo usuário.
    private void disableTouch() {
        for(SequenceColor color : this.colors) {
            color.setTouchable(Touchable.disabled);
        }
    }

    // Destaca todas as cores.
    private void highlightAllColors() {
        for(SequenceColor color : this.colors) {
            color.setBright();
        }
    }

    // Retorna todas as cores para sua imagem normal.
    private void darkenAllColors() {
        for(SequenceColor color : this.colors) {
            color.setDark();
        }
    }

    // Toca os sons de todos os instrumentos disponíveis na dificuldade atual.
    private void playAllSounds() {
        for(int i = 0; i < this.logic.getDifficultyColors(); i++) {
            soundList.get(i).play(SFX_VOLUME);
        }
    }

    @Override
    public void render(float delta) {
        this.logic.incrementTimer(delta);
        super.render(delta);
    }
}
