package entity;

import java.awt.Rectangle;

import com.rs.GamePanel;

import tilemap.Tile;
import tilemap.TileMap;

public abstract class MapObject {
    // tile stuff
    protected TileMap tileMap;
    protected int tileSize;
    protected double xMap;
    protected double yMap;

    // position and vector
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;

    // dimensions
    protected int width;
    protected int height;

    // collision box
    protected int cWidth;
    protected int cHeight;

    // collision
    protected int currRow;
    protected int currCol;
    // next position
    protected double xDest;
    protected double yDest;
    // temp position
    protected double xTemp;
    protected double yTemp;
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

    // animation
    protected Animation animation;
    protected int currentAction;
    protected int previousAction;
    protected boolean facingRight;

    // movement
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;

    // movement attributes
    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double stopJumpSpeed;

    public MapObject(TileMap tm, double x, double y) {
        this.tileMap = tm;
        this.tileSize = tm.getTileSize();

        this.xTemp = x;
        this.yTemp = y;
        this.x = x;
        this.y = y;
    }

    public boolean intersects(MapObject o) {
        Rectangle r1 = this.getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);
    }

    public Rectangle getRectangle() {
        return new Rectangle((int)(this.x - this.cWidth), (int)(this.y - this.cHeight), this.cWidth, this.cHeight);
    }

    public void calculateCorners(double x, double y) {
        int leftTile = (int)(x - this.cWidth / 2) / this.tileSize;
        int rightTile = (int)(x + this.cWidth / 2 - 1) / this.tileSize;
        int topTile = (int)(y - this.cHeight / 2) / this.tileSize;
        int bottomTile = (int)(y + this.cHeight / 2 - 1) / this.tileSize;

        int tl = this.tileMap.getType(topTile, leftTile);
        int tr = this.tileMap.getType(topTile, rightTile);
        int bl = this.tileMap.getType(bottomTile, leftTile);
        int br = this.tileMap.getType(bottomTile, rightTile);

        this.topLeft = tl == Tile.BLOCKED;
        this.topRight = tr == Tile.BLOCKED;
        this.bottomLeft = bl == Tile.BLOCKED;
        this.bottomRight = br == Tile.BLOCKED;
    }

    public void checkTileMapCollision() {
        this.currCol = (int)this.x / this.tileSize;
        this.currRow = (int)this.y / this.tileSize;
        this.xDest = this.x + this.dx;
        this.yDest = this.y + this.dy;
        this.xTemp = this.x;

        this.calculateCorners(this.x, this.yDest);
        if (this.dy < 0) {
            if (this.topLeft || this.topRight) {
                this.dy = 0;
                this.yTemp = this.currRow * this.tileSize + this.cHeight / 2;
            }
            else {
                this.yTemp += this.dy;
            }
        }
        else if (this.dy > 0) {
            if (this.bottomLeft || this.bottomRight) {
                this.dy = 0;
                this.falling = false;
                this.yTemp = (this.currRow + 1) * this.tileSize - this.cHeight / 2;
            }
            else {
                this.yTemp += this.dy;
            }
        }

        calculateCorners(this.xDest, this.y);
        if (this.dx < 0) {
            if (this.topLeft || this.bottomLeft) {
                this.dx = 0;
                this.xTemp = this.currCol * this.tileSize + this.cWidth / 2;
            }
            else {
                this.xTemp += this.dx;
            }
        }
        else if (this.dx > 0) {
            if (this.topRight || this.bottomRight) {
                this.dx = 0;
                this.xTemp = (this.currCol + 1) * this.tileSize - this.cWidth / 2;
            }
            else {
                this.xTemp += this.dx;
            }
        }

        if (!this.falling) {
            calculateCorners(this.x, this.yDest + 1);
            if (!this.bottomLeft && !this.bottomRight) {
                this.falling = true;
            }
        }
    }

    public int getx() {
        return (int)this.x;
    }

    public int gety() {
        return (int)this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getCWidth() {
        return this.cWidth;
    }

    public int getCHeight() {
        return this.cHeight;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setTempPosition(double x, double y) {
        this.xTemp = x;
        this.yTemp = y;
    }

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setMapPosition() {
        this.xMap = this.tileMap.getx();
        this.yMap = this.tileMap.gety();
    }

    public void setLeft(boolean b) {
        this.left = b;
    }

    public void setRight(boolean b) {
        this.right = b;
    }

    public void setUp(boolean b) {
        this.up = b;
    }

    public void setDown(boolean b) {
        this.down = b;
    }

    public void setJumping(boolean b) {
        this.jumping = b;
    }

    public boolean notOnScreen() {
        return this.x + this.xMap + this.width < 0 ||
               this.x + this.xMap - this.width > GamePanel.WIDTH || 
               this.y + this.yMap + this.height < 0 || 
               this.y + this.yMap - this.height > GamePanel.HEIGHT;
    }
}
