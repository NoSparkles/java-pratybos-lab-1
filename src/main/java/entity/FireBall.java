package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import tilemap.TileMap;

public class FireBall extends MapObject {
    private boolean hit;
    private boolean remove;
    private BufferedImage[] sprites;
    private BufferedImage[] hitSprites;
    
    public FireBall(TileMap tm, boolean right) {
        super(tm);
        
        this.facingRight = right;
        
        this.moveSpeed = 3.8;
        if (right) {
            this.dx = this.moveSpeed;
        } else {
            this.dx = -this.moveSpeed;
        }
        
        this.width = 30;
        this.height = 30;
        this.cWidth = 14;
        this.cHeight = 14;

        // load sprites
        try {
            BufferedImage spritesheet = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/fireball.gif"));
            this.sprites = new BufferedImage[4];
            for (int i = 0; i < this.sprites.length; i++) {
                this.sprites[i] = spritesheet.getSubimage(i * this.width, 0, this.width, this.height);
            }
            this.hitSprites = new BufferedImage[3];
            for (int i = 0; i < this.hitSprites.length; ++i) {
                this.hitSprites[i] = spritesheet.getSubimage(i * this.width, this.height, this.width, this.height);
            }
            this.animation = new Animation();
            this.animation.setFrames(this.sprites);
            this.animation.setDelay(70);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void setHit() {
        if (this.hit) {
            return;
        }
        this.hit = true;
        this.animation.setFrames(this.hitSprites);
        this.animation.setDelay(70);
        this.dx = 0;
    }

    public boolean shouldRemove() {
        return this.remove;
    }

    public void update() {
        this.checkTileMapCollision();
        this.setPosition(this.xTemp, this.yTemp);
        if (this.dx == 0 && !this.hit) {
            this.setHit();
        }
        this.animation.update();
        if (this.hit && this.animation.hasPlayedOnce()) {
            this.remove = true;
        }
    }

    public void draw(Graphics2D g) {
        this.setMapPosition();

        if (this.facingRight) {
            g.drawImage(this.animation.getImage(), (int)(this.x + this.xMap - this.width / 2), (int)(this.y + this.yMap - this.height / 2), null);
        }
        else {
            g.drawImage(this.animation.getImage(), (int)(this.x + this.xMap - this.width / 2 + this.width), (int)(this.y + this.yMap - this.height / 2), -this.width, this.height, null);
        }
    }
}
