package gamestate;

import java.awt.Graphics2D;

import com.rs.GamePanel;

import tilemap.TileMap;


public class Level1State extends GameState {

    private TileMap tilemap;


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
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        // clear screen
        g.setColor(java.awt.Color.cyan);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        // draw tilemap
        this.tilemap.draw(g);
    }

    @Override
    public void keyPressed(int k) {
    }

    @Override
    public void keyReleased(int k) {
    }

}
