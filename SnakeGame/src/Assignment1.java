import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Assignment1 {
    /**{
     * Create and display the GUI. For thread safety reasons,
     * This method is called in the event calling thread.
     */
    private static void Snake() {

        //A nice look style
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("Snake");
        frame.setBounds(500, 300, 580, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = frame.getContentPane();

        JTabbedPane tabbedPane = new JTabbedPane();// Create a default tabbedpanel
        JLabel tabLabelA = new JLabel();
        JLabel tabLabelB = new JLabel();

        tabbedPane.addTab("PLAY", tabLabelA);
        tabbedPane.addTab("HELP", tabLabelB);

        container.add(tabbedPane);

        ImageIcon icon = new ImageIcon("resources/picture/Snake.png");
        JButton jButton = new JButton();
        jButton.setIcon(icon);
        jButton.setSize( 580, 320);

        tabLabelA.setLayout(new BorderLayout());
        tabLabelA.add(jButton, BorderLayout.CENTER);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new PLAY();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    ex.printStackTrace();
                }
                frame.dispose();
            }
        });

        JTextArea textArea = new JTextArea(
                "Operations: " + "\n"+
                "1. Control snake with \"Arrow Keys\";" + "\n"+
                "2. Press A to accelerate, press D to resume the normal speed." + "\n"+ "\n"+
                "Instructions: " + "\n"+
                "1. Press space control pause and start;" + "\n"+
                "2. If the user consumes an apple, the length of the snake and the value of \"Score\" will be incremented by one;" + "\n"+
                "3. Conditions for game over: The snake collides with itself, boundary, or blue barriers;" + "\n"+
                "4. The game ends and can be restarted when prompted." + "\n"+ "\n"+
                "Features:" + "\n"+
                "1. The snake speeds up with each apple the user eats;" + "\n"+
                "2. The key \"Space\" allows you to pause and restart the game;" + "\n"+
                "3. Press A to accelerate, press D to resume the normal speed;" + "\n"+
                "4. There is a start screen, and two tabs on it, which are \"PLAY\" and \"HELP\";" + "\n"+
                "5. The game has background music, and the music pauses when the game is paused;" + "\n"+
                "6. There is a sign ‘Score’ to record the score, which is the length of the snake;" + "\n"+
                "7. There are 20 blue barriers, the game will over when the snake collides with barriers." + "\n");

        textArea.setLineWrap(true);
        textArea.setFont(new Font(null, Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(
                textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        tabLabelB.setLayout(new BorderLayout());

        tabLabelB.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        // GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Snake();
            }
        });


}
}