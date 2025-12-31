/*
public class PhysicsEngine {
    private final int centerX, centerY, radius;
    private final double gravity;

    //PS This is not a physics engine in fact is only the area of the hitbox of teh big box
    public PhysicsEngine(int centerX, int centerY, int radius, double gravity) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.gravity = gravity;
    }

    public void updateBall(Ball ball) {
        ball.applyGravity(gravity);
        ball.updatePosition();
        handleBoundaryCollision(ball);
    }

    private void handleBoundaryCollision(Ball ball) {
        double distanceFromCenter = Math.sqrt(Math.pow(ball.getX() - centerX, 2) + Math.pow(ball.getY() - centerY, 2));
        if (distanceFromCenter >= radius - ball.getSize() / 2.0) {
            double angle = Math.atan2(ball.getY() - centerY, ball.getX() - centerX);
            ball.setVelocityX(-Math.cos(angle) * Math.abs(ball.getVelocityX()));
            ball.setVelocityY(-Math.sin(angle) * Math.abs(ball.getVelocityY()));
            ball.setPosition(
                    (int) (centerX + (radius - ball.getSize() / 2.0) * Math.cos(angle)),
                    (int) (centerY + (radius - ball.getSize() / 2.0) * Math.sin(angle))
            );
        }

        // Handle ground collision
        if (ball.getY() > centerY + radius - ball.getSize()) {
            ball.setPosition(ball.getX(), centerY + radius - ball.getSize());
            ball.setVelocityY(-ball.getVelocityY());
        }
    }

    public void handleBallCollision(Ball ball1, Ball ball2) {
        double distance = Math.sqrt(Math.pow(ball1.getX() - ball2.getX(), 2) + Math.pow(ball1.getY() - ball2.getY(), 2));
        if (distance < ball1.getSize()) {
            double overlap = ball1.getSize() - distance;

            double deltaX = ball1.getX() - ball2.getX();
            double deltaY = ball1.getY() - ball2.getY();
            double normX = deltaX / distance;
            double normY = deltaY / distance;

            ball1.setPosition(ball1.getX() + (int) (normX * overlap / 2), ball1.getY() + (int) (normY * overlap / 2));
            ball2.setPosition(ball2.getX() - (int) (normX * overlap / 2), ball2.getY() - (int) (normY * overlap / 2));

            ball1.setVelocityX(-ball1.getVelocityX());
            ball1.setVelocityY(-ball1.getVelocityY());
            ball2.setVelocityX(-ball2.getVelocityX());
            ball2.setVelocityY(-ball2.getVelocityY());
        }
    }
}*/