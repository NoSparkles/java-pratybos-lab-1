package com.rs;

import javax.swing.JFrame;

@SuppressWarnings("unused")
public final class App extends JFrame {
    public App() {
        super("Game");
        this.setContentPane(new GamePanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        App app = new App();
    }
}
