import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;

public class Painting extends JPanel implements KeyListener, ActionListener {
    ImageIcon head = new ImageIcon("resources/picture/head.png");
    ImageIcon dot = new ImageIcon("resources/picture/dot.png");
    ImageIcon apple = new ImageIcon("resources/picture/apple.png");
    ImageIcon barrier = new ImageIcon("resources/picture/barrier.jpg");


    int lengh, score;
    int t = 250;
    int temp = t;
    int[] position_x = new int[600];
    int[] position_y = new int[600];
    int[] barrier_x = new int[20];
    int[] barrier_y = new int[20];
    String direction = "Right";
    boolean isPressed = false;
    boolean isFailed = false;
    Timer timer = new Timer(t, this);
    int apple_x, apple_y;
    Random random = new Random();
    Clip bgm;

    public Painting() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        lengh = 3;
        position_x[0] = 80;
        position_y[0] = 60;
        position_x[1] = 70;
        position_y[1] = 60;
        position_x[2] = 60;
        position_y[2] = 60;
        apple_x = 10 + 10 * random.nextInt(57);
        apple_y = 10 + 10 * random.nextInt(57);
        for (int i = 0; i < 20; i++) {
            barrier_y[i] = 10 + 10 * random.nextInt(57);
            int randomNum = 10 + 10 * random.nextInt(57);
            while (existed(randomNum, barrier_x, i)) {
                randomNum = 10 + 10 * random.nextInt(57);
            }
            barrier_x[i] = randomNum;
        }

        score = 0;
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();
        playBGM();
    }

    private boolean existed(int num, int[] barrier_x, int index) {
        for (int i = 0; i < index; i++) {
            if (num == barrier_x[i] || (apple_x == barrier_x[i] && apple_y == barrier_y[i])) {
                return true;
            }
        }
        return false;
    }

    private void playBGM() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        bgm = AudioSystem.getClip();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("sound/sound.wav");
        AudioInputStream ais = AudioSystem.getAudioInputStream(is);
        bgm.open(ais);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.setBackground(Color.PINK);
        graphics.fillRect(0, 0, 600, 600);
        graphics.setColor(Color.cyan);
        graphics.setFont(new Font("arial", Font.BOLD, 15));
        graphics.drawString("Score: " + score, 500, 50);
        head.paintIcon(this, graphics, position_x[0], position_y[0]);
        for (int i = 1; i < lengh; i++) {
            dot.paintIcon(this, graphics, position_x[i], position_y[i]);
        }
        apple.paintIcon(this, graphics, apple_x, apple_y);
        for (int i = 0; i < 20; i++) {
            barrier.paintIcon(this, graphics, barrier_x[i], barrier_y[i]);
        }
        if (!isPressed) {
            graphics.setColor(Color.white);
            graphics.setFont(new Font("arial", Font.BOLD, 25));
            graphics.drawString("Press Space to Start", 180, 300);
            bgm.stop();
        } else {
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
        if (isFailed) {
            graphics.setColor(Color.red);
            graphics.setFont(new Font("arial", Font.BOLD, 25));
            graphics.drawString("Failed: Press Space to Restart", 140, 300);
            bgm.stop();
        }
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            if (isFailed) {
                isFailed = false;
                lengh = 3;
                position_x[0] = 80;
                position_y[0] = 60;
                position_x[1] = 70;
                position_y[1] = 60;
                position_x[2] = 60;
                position_y[2] = 60;
                temp = 250;
                t = temp;
                timer.setDelay(temp);
                apple_x = 10 + 10 * random.nextInt(57);
                apple_y = 10 + 10 * random.nextInt(57);
                for (int i = 0; i < 20; i++) {
                    barrier_y[i] = 10 + 10 * random.nextInt(57);
                    int randomNum = 10 + 10 * random.nextInt(57);
                    while (existed(randomNum, barrier_x, i)) {
                        randomNum = 10 + 10 * random.nextInt(57);
                    }
                    barrier_x[i] = randomNum;
                }
                score = 0;
                direction = "Right";
            }
            isPressed = !isPressed;
            repaint();
        }

        if (keyCode == KeyEvent.VK_RIGHT) {
            direction = "Right";
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            direction = "Left";
        }
        if (keyCode == KeyEvent.VK_UP) {
            direction = "Up";
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            direction = "Down";
        }
        if (keyCode == KeyEvent.VK_A) {
            temp = t/2;
            timer.setDelay(temp);
            timer.start();
        }
        if (keyCode == KeyEvent.VK_D) {
            temp = t;
            timer.setDelay(temp);
            timer.start();
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isPressed && !isFailed) {
            for (int i = lengh - 1; i > 0; i--) {
                position_x[i] = position_x[i - 1];
                position_y[i] = position_y[i - 1];
            }

            if (Objects.equals(direction, "Right")) {
                position_x[0] += 10;
                if (position_x[0] >= 600) {
                    isFailed = true;
                }
            } else if (Objects.equals(direction, "Left")) {
                position_x[0] -= 10;
                if (position_x[0] < 0) {
                    isFailed = true;
                }
            } else if (Objects.equals(direction, "Up")) {
                position_y[0] -= 10;
                if (position_y[0] < 0) {
                    isFailed = true;
                }
            } else if (Objects.equals(direction, "Down")) {
                position_y[0] += 10;
                if (position_y[0] >= 600) {
                    isFailed = true;
                }
            }
            if ((position_x[0] == apple_x) && (position_y[0] == apple_y)) {
                lengh++;
                score++;
                if (t > 30) {
                    t -= 1;
                    timer.setDelay(temp);
                }
                apple_x = 10 + 10 * random.nextInt(57);
                apple_y = 10 + 10 * random.nextInt(57);

                repaint();
            }
            for (int i = 0; i < 20; i++) {
                if ((position_x[0] == barrier_x[i]) && (position_y[0] == barrier_y[i]) ||
                        barrier_x[i] == 90 && barrier_y[i] == 60 ||
                        barrier_x[i] == 80 && barrier_y[i] == 60 ||
                        barrier_x[i] == 70 && barrier_y[i] == 60 ||
                        barrier_x[i] == 60 && barrier_y[i] == 60)
                {
                    isFailed = true;
                }
            }

            for (int i = 1; i < lengh; i++) {
                if ((position_x[i] == position_x[0]) && (position_y[i] == position_y[0])) {
                    isFailed = true;
                    break;
                }
                repaint();

            }
            timer.start();
        }
    }
}