package gamestate;

import java.awt.Graphics2D;

import com.rs.GamePanel;

import entity.Player;
import tilemap.Background;
import tilemap.TileMap;

public class Level1State extends GameState {
    private TileMap tilemap;
    private Background bg;

    private Player player;

    public Level1State(GameStateManager gsm) {
        super(gsm);
        this.init();
    }

    @Override
    public void init() {
        this.tilemap = new TileMap(30);
        this.tilemap.loadTiles("/Tilesets/grasstileset.gif");
        this.tilemap.loadMap("/Maps/level1-1.map");
        this.tilemap.setPosition(0, 0);
        this.tilemap.setTween(0.07);

        this.bg = new Background("/Backgrounds/grassbg1.gif", 1);

        this.player = new Player(this.tilemap);
        this.player.setPosition(165, 100);
    }

    @Override
    public void update() {
        this.player.update();
        this.tilemap.setPosition(GamePanel.WIDTH / 2 - this.player.getx(), GamePanel.HEIGHT / 2 - this.player.gety());
        this.bg.setPosition(this.tilemap.getx(), this.tilemap.gety());
    }

    @Override
    public void draw(Graphics2D g) {
        // clear screen
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        
        // draw background
        this.bg.draw(g);

        // draw tilemap
        this.tilemap.draw(g);

        // draw player
        this.player.draw(g);
    }

    @Override
    public void keyPressed(int k) {
        if (k == java.awt.event.KeyEvent.VK_A) {
            this.player.setLeft(true);
        }
        if (k == java.awt.event.KeyEvent.VK_D) {
            this.player.setRight(true);
        }
        if (k == java.awt.event.KeyEvent.VK_W) {
            this.player.setUp(true);
        }
        if (k == java.awt.event.KeyEvent.VK_S) {
            this.player.setDown(true);
        }
        if (k == java.awt.event.KeyEvent.VK_SPACE) {
            this.player.setJumping(true);
        }
        if (k == java.awt.event.KeyEvent.VK_SHIFT) {
            this.player.setGliding(true);
        }
        if (k == java.awt.event.KeyEvent.VK_R) {
            this.player.setScratching();
        }
        if (k == java.awt.event.KeyEvent.VK_F) {
            this.player.setFiring();
        }
    }

    @Override
    public void keyReleased(int k) {
        if (k == java.awt.event.KeyEvent.VK_A) {
            this.player.setLeft(false);
        }
        if (k == java.awt.event.KeyEvent.VK_D) {
            this.player.setRight(false);
        }
        if (k == java.awt.event.KeyEvent.VK_W) {
            this.player.setUp(false);
        }
        if (k == java.awt.event.KeyEvent.VK_S) {
            this.player.setDown(false);
        }
        if (k == java.awt.event.KeyEvent.VK_SPACE) {
            this.player.setJumping(false);
        }
        if (k == java.awt.event.KeyEvent.VK_SHIFT) {
            this.player.setGliding(false);
        }
    }

}
