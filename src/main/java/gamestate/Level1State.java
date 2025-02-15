package gamestate;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.rs.GamePanel;

import entity.Enemy;
import entity.HUD;
import entity.Player;
import entity.enemies.Snail;
import tilemap.Background;
import tilemap.TileMap;

public final class Level1State extends GameState {
    private TileMap tilemap;
    private Background bg;

    private Player player;

    private HUD hud;

    private ArrayList<Enemy> enemies;

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

        this.enemies = new ArrayList<>();

        Snail s = new Snail(this.tilemap, this.player.getx(), this.player.gety());
        this.enemies.add(s);

        this.hud = new HUD(this.player);
    }

    @Override
    public void update() {
        this.player.update();
        this.tilemap.setPosition(GamePanel.WIDTH / 2 - this.player.getx(), GamePanel.HEIGHT / 2 - this.player.gety());
        this.bg.setPosition(this.tilemap.getx(), this.tilemap.gety());
        
        // update enemies
        for (int i = 0; i < this.enemies.size(); i++) {
            this.enemies.get(i).update();
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

        // draw enemies
        for (int i = 0; i < this.enemies.size(); i++) {
            this.enemies.get(i).draw(g);
        }

        // draw hud
        this.hud.draw(g);
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
