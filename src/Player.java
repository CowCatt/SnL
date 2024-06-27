public class Player{
    private String userName;
    private int position;
    private boolean canRollAgain;

    //methods
    public Player(String initName){
        this.userName = initName;
        this.position = 0;
    }

    //setter
    public void setName(String userName){
        this.userName = userName;

    }

    public void setPosition(int pos){
        this.position = position;
    }

    public void setCanRollAgain(boolean canRollAgain){
        this.canRollAgain = canRollAgain;
    }

    //getter
    public String getUserName(){
        return this.userName;

    }

    public int getPosition(){
        return this.position;
    }

    public boolean getCanRollAgain(){
        return this.canRollAgain;
    }

    //rollDice method
    public int rollDice() {
        int roll = (int) (Math.random()*6)+1;
        if (roll == 6) {
            setCanRollAgain(true);
        } else {
            setCanRollAgain(false);
        }
        return roll;
    }

    public void moveAround(int x){
        if((this.position + x) > 100){
            this.position = 100 - (this.position + x) % 100;
        }
        else{
            this.position += x;
        }

    }
}