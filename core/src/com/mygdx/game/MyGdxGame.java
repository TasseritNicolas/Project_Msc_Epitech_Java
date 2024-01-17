package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
//ajout
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.awt.*;
import java.util.ArrayList;
import com.badlogic.gdx.utils.Timer.Task;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.audio.Music;

public class MyGdxGame extends ApplicationAdapter {
    //
    List<Cloud> clouds;
    List<Coin> coins;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final float WORLD_WIDTH = (float) screenSize.getWidth();
    private static final float WORLD_HEIGHT = (float) screenSize.getHeight();


    private SpriteBatch batch;
    private Texture spriteSheet, monsterSheet_1, monsterSheet_2, monsterSheet_3;
    private OrthographicCamera camera;
    private Stage stage;
    private BitmapFont font;
    private Label attackLabel, coordonnees, haveSwordLabel, infoHeroLabel, maxPotionLabel, lifeLabel, moneyLabel;

    private Animation<TextureRegion> walkUpAnimation, walkDownAnimation,
            walkLeftAnimation, walkRightAnimation,
            notWalkingAnimation, attackUpAnimation,
            attackLeftAnimation, attackRightAnimation,
            attackDownAnimation;

    private TextureRegion currentFrame;
    private float stateTime, x, y;
    private boolean isMoving, isMovingLeft;
    private Hero hero;
    private MonsterNormal monster_1, monster_2, monster_3;
    private Viewport viewport;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Animation<TextureRegion> monsterWalkUpAnimation;
    private Animation<TextureRegion> monsterWalkDownAnimation;
    private Animation<TextureRegion> monsterWalkRightAnimation;
    private Animation<TextureRegion> monsterNotWalkingAnimation;
    private Animation<TextureRegion> monsterAttackUpAnimation;
    private Animation<TextureRegion> monsterAttackLeftAnimation;
    private Animation<TextureRegion> monsterAttackRightAnimation;
    private Animation<TextureRegion> monsterAttackDownAnimation;
    private Animation<TextureRegion> monsterAttackedAnimation;
    private Animation<TextureRegion> monsterDeadAnimation;
    private Animation<TextureRegion> monsterWalkUpAnimation_;
    private Animation<TextureRegion> monsterWalkDownAnimation_;
    private Animation<TextureRegion> monsterWalkRightAnimation_;
    private Animation<TextureRegion> monsterNotWalkingAnimation_;
    private Animation<TextureRegion> monsterAttackUpAnimation_;
    private Animation<TextureRegion> monsterAttackLeftAnimation_;
    private Animation<TextureRegion> monsterAttackRightAnimation_;
    private Animation<TextureRegion> monsterAttackDownAnimation_;
    private Animation<TextureRegion> monsterAttackedAnimation_;
    private Animation<TextureRegion> monsterDeadAnimation_;
    private TextureRegion monsterCurrentFrame, monsterCurrentFrame_;
    private Vector2 monsterPosition, monsterPosition_2, monsterPosition_3;
    private float monsterSpeed;
    public boolean isMonsterAttacking, isMonsterMovingLeft, isHeroAttacked, isMonsterVisible, isMonsterAttacked;
    public boolean isMonsterAttacking_, isMonsterMovingLeft_, isHeroAttacked_, isMonsterVisible_, isMonsterAttacked_;
    private float attackRange;
    private ShapeRenderer shapeRenderer;
    private Vector2 heroCenter;
    private Vector2 monsterCenter;
    private float timeSinceLastAttack = 0f;
    private static final float ATTACK_COOLDOWN = 2f; // 2 seconds
    private boolean isMonsterDead = false;
    private float monsterDeadTime = 0f;
    // private  Vector2 statuePosition;
    // private float statueInteractionRadius;
    // private boolean isNearStatue;
    private Label dialogueLabel;

    private enum MonsterState {
        ALIVE,
        DYING,
        DEAD
    }
    int counterDialog = 0;

    private MonsterState monsterState = MonsterState.ALIVE;
    private static boolean textStatus = false;
    private AnimatedText statueText,storeText;
    private Music currentMusic;
    public void start() {

        Music music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.play();
        music.setVolume(0.1f);
        music.setLooping(true);
    }
    private void playMusic(String musicFileName, float volume) {
        // Stop the current music if it's playing
        System.out.println("Changing music to: " + musicFileName);

        // Stop and dispose current music if it's playing
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.stop();
            currentMusic.dispose();
        }

