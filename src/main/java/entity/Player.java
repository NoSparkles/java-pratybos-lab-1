package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import tilemap.TileMap;
 
public class Player extends MapObject {
    //player stuff
    private int health;
    private int maxHealth;
    private int fire;
    private int maxFire;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;

    // fireball
    private boolean firing;
    private int fireCost;
    private int fireBallDamage;
    private ArrayList<FireBall> fireBalls;

    // scratch
    private boolean scratching;
    private int scratchDamage;
    private int scratchRange;

    // gliding
    private boolean gliding;

    // animations
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {
        2, 8, 1, 2, 4, 2, 5
    };

    // animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 4;
    private static final int FIREBALL = 5;
    private static final int SCRATCHING = 6;
    
    public Player(TileMap tm) {
        super(tm);

        this.width = 30;
        this.height = 30;
        this.cWidth = 20;
        this.cHeight = 20;

        this.moveSpeed = 0.3;
        this.maxSpeed = 1.6;
        this.stopSpeed = 0.4;
        this.fallSpeed = 0.15;
        this.maxFallSpeed = 4.0;
        this.jumpStart = -4.8;
        this.stopJumpSpeed = 0.3;

        this.facingRight = true;

        this.health = this.maxHealth = 5;
        this.fire = this.maxFire = 2500;

        this.fireCost = 200;
        this.fireBallDamage = 5;
        this.fireBalls = new ArrayList<FireBall>();

        this.scratchDamage = 8;
        this.scratchRange = 40;

        // load sprites
        try {
            BufferedImage spritesheet = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/playersprites.gif"));
            
            this.sprites = new ArrayList<BufferedImage[]>();
            for (int i = 0; i < 7; ++i) {
                BufferedImage[] bi = new BufferedImage[this.numFrames[i]];
                for (int j = 0; j < this.numFrames[i]; ++j) {
                    // scratching animation is twice as wide as others
                    if (i != Player.SCRATCHING) {
                        bi[j] = spritesheet.getSubimage(j * this.width, i * this.height, this.width, this.height);
                    }
                    else {
                        bi[j] = spritesheet.getSubimage(j * this.width * 2, i * this.height, this.width * 2, this.height);
                    }
                }
                sprites.add(bi);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        this.animation = new Animation();
        this.currentAction = Player.IDLE;
        this.animation.setFrames(this.sprites.get(Player.IDLE));
        this.animation.setDelay(400);
    }

    public int getHealth() { 
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getFire() {
        return this.fire;
    }

    public int getMaxFire() {
        return this.maxFire;
    }

    public void setFiring() {
        this.firing = true;
    }

    public void setScratching() {
        this.scratching = true;
    }

    public void setGliding(boolean b) {
        this.gliding = b;
    }

    private void getNextPosition() {
        // movement
        if (this.left) {
            this.dx -= this.moveSpeed;
            if (this.dx < -this.maxSpeed) {
                this.dx = -this.maxSpeed;
            }
        }
        else if (this.right) {
            this.dx += this.moveSpeed;
            if (this.dx > this.maxSpeed) {
                this.dx = this.maxSpeed;
            }
        }
        else {
            if (this.dx > 0) {
                this.dx -= this.stopSpeed;
                if (this.dx < 0) {
                    this.dx = 0;
                }
            }
            else if (this.dx < 0) {
                this.dx += this.stopSpeed;
                if (this.dx > 0) {
                    this.dx = 0;
                }
            }
        }

        // cannot move while attacking, except in air
        if ((this.currentAction == Player.SCRATCHING || this.currentAction == Player.FIREBALL) && !(this.jumping || this.falling)) {
            this.dx = 0;
        }

        // jumping
        if (this.jumping && !this.falling) {
            this.dy = this.jumpStart;
            this.falling = true;
        }

        // falling
        if (this.falling) {
            if (this.dy > 0 && this.gliding) {
                this.dy += this.fallSpeed * 0.1;
            }
            else {
                this.dy += this.fallSpeed;
            }

            if (this.dy > 0) {
                this.jumping = false;
            }
            if (this.dy < 0 && !this.jumping) {
                this.dy += this.stopJumpSpeed;
            }
            if (this.dy > this.maxFallSpeed) {
                this.dy = this.maxFallSpeed;
            }
        }
    }

    public void update() {
        System.out.println(this.animation.getFrame());
        // update position
        this.getNextPosition();
        this.checkTileMapCollision();
        this.setPosition(this.xTemp, this.yTemp);

        // check attack has stopped
        if (this.currentAction == Player.SCRATCHING) {
            if (this.animation.hasPlayedOnce()) {
                this.scratching = false;
            }
        }
        if (this.currentAction == Player.FIREBALL) {
            if (this.animation.hasPlayedOnce()) {
                this.firing = false;
            }
        }

        // set animation
        if (this.scratching) {
            if (this.currentAction != Player.SCRATCHING) {
                this.currentAction = Player.SCRATCHING;
                this.animation.setFrames(this.sprites.get(Player.SCRATCHING));
                this.animation.setDelay(50);
                this.width = 60;
            }
        }
        else if (this.firing) {
            if (this.currentAction != Player.FIREBALL) {
                this.currentAction = Player.FIREBALL;
                this.animation.setFrames(this.sprites.get(Player.FIREBALL));
                this.animation.setDelay(100);
                this.width = 30;
            }
        }
        else if (this.dy > 0) {
            if (this.gliding) {
                if (this.currentAction != Player.GLIDING) {
                    this.currentAction = Player.GLIDING;
                    this.animation.setFrames(this.sprites.get(Player.GLIDING));
                    this.animation.setDelay(100);
                    this.width = 30;
                }
            }
            else if (this.currentAction != Player.FALLING) {
                this.currentAction = Player.FALLING;
                this.animation.setFrames(this.sprites.get(Player.FALLING));
                this.animation.setDelay(100);
                this.width = 30;
            }
        }
        else if (this.dy < 0) {
            if (this.currentAction != Player.JUMPING) {
                this.currentAction = Player.JUMPING;
                this.animation.setFrames(this.sprites.get(Player.JUMPING));
                this.animation.setDelay(-1);
                this.width = 30;
            }
        }
        else if(this.left || this.right) {
            if (this.currentAction != Player.WALKING) {
                this.currentAction = Player.WALKING;
                this.animation.setFrames(this.sprites.get(Player.WALKING));
                this.animation.setDelay(40);
                this.width = 30;
            }
        }
        else {
            if (this.currentAction != Player.IDLE) {
                this.currentAction = Player.IDLE;
                this.animation.setFrames(this.sprites.get(Player.IDLE));
                this.animation.setDelay(400);
                this.width = 30;
            }
        }

        this.animation.update();

        // set direction
        if (this.currentAction != Player.SCRATCHING && this.currentAction != Player.FIREBALL) {
            if (this.right) {
                this.facingRight = true;
            }
            if (this.left) {
                this.facingRight = false;
            }
        }
    }

    public void draw(Graphics2D g) {
        this.setMapPosition();

        // draw player
        if (this.flinching) {
            long elapsed = (System.nanoTime() - this.flinchTimer) / 1000000;
            if (elapsed / 100 % 2 == 0) {
                return;
            }
        }

        if (this.facingRight) {
            g.drawImage(this.animation.getImage(), (int)(this.x + this.xMap - this.width / 2), (int)(this.y + this.yMap - this.height / 2), null);
        }
        else {
            g.drawImage(this.animation.getImage(), (int)(this.x + this.xMap - this.width / 2 + this.width), (int)(this.y + this.yMap - this.height / 2), -this.width, this.height, null);
        }
    }
}


