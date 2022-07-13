package com.bb.game.minigames;

public class MiniGameFactory {
    public enum Type{
        MEMORY, SEQUENCE, AIM, CHIMP
    }

    public static MiniGameGraphics createMiniGame(Type type, Difficulty difficulty){
        MiniGameGraphics miniGame;
        switch (type){
            case MEMORY:
                miniGame = createMemory(difficulty);
                break;
            case SEQUENCE:
                miniGame = createSequence(difficulty);
                break;
            case AIM:
                miniGame = createAim(difficulty);
                break;
            case CHIMP:
                miniGame = createChimp(difficulty);
                break;
            default:
                break;
        }
        return null;
    }

    private static MiniGameGraphics createChimp(Difficulty difficulty) {
        return null;
    }

    private static MiniGameGraphics createAim(Difficulty difficulty) {
        return null;
    }

    private static MiniGameGraphics createSequence(Difficulty difficulty) {
        return null;
    }

    private static MiniGameGraphics createMemory(Difficulty difficulty) {
        return null;
    }
}
