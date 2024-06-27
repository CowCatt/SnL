import javax.swing.*;
import java.awt.*;

class BoardPanel extends JPanel {
    private int[][] ladders;
    private static final int SIZE = 10; // Board size (10x10)

    public BoardPanel(int[][] ladders) {
        this.ladders = ladders;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawLadders(g, ladders);
    }

    private void drawBoard(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Define the size of each cell
        int cellSize = Math.min(getWidth(), getHeight()) / SIZE;

        // Draw the grid
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int x = col * cellSize;
                int y = (SIZE - row - 1) * cellSize; // Flip vertically
                g2.drawRect(x, y, cellSize, cellSize);
            }
        }
    }

    private void drawLadders(Graphics g, int[][] ladders) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);

        // Define the size of each cell
        int cellSize = Math.min(getWidth(), getHeight()) / SIZE;

        for (int[] ladder : ladders) {
            int start = ladder[0];
            int end = ladder[1];

            // Calculate start coordinates
            int startRow = (start - 1) / SIZE;
            int startCol = (start - 1) % SIZE;
            int startX = startCol * cellSize + cellSize / 2;
            int startY = (SIZE - startRow - 1) * cellSize + cellSize / 2;

            // Calculate end coordinates
            int endRow = (end - 1) / SIZE;
            int endCol = (end - 1) % SIZE;
            int endX = endCol * cellSize + cellSize / 2;
            int endY = (SIZE - endRow - 1) * cellSize + cellSize / 2;

            // Draw the line
            g2.drawLine(startX, startY, endX, endY);
        }
    }
}
