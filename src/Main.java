import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.Color;

public class Main {

    public static void main(String[] args) {
        int n1 = 15;
        int n2 = 42;
        int n3 = 78;

        JFrame frame = new JFrame("10x10 Board");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(10, 10)); // 10 rows and 10 columns

        // Create and add buttons
        for (int i = 0; i < 100; i++) {
            int flippedIndex = 99 - i; // Flip the index to place 1 at the bottom and 100 at the top
            JButton button = new JButton(String.valueOf(flippedIndex + 1));
            if (flippedIndex == n1 - 1) { // n is 1-based index, i is 0-based index
                button.setBackground(Color.YELLOW); // Change the color for n1
            } else if (flippedIndex == n2 - 1) {
                button.setBackground(Color.RED); // Change the color for n2
            } else if (flippedIndex == n3 - 1) {
                button.setBackground(Color.BLUE); // Change the color for n3
            }
            frame.add(button);
        }

        // Set the frame visibility
        frame.setVisible(true);
    }
}