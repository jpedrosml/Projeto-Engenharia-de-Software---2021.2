package com.bb.game.minigames;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.bb.game.utils.Constants;
import com.bb.game.utils.Difficulty;

import java.security.SecureRandom;

public class AimGraphics extends MiniGameGraphics{

    private Target target;
    private AimLogic logic;
    private Image bean;
    private float distance;
    private final SequenceAction sequence = new SequenceAction();

    private static final float BEAN_ORIGINAL_X = Constants.WORLD_WIDTH * 0.45f;
    private static final float BEAN_ORIGINAL_Y = Constants.WORLD_HEIGHT * -0.1f;
    private static final float BEAN_VELOCITY = 4000f;
    private static final Texture beanTexture = new Texture("aim\\bean.png");
    private static final Texture standGuyTexture = new Texture("aim\\stand_guy.png");
    private static final Texture counterTexture = new Texture("aim\\counter.png");
    private static final SecureRandom random = new SecureRandom();
    private static final Texture backgroundTexture = new Texture("aim\\background.png");

    public AimGraphics(Difficulty difficulty){
        this.logic = new AimLogic(difficulty);
        this.target = new Target(logic.getRadius());
        this.bean = new Image(beanTexture);
        this.bean.setBounds(BEAN_ORIGINAL_X, BEAN_ORIGINAL_Y, Constants.WORLD_HEIGHT * 0.1f, Constants.WORLD_HEIGHT * 0.1f);
        this.bean.setOrigin(bean.getOriginX() + bean.getImageWidth()/2, bean.getOriginY() + bean.getImageHeight());
        this.bean.setTouchable(Touchable.disabled);
        reposition();
        setUpStage();
    }

    private void reposition(){
        float x;
        float y;
        x = random.nextInt((int) (Constants.WORLD_WIDTH * 0.84f  - logic.getRadius()*2));
        y = random.nextInt((int) (Constants.WORLD_HEIGHT - logic.getRadius() * 2));
        this.target.setPosition(x, y);
    }

    private void setUpActors() {
        Actor background = new Image(backgroundTexture);
        background.setBounds(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getStage().addActor(background);
        getStage().addActor(target);
        getStage().addActor(bean);
        Actor standGuy = new Image(standGuyTexture);
        standGuy.setBounds(Constants.WORLD_WIDTH * 0.75f, Constants.WORLD_HEIGHT * -0.2f, Constants.WORLD_WIDTH * 0.3f, Constants.WORLD_HEIGHT * 0.8f);
        standGuy.setTouchable(Touchable.disabled);
        getStage().addActor(standGuy);
        Actor counter = new Image(counterTexture);
        counter.setBounds(0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT * 0.13f);
        counter.setTouchable(Touchable.disabled);
        getStage().addActor(counter);
    }

    private void setUpStage() {
        setUpActors();
        setUpPanel();
        getStage().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(bean.getX() == BEAN_ORIGINAL_X && bean.getY() == BEAN_ORIGINAL_Y) {
                    beanAnimation(x, y);
                    if (event.getTarget() instanceof Target) {
                        Target t = (Target) event.getTarget();
                        if (t.inTarget(x, y)) {
                            t.setTouchable(Touchable.disabled);
                            updateScore(logic.scorePerHit(t.distanceToCenter(x, y)));
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    reposition();
                                    t.setTouchable(Touchable.enabled);
                                }
                            }, distance / BEAN_VELOCITY);
                        }
                    }
                }
            }
        });
    }

    private void beanAnimation(float x, float y){
        distance = (float) Math.hypot(Math.abs(BEAN_ORIGINAL_X - x), Math.abs(BEAN_ORIGINAL_Y - y));
        sequence.reset();
        sequence.addAction(Actions.parallel(Actions.moveTo(x, y, distance/BEAN_VELOCITY),
                           Actions.rotateBy(360f, distance/BEAN_VELOCITY),
                           Actions.scaleBy(-0.7f, -0.7f, distance/BEAN_VELOCITY)));
        sequence.addAction(Actions.moveTo(BEAN_ORIGINAL_X, BEAN_ORIGINAL_Y));
        sequence.addAction(Actions.scaleTo(1f, 1f));
        bean.addAction(sequence);
    }

}
