package entity;

import tilemap.TileMap;

public abstract class Enemy extends MapObject{
    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage;

    protected boolean flinching;
    protected long flinchTimer;

    public Enemy(TileMap tm, double x, double y) {
        super(tm, x, y);
    }

    public boolean isDead() {
        return this.dead;
    }

    public int getDamage() {
        return this.damage;
    }

    public void hit(int damage) {
        if (this.dead || this.flinching) {
            return;
        }
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.dead = true;
        }
        this.flinching = true;
        this.flinchTimer = System.nanoTime();
    }

    public abstract void update();

}
