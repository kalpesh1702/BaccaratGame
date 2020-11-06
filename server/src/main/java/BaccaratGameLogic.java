import java.util.ArrayList;

public class BaccaratGameLogic {

    public String whoWon(ArrayList<Card> playerHand, ArrayList<Card> bankerHand){

        int bankerAmount = handTotal(bankerHand);
        int playerAmount = handTotal(playerHand);

        if((playerAmount == 9 || playerAmount == 8) && (playerAmount != bankerAmount)){
            return "Player";
        } else if((bankerAmount == 9 || bankerAmount == 8) && (playerAmount != bankerAmount)){
            return "Banker";
        } else if(playerAmount == bankerAmount){
            return "Draw";
        } else if(bankerAmount > playerAmount) {
            return "Banker";
        } else
            return "Player";
    }

    public int handTotal(ArrayList<Card> hand){

        int total = 0;

        for(Card c : hand){
            if(c.value >= 1 && c.value <= 9){
                total += c.value;
            }
        }

        total = total % 10;
        return total;
    }

    public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard){

        int totalCards = handTotal(hand);

        if(totalCards <= 2){
            return true;
        }

        if(totalCards >= 7){
            return false;
        }

        if(playerCard == null){
            if(totalCards <= 5)
                return true;
            else return false;
        }

        if(playerCard.value <= 1) {
            if(totalCards <= 3) return true;
            else return false;
        }

        if(playerCard.value <= 3) {
            if(totalCards <= 4) return true;
            else return false;
        }

        if(playerCard.value <= 5) {
            if(totalCards <= 5) return true;
            else return false;
        }

        if(playerCard.value <= 7) {
            if(totalCards <= 6) return true;
            else return false;
        }

        if(playerCard.value <= 8){
            if(totalCards <= 2) return true;
            else return false;
        }

        if(playerCard.value <= 9) {
            if(totalCards <= 3) return true;
            else return false;
        }

        return false;
    }

    public boolean evaluatePlayerDraw(ArrayList<Card> hand){
        int totalCards = handTotal(hand);

        if(totalCards <= 5){
            return true;
        } else
            return false;
    }
}
