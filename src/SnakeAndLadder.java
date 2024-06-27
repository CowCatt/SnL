import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.HashSet;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class SnakeAndLadder {
    //states
    private ArrayList<Player> players;
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private int boardSize;
    //0 = the game isnt started yet
    //1 = the game is started
    //2 = the game is over
    private int status;
    private int playerInTurn;
    private HashSet<Integer> num = new HashSet<>();
    private int snakeAmount = (int) (Math.random()*5+4);
    private int ladderAmount = (int) (Math.random()*5+4);

    public SnakeAndLadder(int boardSize){
        this.boardSize = boardSize;
        this.players = new ArrayList<Player>();
        this.snakes = new ArrayList<Snake>();
        this.ladders = new ArrayList<Ladder>();
        this.status = 0;

    }
    public void initiateGame(){
        //set the ladders
        int[][] ladders = new int[ladderAmount][2];
        for (int i = 0; i < ladderAmount; i++) {
            int val1, val2;
            do {
                val1 = (int) (Math.random() * 98 + 1);
            } while (num.contains(val1));
            num.add(val1);

            do {
                val2 = (int) (Math.random() * 99 + 1);
            } while (num.contains(val2) || val2 < val1);
            num.add(val2);

            ladders[i][0] = val1;
            ladders[i][1] = val2;
        }
        addLadders(ladders);
        //set the snakes
        int[][] snakes = new int[snakeAmount][2];
        for (int i = 0; i < snakeAmount; i++) {
            int val1, val2;
            do {
                val1 = (int) (Math.random() * 99 + 1);
            } while (num.contains(val1));
            num.add(val1);

            do {
                val2 = (int) (Math.random() * 98 + 1);
            } while (num.contains(val2) || val2 > val1);
            num.add(val2);

            snakes[i][0] = val1;
            snakes[i][1] = val2;
        }
        addSnakes(snakes);
    }

    public void drawLadders(Graphics g, int[][] ladders) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);

        // Define the size of each cell
        int cellSize = 100;

        for (int[] ladder : ladders) {
            int start = ladder[0];
            int end = ladder[1];

            // Calculate start coordinates
            int startRow = (start - 1) / 10;
            int startCol = (start - 1) % 10;
            int startX = startCol * cellSize + cellSize / 2;
            int startY = (100 - startRow - 1) * cellSize + cellSize / 2;

            // Calculate end coordinates
            int endRow = (end - 1) / 10;
            int endCol = (end - 1) % 10;
            int endX = endCol * cellSize + cellSize / 2;
            int endY = (10 - endRow - 1) * cellSize + cellSize / 2;

            // Draw the line
            g2.drawLine(startX, startY, endX, endY);
        }
    }

    //setter
    public void setBoardSize(int boardSize){
        this.boardSize = boardSize;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public void setPlayerInTurn(int p){
        this.playerInTurn = p;
    }
    public void addPlayer(Player p){
        this.players.add(p);
    }
    public void addSnake(Snake s){
        this.snakes.add(s);
    }
    public void addSnakes(int [][] s) {
        for (int i=0;i<s.length;i++){
            Snake snake = new Snake(s[i][0], s[i][1]);
            this.snakes.add(snake);
        }
    }
    public void addLadder(Ladder l){
        this.ladders.add(l);
    }
    public void addLadders(int [][] l){
        for (int i=0;i<l.length;i++){
            Ladder ladder = new Ladder(l[i][0], l[i][1]);
            this.ladders.add(ladder);
        }
    }
    //getter
    public int getBoardSize(){
        return boardSize;
    }
    public int getStatus(){
        return status;
    }
    public int getPlayerInTurn(){
        return playerInTurn;
    }
    public ArrayList<Player> getPlayers(){
        return players;
    }
    public ArrayList<Snake> getSnakes(){
        return snakes;
    }
    public ArrayList<Ladder> getLadders(){
        return ladders;
    }

    public int getTurn(){
        if (this.status == 0){
            double r = Math.random();
            if (r<0.5) return 0;
            else return 1;
        }
        else {
            if(playerInTurn==0) return 1;
            else return 0;
        }
    }
    public void movePlayer(Player p, int x) {
        p.moveAround(x);
        System.out.println("The new position is: " + p.getPosition());

        // Checking the ladder
        for (int i = 0; i < this.ladders.size(); i++) {
            Ladder l = this.ladders.get(i);
            if (p.getPosition() == l.getFromPosition()) {
                p.setPosition(l.getToPosition());
                JOptionPane.showMessageDialog(null, p.getUserName() + " got ladder from " + l.getFromPosition() + " to " + l.getToPosition());
            }
        }

        // Checking the snake
        for (int i = 0; i < this.snakes.size(); i++) {
            Snake s = this.snakes.get(i);
            if (p.getPosition() == s.getFromPosition()) {
                p.setPosition(s.getToPosition());
                JOptionPane.showMessageDialog(null, p.getUserName() + " got snake from " + s.getFromPosition() + " slide down to " + s.getToPosition());
            }
        }

        System.out.println(p.getUserName() + " new position is " + p.getPosition());
        if (p.getPosition() == 100) {
            this.status = 2;
            System.out.println("The winner is: " + p.getUserName());
        }
    }
}