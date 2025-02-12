package gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import tilemap.Background;

public class MenuState extends GameState {

    private Background bg;

    private int currentChoice = 0;
    private String[] options = {
        "Start",
        "Help",
        "Quit"
    };

    private Color titleColor;
    private Font titleFont;

    private Font font;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        try {

            this.bg = new Background("/Backgrounds/menubg.gif", 1);
            this.bg.setVector(-0.1, 0.0);

            this.titleColor = new Color(128, 0, 0);

            this.titleFont = new Font("Century Gothic", Font.PLAIN, 28);

            this.font = new Font("Arial", Font.PLAIN, 12);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void init() {
        // Initialization code here
        System.out.println("MenuState initialized");
    }

    @Override
    public void update() {
        this.bg.update();
    }

    @Override
    public void draw(Graphics2D g) {
        // draw background
        this.bg.draw(g);

        // draw title
        g.setColor(this.titleColor);
        g.setFont(this.titleFont);
        g.drawString("My Platformer", 80, 70);

        // draw menu options
        g.setFont(this.font);
        for (int i = 0; i < this.options.length; i++) {
            if (i == this.currentChoice) {
                g.setColor(Color.BLACK);
            }
            else {
                g.setColor(Color.RED);
            }
            g.drawString(this.options[i], 145, 140 + i * 15);
        }

    }

    private void select() {
        if (this.currentChoice == 0) {
            //
        }
        if (this.currentChoice == 1) {
            // help
        }
        if (this.currentChoice == 2) {
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            this.select();
        }
        if (k == KeyEvent.VK_UP) {
            --this.currentChoice;
            if (this.currentChoice == -1) {
                this.currentChoice = this.options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            ++this.currentChoice;
            if (this.currentChoice == this.options.length) {
                this.currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {
        // Key released code here
    }

}
