/*
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.awt.*;

public class Ball {
    private int x, y;
    private double velocityX, velocityY;
    private final int size;
    private final Color color;

    public Ball(int startX, int startY, int size) {
        this.x = startX;
        this.y = startY;
        this.size = size;
        Random rand = new Random();
        this.velocityX = rand.nextInt(5) + 1;
        this.velocityY = rand.nextInt(5) + 1;
        if (rand.nextBoolean()) velocityX = -velocityX;
        if (rand.nextBoolean()) velocityY = -velocityY;

        // Random color generation
        this.color = Color.getHSBColor(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
    public double getVelocityX() { return velocityX; }
    public double getVelocityY() { return velocityY; }

    public void setVelocityX(double velocityX) { this.velocityX = velocityX; }
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void updatePosition() {
        x += (int) velocityX;
        y += (int) velocityY;
    }


    public void applyGravity(double gravity) {
        velocityY += gravity;
    }


    //The visible size of the balls not any of the hitboxes
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size-15, size-15);
    }
}*/