package tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.rs.GamePanel;

public class Background {

    private BufferedImage image;

    private double x;
    private double y;
    private double dx;
    private double dy;

    private double moveScale;

    public Background(String s, double moveScale) {
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(s));
            this.moveScale = moveScale;
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPosition(double x, double y) {
        this.x = (x * this.moveScale) % GamePanel.WIDTH;
        this.y = (y * this.moveScale) % GamePanel.HEIGHT;
    }

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        this.x += this.dx;
        this.y += this.dy;
    }

    public void draw(Graphics2D g) {
        g.drawImage(this.image, (int) this.x, (int) this.y, null);
        
        if (this.x < 0) {
            g.drawImage(this.image, (int) this.x + GamePanel.WIDTH, (int) this.y, null);
        }
        if (this.x > 0) {
            g.drawImage(this.image, (int) this.x - GamePanel.WIDTH, (int) this.y, null);
        }
    }
    
}
