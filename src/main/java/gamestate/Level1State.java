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
        this.tilemap.setTween(1);

        this.bg = new Background("/Backgrounds/grassbg1.gif", 0.1);

        this.player = new Player(this.tilemap, 165, 200);
    }

    @Override
    public void update() {
        this.player.update();
        this.tilemap.setPosition(GamePanel.WIDTH / 2 - this.player.getx(), GamePanel.HEIGHT / 2 - this.player.gety());
        this.bg.setPosition(this.tilemap.getx(), this.tilemap.gety());

        System.out.println("Player x: " + this.player.getx() + " y: " + this.player.gety());
        System.out.println("Tilemap x: " + this.tilemap.getx() + " y: " + this.tilemap.gety());
        for(int i = 0; i < this.player.getFireBalls().size() && this.player.getFireBalls().get(i).getx() < 50; i++) {
            System.out.println("Fireball x: " + this.player.getFireBalls().get(i).getx() + " y: " + this.player.getFireBalls().get(i).gety());
        }
    }

    @Override
    public void draw(Graphics2D g) {
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
