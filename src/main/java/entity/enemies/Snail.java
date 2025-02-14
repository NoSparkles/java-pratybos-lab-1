package entity.enemies;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Animation;
import entity.Enemy;
import tilemap.TileMap;

public class Snail extends Enemy {
    private BufferedImage[] sprites;

    public Snail(TileMap tm, double x, double y) {
        super(tm, x, y);

        this.moveSpeed = 0.3;
        this.maxSpeed = 0.3;
        this.fallSpeed = 0.2;
        this.maxFallSpeed = 10.0;

        this.width = 30;
        this.height = 30;
        this.cWidth = 20;
        this.cHeight = 20;

        this.health = this.maxHealth = 2;
        this.damage = 1;

        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/slugger.gif"));
            this.sprites = new BufferedImage[3];
            for (int i = 0; i < this.sprites.length; ++i) {
                this.sprites[i] = spritesheet.getSubimage(i * this.width, 0, this.width, this.height);
            }
        }  
        catch (IOException e) {
            e.printStackTrace();
        }      
        
        this.animation = new Animation();
        this.animation.setFrames(this.sprites);
        this.animation.setDelay(300);

        this.right = true;
        this.facingRight = true;
    }

    private void getNextPosition() {
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

        if (this.falling) {
            this.dy += this.fallSpeed;
        }
    }

    @Override
    public void update() {
        // update position
        this.getNextPosition();
        this.checkTileMapCollision();
        this.setPosition(this.xTemp, this.yTemp);

        // check flinching
        if (this.flinching) {
            long elapsed = (System.nanoTime() - this.flinchTimer) / 1000000;
            if (elapsed > 400) {
                this.flinching = false;
            }
        }

        if (this.right && this.dx == 0) {
            this.right = false;
            this.left = true;
            this.facingRight = false;
        }
        else if (this.left && this.dx == 0) {
            this.right = true;
            this.left = false;
            this.facingRight = true;
        }

        this.animation.update();
    }

    @Override
    public void draw(java.awt.Graphics2D g) {
        if (!this.notOnScreen()) {
            return;
        }

        this.setMapPosition();

        super.draw(g);
    }
}
