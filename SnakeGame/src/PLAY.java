import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class PLAY extends Assignment1{
    public PLAY() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        JFrame jFrame = new JFrame("Snake");
        jFrame.setBounds(450, 150, 614, 637);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setResizable(true);
        jFrame.add(new Painting());
    }
}
