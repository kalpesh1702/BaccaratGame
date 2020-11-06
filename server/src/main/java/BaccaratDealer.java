import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class BaccaratDealer {

    ArrayList<Card> deck;

    BaccaratDealer(){
        deck = new ArrayList<Card>();
    }

    public void generateDeck(){

        // generate hearts
        for(int i = 1; i <= 13; i++){
            Card card = new Card("H", i);
            deck.add(card);
        }

        // generate diamonds
        for(int i = 1; i <= 13; i++){
            Card card = new Card("D", i);
            deck.add(card);
        }

        // generate spades
        for(int i = 1; i <= 13; i++){
            Card card = new Card("S", i);
            deck.add(card);
        }

        // generate clubs
        for(int i = 1; i <= 13; i++){
            Card card = new Card("C", i);
            deck.add(card);
        }
    }

    public ArrayList<Card> dealHand(){
        ArrayList<Card> temp = new ArrayList<>();
        Card card1 = deck.get(0); // get 1st and 2nd card
        Card card2 = deck.get(1);
        temp.add(card1);
        temp.add(card2);
        deck.remove(0); // delete from deck
        deck.remove(0);

        return temp;
    }

    public Card drawOne(){
        Card card = deck.get(0); // get top card
        deck.remove(0); // delete from deck
        return card;
    }


    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    public int deckSize(){
        return this.deck.size(); // return the deck size
    }
}