        // Start new music
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(musicFileName));
        currentMusic.setVolume(volume);
        currentMusic.setLooping(true);
        currentMusic.play();
    }

    @Override
    public void create() {
        playMusic("music.mp3", 0.2f);
        isMonsterVisible = false;
        isMonsterVisible_ = false;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        attackRange = -10f;
        statueText = new AnimatedText("Clique sur T pour commencer", 350, 549, 0.1f, new BitmapFont());
        storeText=new AnimatedText("Clique sur P pour commencer", 638, 350, 0.1f, new BitmapFont());
        x = 308f;
        y = 498f;
        hero = new Hero(x, y, "Hero");
        monster_1 = new MonsterNormal(100, 1, 10, 0);
        monster_2 = new MonsterNormal(100, 1, 10, 0);
        monster_3 = new MonsterNormal(100, 1, 10, 0);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        camera.zoom = 0.5f; // Smaller values zoom in
        camera.update();
        spriteSheet = new Texture("player.png");
        monsterSheet_1 = new Texture("monster1.png");
        // System.out.println(spriteSheet);
        monsterSheet_2 = new Texture("monster2.png");
        monsterSheet_3 = new Texture("monster3.png");
        monsterPosition = new Vector2(914f, 678f);
        monsterPosition_2 = new Vector2(914f, 678f);
        monsterPosition_3 = new Vector2(914f, 678f);
        monsterSpeed = 20;
        initializeAnimationsHero();
        initializeAnimationsMonster_1();
        initializeAnimationsMonster_2();
        // initializeAnimationsMonster_3();
        // !
        clouds = new ArrayList<>();
        coins = new ArrayList<>();
        Texture cloudTexture = new Texture("Texture/cloud1.png");
        Random r = new Random();
        float y = r.nextFloat(0f, Gdx.graphics.getWidth());
        clouds.add(new Cloud(Gdx.graphics.getWidth() * -1, y));
        clouds.add(new Cloud(Gdx.graphics.getWidth() * -1, y));

        // !
        font = new BitmapFont();
        LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
        LabelStyle labelStyleRED = new LabelStyle(font, Color.RED);
        LabelStyle labelStyleYELLOW = new LabelStyle(font, Color.YELLOW);
        attackLabel = new Label("I'm attacking!", labelStyle);
        haveSwordLabel = new Label("I don't have a sword!", labelStyle);
        infoHeroLabel = new Label("Life:" + hero.getLife() +",Lvl:" + hero.getLvl() + ",XP:" + hero.getXp()
                + ",Potion:" + hero.getPotion() + ",Money:" + hero.getMoney() + ",Sword:" + hero.haveSword(),
                labelStyle);
        maxPotionLabel = new Label("You can't have more than 3 potions!", labelStyle);
        lifeLabel = new Label("", labelStyleRED);
        moneyLabel = new Label("",labelStyleYELLOW);
        attackLabel.setPosition(100, 100);
        haveSwordLabel.setPosition(100, 100);
        maxPotionLabel.setPosition(100, 100);
        lifeLabel.setPosition(screenSize.width - 50, 100);
        infoHeroLabel.setPosition(50,1000);
        attackLabel.setVisible(false);
        lifeLabel.setVisible(false);
        haveSwordLabel.setVisible(false);
        maxPotionLabel.setVisible(false);
        coordonnees = new Label("x: " + x + " y: " + y, labelStyle);
        coordonnees.setPosition(100, 200);
        coordonnees.setVisible(true);
        stage.addActor(attackLabel);
        stage.addActor(haveSwordLabel);
        stage.addActor(infoHeroLabel);
        stage.addActor(maxPotionLabel);
        stage.addActor(coordonnees);
        stage.addActor(lifeLabel);
        tiledMap = new TmxMapLoader().load("MAPV3.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        heroCenter = new Vector2(x + (float) 0 / 2, y + (float) 0 / 2);
       // start();
    }

    private void updateMonster(float deltaTime) {
        lifeLabel.setText("Monster life:"+monster_1.getLife());
        if(monster_1.getLife()>0){
            if(hero.haveSword()) {
                lifeLabel.setVisible(true);
            }
        } else {
            lifeLabel.setVisible(false);
        }
        final float heroFrameWidth = 0;
        final float heroFrameHeight = 0;
        final float monsterFrameWidth = monsterCurrentFrame.getRegionWidth() - 10;
        final float monsterFrameHeight = monsterCurrentFrame.getRegionHeight() - 50;
        Animation<TextureRegion> currentHeroAnimation = walkDownAnimation;
        TextureRegion heroFrame = currentHeroAnimation.getKeyFrame(stateTime, true);
        heroCenter = new Vector2(x + heroFrameWidth / 2, y + heroFrameHeight / 2);
        monsterCenter = new Vector2(monsterPosition.x + monsterFrameWidth / 2,
                monsterPosition.y + monsterFrameHeight / 2);

        float stopDistance = 5;// heroFrameWidth / 2 + monsterFrameWidth / 2;
        switch (monsterState) {
            case ALIVE:
                // Logic for a living monster (movement, initiating attacks, etc.)
                if (monster_1.getLife() <= 0) {
                    monsterState = MonsterState.DYING;
                }
                break;

            case DYING:
                // Logic for when the monster is dying (playing dead animation)
                monsterCurrentFrame = monsterDeadAnimation.getKeyFrame(monsterDeadTime, false);
                monsterDeadTime += deltaTime;
                if (monsterDeadAnimation.isAnimationFinished(monsterDeadTime)) {
                    monsterState = MonsterState.DEAD;
                    monsterDeadTime = 0; // Reset for next use
                    for (int i =0; i  < (int)(Math.random()*5);i++){
                        coins.add(new Coin(x,y));
                    }
                }
                break;

            case DEAD:
                isMonsterVisible = false;
                // Disable monster's attack here
                isHeroAttacked = false;
                isMonsterAttacking = false; // Add this line
                break;
        }

        // Calculate the direction vector from monster to hero

        Vector2 direction = new Vector2(heroCenter.x - monsterCenter.x, heroCenter.y - monsterCenter.y);
        if (!inCombatZone()) {
            // System.out.println("Combat zone");
            updateMonsterAnimation(direction, deltaTime);
            return;
        }
        if (hero.haveSword() && isMonsterVisible && monsterState != MonsterState.DEAD) {
            if (direction.len() >= stopDistance) { // Only move if the distance is greater than the stop distance
                direction.nor();

                monsterCenter.add(direction.scl(monsterSpeed * deltaTime));
                monsterPosition.set(monsterCenter.x - monsterFrameWidth / 2,
                        monsterCenter.y - monsterFrameHeight / 2);
                isMonsterAttacking = false;

            } else {
                if (timeSinceLastAttack >= ATTACK_COOLDOWN) {
                    // Perform attack
                    isMonsterAttacking = true;
                    isHeroAttacked = true;
                    performMonsterAttack(direction);
                    timeSinceLastAttack = 0f; // Reset the timer after the attack
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            isHeroAttacked = false;
                        }
                    }, 1f);
                } else {
                    isMonsterAttacking = false;
                }
                // isMonsterAttacking = true;
                // performMonsterAttack(direction);

            }

        }
        updateMonsterAnimation(direction, deltaTime);
        // updateMonsterAnimation_(direction, deltaTime);
        // Update the animation frames based on the monster's state

    }

    private void monsterCheck(float centerX, float centerY, float memHeight, float memWeight) {
        TiledMapTileLayer MonsterCollision = (TiledMapTileLayer) tiledMap.getLayers().get("Collision_Monster");
        TiledMapTileLayer.Cell cell = MonsterCollision.getCell(
                (int) ((monsterCenter.x) / MonsterCollision.getTileWidth()),
                (int) (monsterCenter.y / MonsterCollision.getTileHeight()));
        if (cell != null) {
            monsterPosition.set(centerX - memWeight / 2 + 1, centerY - memHeight / 2 + 1);
        }
        MonsterCollision = (TiledMapTileLayer) tiledMap.getLayers().get("Back_Collision");
        cell = MonsterCollision.getCell((int) ((monsterCenter.x) / MonsterCollision.getTileWidth()),
                (int) (monsterCenter.y / MonsterCollision.getTileHeight()));
        if (cell != null) {
            monsterPosition.set(centerX - memWeight / 2, centerY - memHeight / 2 - 1);
        }

    }

    private boolean inCombatZone() {
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("combat_zone");
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) ((x) / collisionLayer.getTileWidth()),
                (int) (y / collisionLayer.getTileHeight()));
        return cell != null;
    }
    private boolean inStoreZone() {
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("combat_zone");
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) ((x) / collisionLayer.getTileWidth()),
                (int) (y / collisionLayer.getTileHeight()));
        return cell != null;
    }

    private boolean isNearStatue() {
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Collision_Statue");
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) ((x) / collisionLayer.getTileWidth()),
                (int) (y / collisionLayer.getTileHeight()));
        return cell != null;
    }
    private void updateMonster_2(float deltaTime) {
        final float heroFrameWidth = 0;
        final float heroFrameHeight = 0;
        final float monsterFrameWidth = monsterCurrentFrame_.getRegionWidth() - 10;
        final float monsterFrameHeight = monsterCurrentFrame_.getRegionHeight() - 50;
        Animation<TextureRegion> currentHeroAnimation = walkDownAnimation;
        TextureRegion heroFrame = currentHeroAnimation.getKeyFrame(stateTime, true);
        heroCenter = new Vector2(x + heroFrameWidth / 2, y + heroFrameHeight / 2);
        monsterCenter = new Vector2(monsterPosition_2.x + monsterFrameWidth / 2,
                monsterPosition_2.y + monsterFrameHeight / 2);

        float stopDistance = 5;// heroFrameWidth / 2 + monsterFrameWidth / 2;
        switch (monsterState) {
            case ALIVE:
                // Logic for a living monster (movement, initiating attacks, etc.)
                if (monster_2.getLife() <= 0) {
                    monsterState = MonsterState.DYING;
                }
                break;

            case DYING:
                // Logic for when the monster is dying (playing dead animation)
                monsterCurrentFrame = monsterDeadAnimation.getKeyFrame(monsterDeadTime, false);
                monsterDeadTime += deltaTime;
                if (monsterDeadAnimation.isAnimationFinished(monsterDeadTime)) {
                    monsterState = MonsterState.DEAD;
                    monsterDeadTime = 0; // Reset for next use
                }
                break;

            case DEAD:
                isMonsterVisible_ = false;
                // Disable monster's attack here
                isHeroAttacked_ = false;
                isMonsterAttacking_ = false; // Add this line
                break;
        }

        // Calculate the direction vector from monster to hero

        Vector2 direction = new Vector2(heroCenter.x - monsterCenter.x, heroCenter.y - monsterCenter.y);
        if (hero.haveSword() && isMonsterVisible && monsterState != MonsterState.DEAD) {
            if (direction.len() >= stopDistance) { // Only move if the distance is greater than the stop distance
                direction.nor();

                monsterCenter.add(direction.scl(monsterSpeed * deltaTime));
                monsterPosition_2.set(monsterCenter.x - monsterFrameWidth / 2,
                        monsterCenter.y - monsterFrameHeight / 2);
                isMonsterAttacking_ = false;

            } else {
                if (timeSinceLastAttack >= ATTACK_COOLDOWN) {
                    // Perform attack
                    isMonsterAttacking_ = true;
                    isHeroAttacked_ = true;
                    performMonsterAttack_2(direction);
                    timeSinceLastAttack = 0f; // Reset the timer after the attack
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            isHeroAttacked_ = false;
                        }
                    }, 1f);
                } else {
                    isMonsterAttacking_ = false;
                }
                // isMonsterAttacking = true;
                // performMonsterAttack(direction);

            }

        }
        updateMonsterAnimation_2(direction, deltaTime);
        // updateMonsterAnimation_(direction, deltaTime);
        // Update the animation frames based on the monster's state

    }

    private void performMonsterAttack(Vector2 direction) {
        if (monsterState != MonsterState.ALIVE)
            return;
        if (isMonsterAttacking) {
            System.out.println("Monster is attacking!");
            monster_1.dealElement(hero);
            // You need to select the correct attack animation based on the direction
            if (Math.abs(direction.x) > Math.abs(direction.y)) {
                // Monster is attacking more horizontally
                if (direction.x > 0) {
                    monsterCurrentFrame = monsterAttackRightAnimation.getKeyFrame(stateTime, true);
                } else {
                    monsterCurrentFrame = monsterAttackLeftAnimation.getKeyFrame(stateTime, true);
                }
            } else {
                // Monster is attacking more vertically
                if (direction.y > 0) {
                    monsterCurrentFrame = monsterAttackUpAnimation.getKeyFrame(stateTime, true);
                } else {
                    monsterCurrentFrame = monsterAttackDownAnimation.getKeyFrame(stateTime, true);
                }
            }
        }
    }

    private void performMonsterAttack_2(Vector2 direction) {
        if (monsterState != MonsterState.ALIVE)
            return;
        if (isMonsterAttacking_) {
            System.out.println("Monster is attacking!");
            monster_2.dealElement(hero);
            // You need to select the correct attack animation based on the direction
            if (Math.abs(direction.x) > Math.abs(direction.y)) {
                // Monster is attacking more horizontally
                if (direction.x > 0) {
                    monsterCurrentFrame_ = monsterAttackRightAnimation_.getKeyFrame(stateTime, true);
                } else {
                    monsterCurrentFrame_ = monsterAttackLeftAnimation_.getKeyFrame(stateTime, true);
                }
            } else {
                // Monster is attacking more vertically
                if (direction.y > 0) {
                    monsterCurrentFrame_ = monsterAttackUpAnimation_.getKeyFrame(stateTime, true);
                } else {
                    monsterCurrentFrame_ = monsterAttackDownAnimation_.getKeyFrame(stateTime, true);
                }
            }
        }
    }

    private void updateMonsterAnimation(Vector2 direction, float deltaTime) {
        if (!hero.haveSword() || !inCombatZone()) {
            //System.out.println("Combat zone11");
            monsterCurrentFrame = monsterNotWalkingAnimation.getKeyFrame(stateTime, true);
            return;
        }
        if (isHeroAttacked) {
            if (direction.x > 0) {
                monsterCurrentFrame = monsterAttackRightAnimation.getKeyFrame(stateTime, true);
            } else {
                monsterCurrentFrame = monsterAttackLeftAnimation.getKeyFrame(stateTime, true);
            }
            return;
        }
        if (isMonsterAttacked) {
            monsterCurrentFrame = monsterAttackedAnimation.getKeyFrame(stateTime, true);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    isMonsterAttacked = false;
                }
            }, 1f);
            isMonsterMovingLeft = direction.x < 0;
            if (monster_1.getLife() <= 0) {
                monsterCurrentFrame = monsterDeadAnimation.getKeyFrame(stateTime, true);
                isMonsterVisible = false;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        isMonsterVisible = false;
                    }
                }, 1f);
                return;
            }
            return;
        }

        if (Math.abs(direction.x) > Math.abs(direction.y)) {
            // Monster is moving more horizontally
            // System.out.println("Monster is moving horizontally!" + direction.x);
            monsterCurrentFrame = monsterWalkRightAnimation.getKeyFrame(stateTime, true);
            isMonsterMovingLeft = direction.x < 0;
        } else {
            // Monster is moving more vertically
            isMonsterMovingLeft = false;
            monsterCurrentFrame = direction.y > 0 ? monsterWalkUpAnimation.getKeyFrame(stateTime, true)
                    : monsterWalkDownAnimation.getKeyFrame(stateTime, true);
        }
    }

    private void updateMonsterAnimation_2(Vector2 direction, float deltaTime) {
        if (!hero.haveSword()) {
            monsterCurrentFrame_ = monsterNotWalkingAnimation_.getKeyFrame(stateTime, true);
            return;
        }
        if (isHeroAttacked) {
            if (direction.x > 0) {
                monsterCurrentFrame_ = monsterAttackRightAnimation_.getKeyFrame(stateTime, true);
            } else {
                monsterCurrentFrame_ = monsterAttackLeftAnimation_.getKeyFrame(stateTime, true);
            }
            return;
        }
        if (isMonsterAttacked) {
            monsterCurrentFrame_ = monsterAttackedAnimation_.getKeyFrame(stateTime, true);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    isMonsterAttacked_ = false;
                }
            }, 1f);
            isMonsterMovingLeft_ = direction.x < 0;
            if (monster_2.getLife() <= 0) {
                monsterCurrentFrame_ = monsterDeadAnimation_.getKeyFrame(stateTime, true);
                isMonsterVisible_ = false;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        isMonsterVisible_ = false;
                    }
                }, 1f);
                return;
            }
            return;
        }

        if (Math.abs(direction.x) > Math.abs(direction.y)) {
            // Monster is moving more horizontally
            // System.out.println("Monster is moving horizontally!" + direction.x);
            monsterCurrentFrame_ = monsterWalkRightAnimation_.getKeyFrame(stateTime, true);
            isMonsterMovingLeft_ = direction.x < 0;
        } else {
            // Monster is moving more vertically
            isMonsterMovingLeft = false;
            monsterCurrentFrame_ = direction.y > 0 ? monsterWalkUpAnimation_.getKeyFrame(stateTime, true)
                    : monsterWalkDownAnimation_.getKeyFrame(stateTime, true);
        }
    }

    private void heroAttack() {
        if (monsterState != MonsterState.ALIVE || !isMonsterVisible)
            return;
        final float heroFrameWidth = 0; // Replace with actual width of hero's frame
        final float heroFrameHeight = 0; // Replace with actual height of hero's frame

        // Assume that you have the dimensions of the monster's frame.
        final float monsterFrameWidth = monsterCurrentFrame.getRegionWidth() - 10;
        final float monsterFrameHeight = monsterCurrentFrame.getRegionHeight() - 50;
        Animation<TextureRegion> currentHeroAnimation = walkDownAnimation;
        TextureRegion heroFrame = currentHeroAnimation.getKeyFrame(stateTime, true);
        heroCenter = new Vector2(x + heroFrameWidth / 2, y + heroFrameHeight / 2);
        monsterCenter = new Vector2(monsterPosition.x + monsterFrameWidth / 2,
                monsterPosition.y + monsterFrameHeight / 2);

        // This is the distance you want the monster to stop at -- adjust accordingly
        float stopDistance = 5;// heroFrameWidth / 2 + monsterFrameWidth / 2;
        // Calculate the distance to the monster
        Vector2 direction = new Vector2(heroCenter.x - monsterCenter.x, heroCenter.y - monsterCenter.y);
        // Check if the monster is within attack range
        if (direction.len() >= stopDistance) { // Only move if the distance is greater than the stop distance
            direction.nor(); // Normalize to get direction
            // Move the monster towards the hero
            monsterCenter.add(direction.scl(monsterSpeed * Gdx.graphics.getDeltaTime()));
            // System.out.println("Monster is moving!" + direction.len());
            // Update the monster's position
            // monsterPosition.set(monsterCenter.x - monsterFrameWidth / 2, monsterCenter.y - monsterFrameHeight / 2);

        } else {
            System.out.println("I'm attacking!");
            hero.dealDamage(monster_1);
            isMonsterAttacked = true;
            updateMonsterVisibility(monster_1);
            // isMonsterAttacking = true;
            // performMonsterAttack(direction);

        }
    }

    private void updateMonsterVisibility(MonsterNormal monster) {
        if (monster.getLife() > 0) {
            isMonsterVisible = true;
        } else {
            isMonsterVisible = false;
        }
    }

    private void updateMonsterVisibility_2(MonsterNormal monster) {
        if (monster.getLife() > 0) {
            isMonsterVisible_ = true;
        } else {
            isMonsterVisible_ = false;
        }
    }

    private void initializeAnimationsHero() {
        int frameCols = 6, frameRows = 9;
        TextureRegion[][] tmpFrames = TextureRegion.split(spriteSheet,
                spriteSheet.getWidth() / frameCols,
                spriteSheet.getHeight() / frameRows);

        walkUpAnimation = new Animation<>(0.1f, tmpFrames[1]);
        walkDownAnimation = new Animation<>(0.1f, tmpFrames[3]);
        walkLeftAnimation = new Animation<>(0.1f, tmpFrames[2]);
        walkRightAnimation = new Animation<>(0.1f, tmpFrames[2]);
        notWalkingAnimation = new Animation<>(0.1f, tmpFrames[6]);
        attackUpAnimation = new Animation<>(0.1f, tmpFrames[7]);
        attackLeftAnimation = new Animation<>(0.1f, tmpFrames[8]);
        attackRightAnimation = new Animation<>(0.1f, tmpFrames[8]);
        attackDownAnimation = new Animation<>(0.1f, tmpFrames[0]);
        currentFrame = walkDownAnimation.getKeyFrame(0);
    }

    private void initializeAnimationsMonster_1() {
        int frameCols = 8, frameRows = 5;
        TextureRegion[][] tmpFrames = TextureRegion.split(monsterSheet_1,
                monsterSheet_1.getWidth() / frameCols,
                monsterSheet_1.getHeight() / frameRows);

        monsterWalkUpAnimation = new Animation<>(0.1f, tmpFrames[4]);
        monsterWalkDownAnimation = new Animation<>(0.1f, tmpFrames[4]);
        Animation<TextureRegion> monsterWalkLeftAnimation = new Animation<>(0.1f, tmpFrames[4]);
        monsterWalkRightAnimation = new Animation<>(0.1f, tmpFrames[4]);
        monsterNotWalkingAnimation = new Animation<>(0.1f, tmpFrames[0]);
        monsterAttackUpAnimation = new Animation<>(0.1f, tmpFrames[3]);
        monsterAttackLeftAnimation = new Animation<>(0.1f, tmpFrames[3]);
        monsterAttackRightAnimation = new Animation<>(0.1f, tmpFrames[3]);
        monsterAttackDownAnimation = new Animation<>(0.1f, tmpFrames[3]);
        monsterAttackedAnimation = new Animation<>(0.1f, tmpFrames[2]);
        monsterDeadAnimation = new Animation<>(0.1f, tmpFrames[1]);
        monsterCurrentFrame = monsterWalkUpAnimation.getKeyFrame(0);
    }

    private void initializeAnimationsMonster_2() {
        int frameCols = 8, frameRows = 6;
        TextureRegion[][] tmpFrames = TextureRegion.split(monsterSheet_2,
                monsterSheet_2.getWidth() / frameCols,
                monsterSheet_2.getHeight() / frameRows);

        monsterWalkUpAnimation_ = new Animation<>(0.1f, tmpFrames[3]);
        monsterWalkDownAnimation_ = new Animation<>(0.1f, tmpFrames[3]);
        Animation<TextureRegion> monsterWalkLeftAnimation_ = new Animation<>(0.1f, tmpFrames[1]);
        monsterWalkRightAnimation_ = new Animation<>(0.1f, tmpFrames[3]);
        monsterNotWalkingAnimation_ = new Animation<>(0.1f, tmpFrames[2]);
        monsterAttackUpAnimation_ = new Animation<>(0.1f, tmpFrames[1]);
        monsterAttackLeftAnimation_ = new Animation<>(0.1f, tmpFrames[1]);
        monsterAttackRightAnimation_ = new Animation<>(0.1f, tmpFrames[1]);
        monsterAttackDownAnimation_ = new Animation<>(0.1f, tmpFrames[1]);
        monsterAttackedAnimation_ = new Animation<>(0.1f, tmpFrames[5]);
        monsterDeadAnimation_ = new Animation<>(0.1f, tmpFrames[4]);
        monsterCurrentFrame_ = monsterWalkUpAnimation_.getKeyFrame(0);
    }

    private void initializeAnimationsMonster_3() {
        int frameCols = 8, frameRows = 7;
        TextureRegion[][] tmpFrames = TextureRegion.split(monsterSheet_3,
                monsterSheet_3.getWidth() / frameCols,
                monsterSheet_3.getHeight() / frameRows);

        walkUpAnimation = new Animation<>(0.1f, tmpFrames[6]);
        walkDownAnimation = new Animation<>(0.1f, tmpFrames[6]);
        walkLeftAnimation = new Animation<>(0.1f, tmpFrames[2]);
        walkRightAnimation = new Animation<>(0.1f, tmpFrames[6]);
        notWalkingAnimation = new Animation<>(0.1f, tmpFrames[3]);
        attackUpAnimation = new Animation<>(0.1f, tmpFrames[5]);
        attackLeftAnimation = new Animation<>(0.1f, tmpFrames[4]);
        attackRightAnimation = new Animation<>(0.1f, tmpFrames[1]);
        attackDownAnimation = new Animation<>(0.1f, tmpFrames[0]);
        currentFrame = walkDownAnimation.getKeyFrame(0);
    }

    private void Coordonnees() {
        coordonnees.setText("x: " + x + " y: " + y);
        infoHeroLabel.setText("Life:" + hero.getLife() + ",Lvl:" + hero.getLvl() + ",XP:" + hero.getXp() + ",Potion:"
                + hero.getPotion() + ",Sword:" + hero.haveSword());
    }

    public Vector2 getCharacterPosition() {
        return new Vector2(x, y);
    }

    private void handleInput() {
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean isAttacking = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        boolean addSword = Gdx.input.isKeyJustPressed(Input.Keys.A);
        boolean addPotion = Gdx.input.isKeyJustPressed(Input.Keys.Z);
        boolean buyPotion=Gdx.input.isKeyJustPressed(Input.Keys.P);
        boolean heal = Gdx.input.isKeyJustPressed(Input.Keys.X);
        boolean haveSword = hero.haveSword();
        boolean nextDialog = Gdx.input.isKeyJustPressed(Input.Keys.T);

        // boolean Speak = Gdx.input.isKeyJustPressed(Input.Keys.E);
        if (monster_1.getLife() <= 0 && counterDialog > 50) {
            // statueText.changeText("(T pour continuer)");

            counterDialog = 11;
        }
        if(buyPotion){
            if (hero.getPotion()>=3){
                storeText.changeText("You can't have more than 3 potions(X to Heal)");

            }else if(hero.getMoney()==0){
                storeText.changeText("You have No Money");
            }
            else{
                storeText.changeText("the potion coast 10 coins.(X to Heal)");
                hero.addPotion();
                hero.setMoney(hero.getMoney()-10);

            }
        }
        else if(heal){
            if(hero.getLife()<hero.getMaxLife() && hero.getPotion() > 0){
                hero.setLife(hero.getLife()+10);
                hero.setPotion(hero.getPotion()-1);
            }
            if(hero.getLife()>hero.getMaxLife()){
                hero.setLife(hero.getMaxLife());
            }
        } else if (upPressed) {
            y += 2;
            CheckCollision("up");
            isMoving = true;
            currentFrame = (isAttacking && haveSword) ? attackUpAnimation.getKeyFrame(stateTime, true)
                    : walkUpAnimation.getKeyFrame(stateTime, true);
            Coordonnees();
            System.out.println(getCharacterPosition());
            heroAttack();
        } else if (downPressed) {
            y -= 2;
            CheckCollision("down");
            isMoving = true;
            Coordonnees();
            currentFrame = (isAttacking && haveSword) ? attackDownAnimation.getKeyFrame(stateTime, true)
                    : walkDownAnimation.getKeyFrame(stateTime, true);
            heroAttack();
        } else if (leftPressed) {
            x -= 2;
            CheckCollision("left");
            isMovingLeft = true; // This flag determines the direction character is facing
            isMoving = true;
            Coordonnees();
            currentFrame = (isAttacking && haveSword) ? attackLeftAnimation.getKeyFrame(stateTime, true)
                    : walkLeftAnimation.getKeyFrame(stateTime, true);
            heroAttack();
        } else if (rightPressed) {
            x += 2;
            CheckCollision("right");
            isMovingLeft = false; // Character is moving right
            isMoving = true;
            Coordonnees();
            currentFrame = (isAttacking && haveSword) ? attackRightAnimation.getKeyFrame(stateTime, true)
                    : walkRightAnimation.getKeyFrame(stateTime, true);
            heroAttack();
            // } else if (Speak) {
            //     showDialogue();


        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

            currentFrame = (isAttacking && haveSword) ? attackDownAnimation.getKeyFrame(stateTime, true)
                    : notWalkingAnimation.getKeyFrame(stateTime, true);
            isMoving = true;
            isMovingLeft = false;
            heroAttack();
        } else if (addSword && counterDialog == 10) {
            playMusic("action.mp3", 0.2f);
            counterDialog = 999;
            System.out.println("I'm near the statue");
            hero.addSword();
            statueText.changeText("Kill the Monster First(T pour continuer)");
            counterDialog++;
        } else if (nextDialog) {
            if (isNearStatue()) {
                switch (counterDialog) {
                    case 0:
                        statueText.changeText(
                                "Status:Ô noble guerrier,(T pour continuer)");
                        counterDialog++;
                        break;
                    case 1:
                        statueText.changeText("devant le mal qui ronge ce village,(T pour continuer)");
                        counterDialog++;
                        break;
                    case 2:
                        statueText.changeText("je t'offre cette épée.(T pour continuer)");
                        counterDialog++;
                        break;
                    case 3:
                        statueText.changeText("Elle seule peut vaincre le monstre terrifiant.(T pour continuer)");
                        counterDialog++;
                        break;
                    case 4:
                        statueText.changeText("Hero:Statue ancienne,(T pour continuer)");
                        counterDialog++;
                        break;
                    case 5:
                        statueText.changeText("je prends cette arme avec honneur.(T pour continuer)");
                        counterDialog++;
                        break;
                    case 6:
                        statueText.changeText(" Je promets de libérer notre terre de cette menace.(T pour continuer)");
                        counterDialog++;
                        break;
                    case 7:
                        statueText.changeText("Status:Sois brave et juste.(T pour continuer)");
                        counterDialog++;
                        break;
                    case 8:
                        statueText.changeText(" Que la lumière guide ton chemin. (T pour continuer)");
                        counterDialog++;
                        break;
                    case 9:
                        statueText.changeText("Reviens victorieux et rends-moi l'épée,(T pour continuer)");
                        counterDialog++;
                        break;
                    case 10:
                        statueText.changeText("car son pouvoir ne doit pas être détourné.(Q pour prendre)");
                        if (hero.haveSword()) {
                            System.out.println("I'm near the statue");
                            statueText.clearText();

                        }

                        break;
                    // !!! part2
                    case 11:
                        if (hero.haveSword())
                            statueText.changeText("Hero:Ô statue sage, (T pour continuer)");
                        counterDialog++;
                        break;
                    case 12:
                        if (hero.haveSword())
                            statueText.changeText("le monstre est vaincu.(T pour continuer)");
                        counterDialog++;
                        break;
                    case 13:
                        if (hero.haveSword())
                            statueText.changeText("La paix est restaurée(T pour continuer)");
                        counterDialog++;
                        break;
                    case 14:
                        if (hero.haveSword())
                            statueText.changeText("Comme promis, je te rends cette épée sacrée. (T pour continuer)");
                        counterDialog++;
                        break;
                    case 15:
                        if (hero.haveSword())
                            statueText.changeText("Status:Bravo, noble guerrier.(T pour continuer)");
                        counterDialog++;
                        break;
                    case 16:
                        if (hero.haveSword())
                            statueText.changeText(" Mais le destin est cruel,(T pour continuer)");
                        counterDialog++;
                        break;
                    case 17:
                        if (hero.haveSword())
                            statueText.changeText("et le cycle du mal ne peut être brisé.(T pour continuer)");
                        counterDialog++;
                        break;
                    case 18:
                        if (hero.haveSword())
                            statueText.changeText(" Pour le bien de tous, je dois t'ôter la vie.(T pour continuer)");
                        counterDialog++;
                        break;
                    case 19:
                        if (hero.haveSword())
                            statueText.changeText("Hero:Quoi ? Mais... je ne comprends pas..(T pour continuer)");
                        counterDialog++;
                        break;
                    case 20:
                        if (hero.haveSword())
                            statueText.changeText("Status:C'est le prix à payer pour l'équilibre.(T pour continuer)");
                        counterDialog++;
                        break;
                    case 21:
                        if (hero.haveSword())
                            statueText.changeText("Repose en paix, héros.(T pour continuer)");
                        counterDialog++;
                        break;
                    case 22:
                        if (hero.haveSword())
                            statueText.changeText("Ta bravoure sera chantée à travers les âges.(T pour continuer)");

                        counterDialog++;

                        break;
                    case 23:
                        if (hero.haveSword())
                            Gdx.app.exit();
                        break;
                    default:
                        statueText.changeText("Kill the Monster First(T pour continuer)");
                        break;
                }

                // hero.addSword();
                // System.out.println("I'm near the statue");

            }
            // hero.addSword();
            // haveSwordLabel.setText("I have a sword!");
        } else {
            isMoving = false;
            Coordonnees();
            // If the character is not moving, you could set an idle frame
            currentFrame = notWalkingAnimation.getKeyFrame(stateTime, true);
        }


        // Handle attack label visibility
        attackLabel.setVisible(isAttacking && haveSword);
        haveSwordLabel.setVisible(isAttacking && !haveSword);
        infoHeroLabel.setVisible(true);
        maxPotionLabel.setVisible((hero.getPotion() >= 3) && Gdx.input.isKeyPressed(Input.Keys.Z));
        isMonsterVisible = hero.haveSword();
    }

    // private void showDialogue() {
    //     dialogueLabel.setText("Bonjour, jeune Héro");
    //     dialogueLabel.setVisible(true);
    // }
    // private void hideDialogue(){
    //         dialogueLabel.setVisible(false);
    //     }

    private  void initializeDialogueUI(){
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        dialogueLabel = new Label("",labelStyle);
        dialogueLabel.setSize(200, 300);
        dialogueLabel.setPosition(1000,510);
        dialogueLabel.setWrap(true);
        dialogueLabel.setVisible(false);
        stage.addActor(dialogueLabel);
    }
    private void drawCharacter() {
        if (isHeroAttacked)
            batch.setColor(Color.RED);

        if (isMovingLeft) {
            // If moving left, flip the sprite by drawing it with a negative width
            batch.draw(currentFrame, x + currentFrame.getRegionWidth(), y, -currentFrame.getRegionWidth(),
                    currentFrame.getRegionHeight());
        } else {
            // If moving right or not moving, draw the sprite normally
            batch.draw(currentFrame, x, y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
        }
        batch.setColor(Color.WHITE);
    }

    private void updateClouds() {
        Iterator<Cloud> iterator = clouds.iterator();
        while (iterator.hasNext()) {
            Cloud cloud = iterator.next();
            cloud.update();
            if (cloud.getX() > Gdx.graphics.getWidth()) {
                iterator.remove();
            }
        }
        if (Math.random() < 0.001) {
            float randomValue = getRandomFloat(Gdx.graphics.getHeight() * -1, Gdx.graphics.getHeight());
            clouds.add(new Cloud(Gdx.graphics.getWidth() * -1 - 1000, randomValue));
        }
    }
    private void updateCoins(){
        Iterator<Coin> coinIterator = coins.iterator();
        while (coinIterator.hasNext()){
            Coin coin = coinIterator.next();
            coin.setTime(coin.getTime()+1);
            if(coin.getTime()>120){
                coinIterator.remove();
                hero.setMoney(hero.getMoney()+1);
            }
        }
    }
    private void drawCoins() {
        batch.begin();
        for (Coin coin : coins) {
            batch.draw(coin.getTexture(), coin.getX(), coin.getY());
        }
        batch.end();
    }
    private void drawHUD_Bourse(){
        batch.begin();
        moneyLabel.setText(""+hero.getMoney());
        moneyLabel.setPosition(1750, 1000);
        batch.draw(HUD_bourse.getTexture(), x + 350, y + 200);
        stage.addActor(moneyLabel);
        moneyLabel.setVisible(true);
        batch.end();
    }

    private void drawClouds() {
        batch.begin();
        for (Cloud cloud : clouds) {
            batch.draw(cloud.getTexture(), cloud.getX(), cloud.getY());
        }
        batch.end();
    }

    public static float getRandomFloat(float min, float max) {
        return (float) (Math.random() * (max - min) + min);
    }

    private void CheckCollision(String str) {
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Collision");
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) ((x) / collisionLayer.getTileWidth()),
                (int) (y / collisionLayer.getTileHeight()));
        if (cell != null) {
            switch (str) {
                case "down":
                    y += 2;
                    break;
                case "right":
                    x -= 2;
                    break;
                case "left":
                    x += 2;
                    break;
                default:
                    y -= 2;
            }
            Coordonnees();
        }
    }

    public boolean getTextStatus() {
        if (textStatus) {
            return true;
        }
        return false;
    }

    public void setTextStatus(boolean state) {
        textStatus = state;
    }

    public void setTextStatus() {
        textStatus = false;
    }

    @Override
    public void render() {
        if(hero.getLife()<=0)
            Gdx.app.exit();
        float deltaTime = Gdx.graphics.getDeltaTime();
        timeSinceLastAttack += deltaTime;
        stateTime += Gdx.graphics.getDeltaTime();// Accumulate elapsed animation time
        Gdx.gl.glClearColor(0.53f, 0.81f, 0.98f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        updateClouds();
        drawClouds();
        updateCoins();
        if (isNearStatue()) {
            // textStatus = true;
            statueText.startAnimation(3.0f);
        } else {
            // textStatus = false;
        }
        statueText.update(Gdx.graphics.getDeltaTime());
        storeText.update(Gdx.graphics.getDeltaTime());
        tiledMapRenderer.setView(camera);
        int[] layer = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        tiledMapRenderer.render(layer);

        batch.begin();

        drawCharacter();

        camera.position.set(x + (float) currentFrame.getRegionWidth() / 2, y + (float) currentFrame.getRegionHeight() / 2, 0);
        switch (hero.getLvl()) {
            case 999:
                updateMonster_2(Gdx.graphics.getDeltaTime());
                if (monsterState != MonsterState.DEAD)
                    if (isMonsterMovingLeft_) {
                        // Flip the sprite by drawing it with a negative width
                        batch.draw(monsterCurrentFrame_, monsterPosition_2.x + monsterCurrentFrame_.getRegionWidth(),
                                monsterPosition_2.y, -monsterCurrentFrame_.getRegionWidth(),
                                monsterCurrentFrame_.getRegionHeight());
                    } else {
                        // Draw the sprite normally
                        batch.draw(monsterCurrentFrame_, monsterPosition_2.x, monsterPosition_2.y,
                                monsterCurrentFrame_.getRegionWidth(), monsterCurrentFrame_.getRegionHeight());
                    }
                break;

            default:
                updateMonster(Gdx.graphics.getDeltaTime());
                if (monsterState != MonsterState.DEAD)
                    if (isMonsterMovingLeft) {
                        // Flip the sprite by drawing it with a negative width
                        batch.draw(monsterCurrentFrame, monsterPosition.x + monsterCurrentFrame.getRegionWidth(),
                                monsterPosition.y, -monsterCurrentFrame.getRegionWidth(),
                                monsterCurrentFrame.getRegionHeight());
                    } else {
                        // Draw the sprite normally
                        batch.draw(monsterCurrentFrame, monsterPosition.x, monsterPosition.y,
                                monsterCurrentFrame.getRegionWidth(), monsterCurrentFrame.getRegionHeight());
                    }
                break;
        }


        camera.update();

        batch.setProjectionMatrix(camera.combined);
        statueText.draw(batch);
        storeText.draw(batch);
        batch.end();
        if (textStatus) {
            tiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("text_status"));
        }
        // Scene2D UI acting and drawing
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.end();
        tiledMapRenderer.getBatch().begin();
        tiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("shadow"));
        tiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("houses"));
        tiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("decorations"));
        tiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("plants"));
        if (textStatus) {
            tiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("text_status"));
        }
        tiledMapRenderer.getBatch().end();
        drawCoins();
        drawHUD_Bourse();
        stage.draw();

    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        spriteSheet.dispose();
        font.dispose();
        tiledMap.dispose();
        tiledMapRenderer.dispose();
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }if (currentMusic != null) {
            currentMusic.dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // Update the viewport - true centers the camera
        // Optionally update the camera as well
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
    }
}
class AnimatedText {
    private String fullText;
    private StringBuilder currentText;
    private float x, y;
    private float timePerChar;
    private float elapsedTime;
    public boolean isAnimationDone;
    private BitmapFont font;
    private boolean visible;
    private float duration;
    private float alpha = 1.0f;

    public AnimatedText(String text, float x, float y, float timePerChar, BitmapFont font) {
        this.fullText = text;
        this.x = x;
        this.y = y;
        this.timePerChar = timePerChar;
        this.font = font;
        this.currentText = new StringBuilder();
        this.isAnimationDone = false;
    }

    public void update(float deltaTime) {
        if (isAnimationDone)
            return;

        elapsedTime += deltaTime;
        int charIndex = Math.min((int) (elapsedTime / timePerChar), fullText.length());

        if (currentText.length() < charIndex) {
            currentText.append(fullText.charAt(currentText.length()));
            if (currentText.length() == fullText.length()) {
                isAnimationDone = true;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        font.draw(batch, currentText.toString(), x, y);

    }

    public boolean isAnimationDone() {
        return isAnimationDone;
    }

    public void startAnimation(float duration) {
        this.duration = duration;
        this.visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public void changeText(String newText) {
        this.fullText = newText;
        this.currentText = new StringBuilder();
        this.elapsedTime = 0;
        this.isAnimationDone = false;
    }

    public void clearText() {
        this.currentText = new StringBuilder();
        this.isAnimationDone = true;
    }

}