import javax.swing.BorderFactory;
import javax.swing.*;
import java.awt.*;

public class GUI {
    public GUI() {
        JFrame frame = new JFrame();

        JButton client = new JButton("client");
        JLabel clientTemp = new JLabel("some text");

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(client);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Warehouse");
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String args[]) {

    }
}
