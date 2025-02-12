package gamestate;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {
    private final ArrayList<GameState> gameStates;
    private int currentState;

    public static final int MENUSTATE = 0;

    public GameStateManager() {
        this.gameStates = new ArrayList<>();

        this.currentState = GameStateManager.MENUSTATE;
        gameStates.add(new MenuState(this));
    }

    public void setState(int state) {
        this.currentState = state;
        gameStates.get(this.currentState).init();
    }

    public void update() {
        gameStates.get(this.currentState).update();
    }

    public void draw(Graphics2D g) {
        gameStates.get(this.currentState).draw(g);
    }

    public void keyPressed(int k) {
        gameStates.get(this.currentState).keyPressed(k);
    }

    public void keyReleased(int k) {
        gameStates.get(this.currentState).keyReleased(k);
    }
}
