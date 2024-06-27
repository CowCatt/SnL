import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Board extends JFrame {
    private SnakeAndLadder game;
    private ArrayList<Player> players;
    private int numPlayers;
    private int currentPlayerIndex;
    private JLabel[][] boardLabels;
    private JLabel[] playerLabels;
    JFrame frame;
    JLabel displayField;
    ImageIcon image;

    public Board() {
        game = new SnakeAndLadder(100);
        game.initiateGame();
        players = new ArrayList<>();
        numPlayers = 0;
        currentPlayerIndex = 0;

        // Welcome message with image
        frame = new JFrame("Ular Tangga");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        image = new ImageIcon(getClass().getResource("ularTangga.jpeg")); //add image file
        displayField = new JLabel(image);
        frame.add(displayField);
        frame.setSize(150, 150); // Resize the image to 300x300
        JLabel imageLabel = new JLabel(image); // Create a JLabel for the image
        imageLabel.setHorizontalAlignment(JLabel.CENTER); // Center the image horizontally

        JLabel textLabel = new JLabel("Selamat datang di permainan ular tangga!"); // Create a JLabel for the text
        textLabel.setHorizontalAlignment(JLabel.CENTER); // Center the text horizontally

        JPanel panel = new JPanel(); // Create a JPanel
        panel.setLayout(new BorderLayout()); // Set the layout to BorderLayout

        panel.add(imageLabel, BorderLayout.CENTER); // Add the image label to the center
        panel.add(textLabel, BorderLayout.SOUTH); // Add the text label to the south (below the image)

        JOptionPane.showMessageDialog(this, panel); // Show the message dialog with the panel


        // Create the board GUI
        setLayout(new BorderLayout());
        JPanel boardPanel = new JPanel(new GridLayout(10, 10));
        boardLabels = new JLabel[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JLabel label = new JLabel(String.valueOf((i * 10) + j + 1));
                label.setHorizontalAlignment(JLabel.CENTER);
                boardLabels[i][j] = label;
                boardPanel.add(label);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // Create the player labels
        JPanel playerPanel = new JPanel(new FlowLayout());
        playerLabels = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            JLabel label = new JLabel("Player " + (i + 1) + ": ");
            label.setForeground(getPlayerColor(i));
            playerLabels[i] = label;
            playerPanel.add(label);
        }
        add(playerPanel, BorderLayout.NORTH);

        // Show the initial GUI
        setSize(800, 800);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);

        // Ask for the number of players
        String numPlayersStr = JOptionPane.showInputDialog("Enter the number of players (2-4):");
        numPlayers = Integer.parseInt(numPlayersStr);
        if (numPlayers < 2 || numPlayers > 4) {
            JOptionPane.showMessageDialog(this, "Invalid number of players. Please enter 2-4.");
            System.exit(0);
        }

        // Ask for the player names
        for (int i = 0; i < numPlayers; i++) {
            String playerName = JOptionPane.showInputDialog("Enter player " + (i + 1) + "'s name:");
            Player player = new Player(playerName);
            players.add(player);
            game.addPlayer(player);
        }

        // Start the game
        game.setStatus(1);
        currentPlayerIndex = game.getTurn();
        updatePlayerLabels();
    }

    private Color getPlayerColor(int playerIndex) {
        switch (playerIndex) {
            case 0:
                return Color.BLUE;
            case 1:
                return Color.RED;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.MAGENTA;
            default:
                return Color.BLACK;
        }
    }

    private void updatePlayerLabels() {
        for (int i = 0; i < numPlayers; i++) {
            Player player = players.get(i);
            JLabel label = playerLabels[i];
            label.setText("Player " + (i + 1) + ": " + player.getUserName() + " - Position: " + player.getPosition());
        }
    }

    private void updateBoardLabels() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JLabel label = boardLabels[i][j];
                label.setText(String.valueOf((i * 10) + j + 1));
            }
        }
        for (Player player : players) {
            int position = player.getPosition();
            if (position >= 1 && position <= 100) { // Check if the position is within the board
                int row = (position - 1) / 10;
                int col = (position - 1) % 10;
                if (row >= 0 && row < 10 && col >= 0 && col < 10) { // Check if the row and col indices are within the bounds of the array
                    JLabel label = boardLabels[row][col];
                    StringBuilder playerNameBuilder = new StringBuilder();
                    for (Player p : players) {
                        if (p.getPosition() == position) {
                            playerNameBuilder.append(p.getUserName()).append(", ");
                        }
                    }
                    String playerNames = playerNameBuilder.toString();
                    if (playerNames.endsWith(", ")) {
                        playerNames = playerNames.substring(0, playerNames.length() - 2);
                    }
                    label.setText(playerNames);
                    label.setForeground(getPlayerColor(players.indexOf(player)));
                }
            }
        }
    }

    public void playRound() {
        Player currentPlayer = players.get(currentPlayerIndex);
        String rollPrompt = "Roll the dice for " + currentPlayer.getUserName() + "!";

        JOptionPane rollDialog = new JOptionPane(rollPrompt, JOptionPane.QUESTION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new String[] {"Roll"});
        JDialog dialog = rollDialog.createDialog(this, "Roll the dice");
        dialog.setModal(true);
        dialog.setVisible(true);

        int roll;
        boolean rollAgain = true;
        while (rollAgain) {
            roll = currentPlayer.rollDice();
            System.out.println("Player " + currentPlayer.getUserName() + " rolled a " + roll);

            // Update the game state
            game.movePlayer(currentPlayer, roll);
            updatePlayerLabels();
            updateBoardLabels();

            // Check if the player can roll again
            if (currentPlayer.getCanRollAgain()) {
                // Ask the user if they want to roll again
                int response = JOptionPane.showConfirmDialog(this, "You rolled a 6! Roll again?", "Roll Again?", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.NO_OPTION) {
                    rollAgain = false;
                }
            }
            else {
                rollAgain = false;
            }
        }

        if(game.getStatus() == 2){
            JOptionPane.showMessageDialog(this, "Game over! The winner is " + currentPlayer.getUserName() + ", GG!");
            System.exit(0);
        }

        for(Player player : players){
            if(player.getPosition() == 100){
                game.setStatus(2);
                JOptionPane.showMessageDialog(this, "Game over! The winner is " + player.getUserName() + ", GG!");
            }
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
    }

    public static void main(String[] args) {
        Board board = new Board();
        while (true) {
            board.playRound();
        }
    }
}