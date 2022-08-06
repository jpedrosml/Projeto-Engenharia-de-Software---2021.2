package com.bb.game.minigames;

import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

    /*
        Classe responsável pela parte lógica do Jogo do Macaco ~ Chimp Game
    */
public class ChimpLogic {

    /*
        Lista contendo o número de botões, que variam de acordo
        com a dificuldade.
    */
    private List<Integer> buttons;
    /*
        Inteiro que guarda o número de botões restantes para vencer
        um round do Jogo do Macaco.
    */
    private int buttonsLeft;
    /*
        Float que guarda o tempo passado para calcular corretamente
        os pontos ganhos pelo usuário.
     */
    private float timer;
    /*
        Inteiro que guarda o número de pontos que o usuário deixa de ganhar
        a cada segundo que passa; varia de acordo com a dificuldade.
     */
    private int pointsLostPerSec;
    /*
        Inteiro que guarda o número sendo verificado, para saber se
        o próximo é de fato seu sucessor.
     */
    private int iterator;

    /*
        Construtor que inicializa as variáveis.
     */
    ChimpLogic(Difficulty difficulty) {
        difficultyConfig(difficulty);
        this.buttonsLeft = this.buttons.size();
        this.iterator = 1;
        this.timer = 0;
    }

    /*
        Disponibiliza novos botões e reinicia o temporizador.
     */
    public void reset() {
        Collections.shuffle(this.buttons);
        this.buttonsLeft = this.buttons.size();
        this.iterator = 1;
        this.timer = 0;
    }

    /*
        Configura a dificuldade do mini jogo (fácil, médio, difícil).
     */
    private void difficultyConfig(Difficulty difficulty) {
        switch (difficulty){
            case EASY:
                this.buttons = Arrays.asList(1,2,3,4,5,6);
                Collections.shuffle(this.buttons);
                this.pointsLostPerSec = 200;
                break;
            case MEDIUM:
                this.buttons = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);
                this.pointsLostPerSec = 100;
                break;
            case HARD:
                this.buttons = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18);
                this.pointsLostPerSec = 50;
                break;
            default:
                throw new IllegalStateException();
        }
    }

    /*
        Recebe o número do botão clicado pelo jogador e verifica
        se foi de fato o correto. Caso seja, calcula a quantia de
        pontos para enfim retorná-la, reiniciando o temporizador.
     */
    public int tryButton(int button) {
        int points = 0;

        System.out.println(button);
        System.out.println(iterator);

        if(button == iterator) {
            points = Math.max((Constants.MAX_POINTS_PER_PLAY - (int)(this.timer * this.pointsLostPerSec)), Constants.MIN_POINTS_PER_PLAY);
            this.timer = 0;
            this.buttonsLeft -= 1;
            this.iterator += 1;
        }
        System.out.println(iterator);
        return points;
    }

    public boolean inOrder(int button1, int button2) {
        boolean flag = true;
        for (int i = 0; i < this.buttons.size() - 1; i++) {
            button1 = this.buttons.indexOf(i);
            button2 = this.buttons.indexOf(i + 1);

            if (button1 < button2) {
                this.buttons.remove(i);
                return true;
            }
        }
        return false;
    }

    /*
        Incrementa o temporizador conforme o tempo passado.
     */
    public void incrementTimer(float delta) {
        this.timer += delta;
    }

    public List<Integer> getButtons() {
        return buttons;
    }

    /*
        Retorna se não existem botões restantes, ou seja
        se o jogador acertou todos os números dos botões em ordem
        ascendente.
     */
    public boolean noButtonsLeft() {
        return this.buttonsLeft == 0;
    }

}
