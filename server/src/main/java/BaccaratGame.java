import java.util.ArrayList;

public class BaccaratGame {

    ArrayList<Card> playerHand;
    ArrayList<Card> bankerHand;
    BaccaratDealer dealer;
    BaccaratGameLogic gameLogic;
    double currentBet;
    String bettingOn;

    public BaccaratGame(String thebettingOn, double thecurrentBet){
        bettingOn = thebettingOn;
        currentBet = thecurrentBet;
        playerHand = new ArrayList<Card>();
        bankerHand = new ArrayList<Card>();
        dealer = new BaccaratDealer();
        gameLogic = new BaccaratGameLogic();
        dealer.generateDeck();
    }

    public double evaluateWinnings(){
        double winnings = 0;
        String winner = gameLogic.whoWon(playerHand, bankerHand);

        if(bettingOn.equals(winner) && winner.equals("Banker")){
            winnings = currentBet;
            winnings -= (currentBet * 0.05);
        }

        else if(bettingOn.equals(winner) && winner.equals("Player")){
            winnings = currentBet;
        }

        else if(bettingOn.equals(winner) && winner.equals("Draw")){
            winnings = currentBet * 8;
        }

        else if(!bettingOn.equals(winner) && !winner.equals("Draw")){
            winnings = currentBet * -1;
        }

        return winnings;
    }

}
