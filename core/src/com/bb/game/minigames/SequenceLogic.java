package com.bb.game.minigames;

import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

// Classe que realiza a parte lógica do jogo da sequência
public class SequenceLogic {


    // Lista que contêm a sequência de cores a serem "iluminadas" na forma de inteiros que representam cada cor,
    // de 0 até o número de cores disponíveis na dificuldade menos 1.
    private List<Integer> sequence;

    // Inteiro que guarda o tamanho da sequência. É uma variável para poder ser facilmente incrementada quando
    // a sequência precisar ser aumentada. A dificuldade determina o tamanho inicial.
    private int sequenceSize;

    // Inteiro que guarda a posição na sequência que o jogador está, para que seja possível
    // saber qual a posição da sequência a ser verificada quando o usuário tocar em uma cor.
    private int sequenceIterator;

    // Inteiro que guarda o número de cores que restam para o usuário acertar todas as cores da sequência.
    private int colorsLeft;

    // Inteiro que guarda o número de pontos que o usuário deixa de ganhar a cada segundo que passa, varia de acordo com a dificuldade.
    private int pointsLostPerSec;

    // Float que guarda o tempo passado para calcular corretamente os pontos ganhos pelo usuário.
    private float timer;

    // Inteiro que guarda o número de cores diferentes que estarão no jogo, varia de acordo com a dificuldade.
    private int difficultyColors;


    // Inicializa variáveis e a sequência, de acordo com a dificuldade recebida.
    SequenceLogic(Difficulty difficulty) {
        this.sequence = new ArrayList<>();
        difficultyConfig(difficulty);
        this.colorsLeft = this.sequenceSize;
        this.sequenceIterator = 0;
        this.timer = 0;
        createSequence();
    }

    // Cria uma nova sequência e reinicia os valores associados à ela, assim como o timer.
    public void reset() {
        this.colorsLeft = this.sequenceSize;
        this.sequenceIterator = 0;
        this.timer = 0;
        createSequence();
    }

    // Reinicia os valores associados à sequência.
    public void wrongColor() {
        this.colorsLeft = this.sequenceSize;
        this.sequenceIterator = 0;
    }

    // Configura os valores de acordo com a dificuldade recebida.
    private void difficultyConfig(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                this.sequenceSize = 2;
                this.difficultyColors = 3;
                this.pointsLostPerSec = 200;
                break;
            case MEDIUM:
                this.sequenceSize = 3;
                this.difficultyColors = 4;
                this.pointsLostPerSec = 100;
                break;
            case HARD:
                this.sequenceSize = 4;
                this.difficultyColors = 5;
                this.pointsLostPerSec = 50;
                break;
            default:
                throw new IllegalStateException();
        }
    }

    // Cria a sequência até o tamanho do sequenceSize, adicionando em cada posição
    // um inteiro aleatório que representa uma cor disponível na dificuldade escolhida.
    private void createSequence() {
        this.sequence.clear();
        for(int i = 0; i < sequenceSize; i++) {
            this.sequence.add((int)(Math.random()*difficultyColors));
        }
    }

    // Retorna uma posição da sequência.
    public int getFromSequence(int i) {
        return this.sequence.get(i);
    }

    // Retorna o tamanho da sequência.
    public int getSequenceSize() {
        return this.sequenceSize;
    }

    // Retorna o número de cores disponíveis na dificuldade escolhida.
    public int getDifficultyColors() {
        return this.difficultyColors;
    }

    // Função que recebe um número representando uma cor que foi clicada pelo usuário e verifica
    // se foi a cor correta da sequência. Caso seja, calcula a quantidade de pontos ganhos
    // para retorná-la, reinicia o timer e atualiza os valores associados à sequência.
    public int tryColor(int number) {
        int points = 0;

        if(number == sequence.get(sequenceIterator)) {
            points = Math.max((Constants.MAX_POINTS_PER_PLAY - (int)(this.timer * this.pointsLostPerSec)), Constants.MIN_POINTS_PER_PLAY);
            this.timer = 0;
            this.colorsLeft -= 1;
            this.sequenceIterator += 1;
        }

        return points;
    }

    // Aumenta o tamanho da sequência.
    public void incrementSequenceSize() {
        this.sequenceSize++;
    }

    // Aumenta o timer de acordo com o tempo passado.
    public void incrementTimer(float delta) {
        this.timer += delta;
    }

    // Retorna se não falta mais nenhuma cor na sequência, ou seja, se o usuário já terminou a sequência.
    public boolean noColorsLeft() {
        return this.colorsLeft == 0;
    }
}
