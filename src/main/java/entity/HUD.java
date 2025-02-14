package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HUD {
    private Player player;

    private BufferedImage image;
    private Font font;

    public HUD(Player p) {
        this.player = p;

        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
            
        
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(java.awt.Graphics2D g) {
        g.drawImage(this.image, 0, 10, null);
        g.setFont(this.font);
        g.setColor(Color.WHITE);
        g.drawString(this.player.getHealth() + "/" + this.player.getMaxHealth(), 30, 25);
        g.drawString(this.player.getFire() / 100 + "/" + this.player.getMaxFire() / 100, 30, 45);
    }
}
