package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Explosion {
    private double x;
    private double y;
    private double xmap;
    private double ymap;

    private int width;
    private int height;

    private Animation animation;
    private BufferedImage[] sprites;

    private boolean remove;

    public Explosion(double x, double y) {
        this.x = x;
        this.y = y;
        this.xmap = x;
        this.ymap = y;

        this.width = 30;
        this.height = 30;

        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/explosion.gif"));
            this.sprites = new BufferedImage[6];
            for (int i = 0; i < this.sprites.length; ++i) {
                this.sprites[i] = spritesheet.getSubimage(i * this.width, 0, this.width, this.height);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.animation = new Animation();
        this.animation.setFrames(this.sprites);
        this.animation.setDelay(70);
    }

    public void update() {
        this.animation.update();
        if (this.animation.hasPlayedOnce()) {
            this.remove = true;
        }
    }

    public boolean shouldRemove() {
        return this.remove;
    }

    public void setMapPosition(int x, int y) {
        this.xmap = x;
        this.ymap = y;
    }

    public void draw(java.awt.Graphics2D g) {
        g.drawImage(this.animation.getImage(), (int)(this.x + this.xmap - this.width / 2), (int)(this.y + this.ymap - this.height / 2), null);
    }
}
