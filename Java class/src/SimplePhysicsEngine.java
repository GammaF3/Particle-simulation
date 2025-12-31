import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimplePhysicsEngine extends JPanel implements ActionListener, MouseListener
{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int MAX_LIFETIME = 10000;

    private final List<Ball> balls;
    private boolean isMousePressed = false;

    private static double GRAVITY = 1.81;
    private final JSlider gravitySlider;

    private int frameCount = 0;
    private long lastTime = System.nanoTime();
    private double frameRate = 0;

    private double mouseBallRadius = 50.0;
    private double pushStrength = 0.1;

    File imageFile = new File("fergo.png");
    Image heart;

    private Timer addBallTimer;

    public SimplePhysicsEngine()
    {
        int frameWidth = 1000;
        int frameHeight = 638;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.LIGHT_GRAY);
        balls = new ArrayList<>();

        Timer timer = new Timer(8, this);
        timer.start();

        Random r = new Random();
        int minPos = 0;
        int maxPos = 750;
        double minRest = 0;
        double maxRest = 1;

        //Inicia Balls
        for (int i = 0; i < 1000; i++)
        {
            balls.add(new Ball(r.nextInt(maxPos - minPos) + minPos,
                    r.nextInt(maxPos - minPos) + minPos,
                    15, 12, r.nextDouble(maxRest - minRest) + minRest));
        }

        addMouseListener(this);
        addMouseMotionListener(this);

        // Gravity slider
        gravitySlider = new JSlider(JSlider.VERTICAL, 0, 20, 10);
        gravitySlider.setMajorTickSpacing(5);
        gravitySlider.setMinorTickSpacing(1);
        gravitySlider.setPaintTicks(true);
        gravitySlider.setPaintLabels(true);
        gravitySlider.setPreferredSize(new Dimension(50, 200));
        gravitySlider.setBackground(Color.BLACK);

        gravitySlider.addChangeListener(e -> {
            GRAVITY = gravitySlider.getValue();
        });

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        this.setBounds(0, 0, WIDTH, HEIGHT);
        layeredPane.add(this, Integer.valueOf(0));

        gravitySlider.setBounds(frameWidth - 60, 10, 50, 200);
        layeredPane.add(gravitySlider, Integer.valueOf(1));

        JButton addRedBallButton = new JButton("+");
        addRedBallButton.setFont(new Font("BOLD", Font.BOLD, 25));
        addRedBallButton.setBounds(frameWidth - 140, 220, 50, 40);
        addRedBallButton.setBackground(Color.RED);
        addRedBallButton.setForeground(Color.WHITE);
        addRedBallButton.setFocusPainted(true);

        int addInterval = 10;
        addBallTimer = new Timer(addInterval, e -> {
            Random rand = new Random();
            int randomX = rand.nextInt(WIDTH - 100);
            int randomY = rand.nextInt(HEIGHT - 50);
            balls.add(new Ball(randomX, randomY, 0, 10, 0.9, true));
        });
        addBallTimer.setRepeats(true);

        addRedBallButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addBallTimer.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                addBallTimer.stop();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    addBallTimer.stop();
                }
            }
        });

        layeredPane.add(addRedBallButton, Integer.valueOf(1));

        JFrame frame = new JFrame("Simple Physics Engine");
        frame.setLayout(new BorderLayout());
        frame.add(layeredPane, BorderLayout.CENTER);
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try
        {
            heart = ImageIO.read(imageFile);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void addMouseMotionListener(SimplePhysicsEngine simplePhysicsEngine) {}

    @Override
    public void actionPerformed(ActionEvent e)
    {
        balls.removeIf(ball -> ball.lifetime > MAX_LIFETIME);

        for (Ball ball : balls)
        {
            ball.update();
            ball.contactCount = 0;
        }

        applySurfaceTensionEffects();

        if (isMousePressed)
        {
            Point mousePos = getMousePosition();
            if (mousePos != null)
            {
                double mx = mousePos.x;
                double my = mousePos.y;

                for (Ball ball : balls) {
                    double dx = ball.x - mx;
                    double dy = ball.y - my;
                    double dist = Math.sqrt(dx*dx + dy*dy);
                    if (dist < mouseBallRadius && dist > 0) {
                        double force = pushStrength * (mouseBallRadius - dist) / dist;
                        ball.vx += dx * force;
                        ball.vy += dy * force;
                    }
                }
            }
        }

        // Check collisions
        for (int i = 0; i < balls.size(); i++)
        {
            for (int j = i + 1; j < balls.size(); j++)
            {
                if (balls.get(i).checkCollision(balls.get(j)))
                {
                    balls.get(i).contactCount++;
                    balls.get(j).contactCount++;
                }
            }
        }

        frameCount++;
        long currentTime = System.nanoTime();
        double elapsedTime = (currentTime - lastTime) / 1e9;
        if (elapsedTime >= 1)
        {
            frameRate = frameCount / elapsedTime;
            frameCount = 0;
            lastTime = currentTime;
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);

        for (Ball ball : balls) {
            if (ball.isRed && heart != null) {
                g.drawImage(heart, (int) ball.x, (int) ball.y, this);
            } else {
                if (ball.contactCount > 1) {
                    g.setColor(new Color(0, 0, 255, 173));
                } else {
                    g.setColor(new Color(169, 84, 84, 173));
                }
                g.fillOval((int) ball.x, (int) ball.y, ball.radius * 2, ball.radius * 2);
            }
        }

        // Draw an overlay or debug circle for the mouse pushing ball if mouse pressed
        if (isMousePressed) {
            Point mousePos = getMousePosition();
            if (mousePos != null) {
                g.setColor(new Color(0, 0, 0, 255));
                g.drawOval(mousePos.x - (int)mouseBallRadius,
                        mousePos.y - (int)mouseBallRadius,
                        (int)mouseBallRadius*2, (int)mouseBallRadius*2);
            }
        }

        g.setColor(new Color(112, 112, 224, 105));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(String.format("FPS: %.2f", frameRate), WIDTH - 80, HEIGHT - 10);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        // Vliendo Pito por tha micha madre,
        // not on the button. Since we have the button in a layered pane,
        // getMousePosition still returns panel coordinates.
        // For simplicity, we assume user clicks on panel area:
        isMousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isMousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    private void applySurfaceTensionEffects() {
        double surfaceLimit = HEIGHT / 4.0;
        double neighborRadius = 20.0;
        double verticalAdjustment = 0.01;
        double attractionForce = 0.001;

        for (Ball b : balls) {
            if (b.y < surfaceLimit) {
                List<Ball> neighbors = findNeighbors(b, balls, neighborRadius);
                if (!neighbors.isEmpty()) {
                    double avgY = 0;
                    for (Ball nb : neighbors) {
                        avgY += nb.y;
                    }
                    avgY /= neighbors.size();

                    double diff = avgY - b.y;
                    b.vy += diff * verticalAdjustment;

                    for (Ball nb : neighbors) {
                        double dx = nb.x - b.x;
                        double dy = nb.y - b.y;
                        double dist = Math.sqrt(dx*dx + dy*dy);
                        if (dist > 0 && dist < neighborRadius) {
                            b.vx += (dx / dist) * attractionForce;
                            b.vy += (dy / dist) * attractionForce;
                        }
                    }
                }
            }
        }
    }

    private List<Ball> findNeighbors(Ball b, List<Ball> allBalls, double radius) {
        List<Ball> neighbors = new ArrayList<>();
        for (Ball other : allBalls) {
            if (other != b) {
                double dx = other.x - b.x;
                double dy = other.y - b.y;
                double dist = Math.sqrt(dx*dx + dy*dy);
                if (dist <= radius) {
                    neighbors.add(other);
                }
            }
        }
        return neighbors;
    }

    static class Ball
    {
        double x, y;
        double vx, vy;
        int radius;
        double restitution;
        boolean isRed;
        int lifetime;
        int contactCount;

        Ball(double x, double y, double vx, double vy, double restitution) {
            this(x, y, vx, vy, restitution, false);
        }

        Ball(double x, double y, double vx, double vy, double restitution, boolean isRed)
        {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.radius = 5;
            this.restitution = restitution;
            this.isRed = isRed;
            this.lifetime = 0;
            this.contactCount = 0;
        }

        void update() {
            vy += GRAVITY * 0.1;
            x += vx;
            y += vy;

            if (x - radius < 0 || x + radius > WIDTH - 60)
            {
                vx = -vx * restitution;
                x = Math.max(radius, Math.min(x, WIDTH - 60 - radius));
            }

            if (y - radius < 0 || y + radius > HEIGHT)
            {
                vy = -vy * restitution;
                y = Math.max(radius, Math.min(y, HEIGHT - radius));
            }

            lifetime++;
        }

        boolean checkCollision(Ball other)
        {
            double dx = other.x - this.x;
            double dy = other.y - this.y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < this.radius + other.radius)
            {
                double nx = dx / distance;
                double ny = dy / distance;

                double relativeVelocityX = other.vx - this.vx;
                double relativeVelocityY = other.vy - this.vy;
                double velocityAlongNormal = relativeVelocityX * nx + relativeVelocityY * ny;

                if (velocityAlongNormal < 0)
                {
                    double restitutionFactor = (this.restitution + other.restitution) / 2.0;
                    double impulse = 2 * velocityAlongNormal / (this.radius + other.radius);

                    this.vx += impulse * other.radius * nx * restitutionFactor;
                    this.vy += impulse * other.radius * ny * restitutionFactor;
                    other.vx -= impulse * this.radius * nx * restitutionFactor;
                    other.vy -= impulse * this.radius * ny * restitutionFactor;

                    double overlap = this.radius + other.radius - distance;
                    this.x -= nx * overlap / 2;
                    this.y -= ny * overlap / 2;
                    other.x += nx * overlap / 2;
                    other.y += ny * overlap / 2;
                }
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(SimplePhysicsEngine::new);
    }
}
