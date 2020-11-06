import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class BaccaratGameTest {


    @Test // Card constructor
    void CardConstructorTest(){
        Card card1 = new Card("S",4);
        assertEquals(4, card1.value);
    }

    // BaccaratDealer tests

    @Test // BaccaratDealer constructor
    void DealerConstructorTest(){
        BaccaratDealer dealer = new BaccaratDealer();
        assertNotNull(dealer.deck);
    }

    @Test // BaccaratDealer -> generateDeck()
    void GenerateDeck1Test(){
        BaccaratDealer dealer = new BaccaratDealer();
        dealer.generateDeck();

        assertEquals(1,dealer.deck.get(0).value);
        assertEquals("H",dealer.deck.get(0).suite);
        assertEquals(2,dealer.deck.get(1).value);
        assertEquals("H",dealer.deck.get(1).suite);
        assertEquals(13,dealer.deck.get(12).value);
        assertEquals("H",dealer.deck.get(12).suite);

    }

    @Test // BaccaratDealer -> generateDeck()
    void GenerateDeck2Test(){
        BaccaratDealer dealer = new BaccaratDealer();
        dealer.generateDeck();

        assertEquals(1,dealer.deck.get(26).value);
        assertEquals("S",dealer.deck.get(26).suite);
        assertEquals(2,dealer.deck.get(27).value);
        assertEquals("S",dealer.deck.get(27).suite);
        assertEquals(13,dealer.deck.get(38).value);
        assertEquals("S",dealer.deck.get(38).suite);
    }

    @Test // BaccaratDealer -> dealHand()
    void DealHand1Test(){
        BaccaratDealer dealer = new BaccaratDealer();
        dealer.generateDeck();
        ArrayList<Card> hand = dealer.dealHand();

        assertEquals(1,hand.get(0).value);
        assertEquals("H",hand.get(0).suite);
        assertEquals(2,hand.get(1).value);
        assertEquals("H",hand.get(1).suite);
    }

    @Test // BaccaratDealer -> dealHand()
    void DealHand2Test(){
        BaccaratDealer dealer = new BaccaratDealer();
        dealer.generateDeck();
        dealer.deck.remove(0);
        dealer.deck.remove(0);
        ArrayList<Card> hand = dealer.dealHand();


        assertEquals(3,hand.get(0).value);
        assertEquals("H",hand.get(0).suite);
        assertEquals(4,hand.get(1).value);
        assertEquals("H",hand.get(1).suite);
    }

    @Test // BaccaratDealer -> drawOne()
    void DrawOne1Test(){
        BaccaratDealer dealer = new BaccaratDealer();
        dealer.generateDeck();

        Card card = dealer.drawOne();

        assertEquals(1,card.value);
        assertEquals("H",card.suite);
    }

    @Test // BaccaratDealer -> drawOne()
    void DrawOne2Test(){
        BaccaratDealer dealer = new BaccaratDealer();
        dealer.generateDeck();
        dealer.deck.remove(0);

        Card card = dealer.drawOne();

        assertEquals(2,card.value);
        assertEquals("H",card.suite);
    }

    @Test // BaccaratDealer -> shuffleDeck()
    void ShuffleDeck1Test(){
        BaccaratDealer dealer = new BaccaratDealer();
        dealer.generateDeck();
        Card cardTemp1 = dealer.deck.get(0);

        assertEquals(1,cardTemp1.value);
        assertEquals("H",cardTemp1.suite);

        dealer.shuffleDeck();
        Card cardTemp2 = dealer.deck.get(0);

        assertNotSame(cardTemp1,cardTemp2);
    }

    @Test // BaccaratDealer -> shuffleDeck()
    void ShuffleDeck2Test(){
        BaccaratDealer dealer = new BaccaratDealer();
        dealer.generateDeck();
        dealer.deck.remove(0);
        Card cardTemp1 = dealer.deck.get(0);

        assertEquals(2,cardTemp1.value);
        assertEquals("H",cardTemp1.suite);

        dealer.shuffleDeck();
        Card cardTemp2 = dealer.deck.get(0);

        assertNotSame(cardTemp1,cardTemp2);
    }

    @Test // BaccaratDealer -> deckSize()
    void DeckSize1Test(){
        BaccaratDealer dealer = new BaccaratDealer();
        dealer.generateDeck();

        assertEquals(52, dealer.deckSize());

        dealer.deck.remove(0);

        assertEquals(51, dealer.deckSize());
    }

    @Test // BaccaratDealer -> deckSize()
    void DeckSize2Test(){
        BaccaratDealer dealer = new BaccaratDealer();
        dealer.generateDeck();

        assertEquals(52, dealer.deckSize());

        dealer.deck.remove(0);
        dealer.deck.remove(0);
        dealer.deck.remove(0);
        dealer.deck.remove(0);

        assertEquals(48, dealer.deckSize());
    }

    // BaccaratGameLogic tests

    @Test // BaccaratGameLogic -> whoWon()
    void WhoWon1Test(){
        BaccaratGameLogic logic = new BaccaratGameLogic();
        Card card1 = new Card("H",4);
        Card card2 = new Card("D",4); // 9
        Card card3 = new Card("S",1);
        Card card4 = new Card("C",5);
        Card card5 = new Card("D",5); // 2
        Card card6 = new Card("S",2);
        ArrayList<Card> hand1 = new ArrayList<Card>();
        ArrayList<Card> hand2 = new ArrayList<Card>();
        hand1.add(card1);
        hand1.add(card2);
        hand1.add(card3);
        hand2.add(card4);
        hand2.add(card5);
        hand2.add(card6);

        String winner = logic.whoWon(hand1,hand2);

        assertEquals("Player",winner);

    }

    @Test // BaccaratGameLogic -> whoWon()
    void WhoWon2Test(){
        BaccaratGameLogic logic = new BaccaratGameLogic();
        Card card1 = new Card("H",4);
        Card card2 = new Card("D",2); // 7
        Card card3 = new Card("S",1);
        Card card4 = new Card("C",9);
        Card card5 = new Card("D",8); // 7
        ArrayList<Card> hand1 = new ArrayList<Card>();
        ArrayList<Card> hand2 = new ArrayList<Card>();
        hand1.add(card1);
        hand1.add(card2);
        hand1.add(card3);
        hand2.add(card4);
        hand2.add(card5);

        String winner = logic.whoWon(hand1,hand2);

        assertEquals("Draw",winner);
    }

    @Test // BaccaratGameLogic -> handTotal()
    void HandTotal1Test(){
        BaccaratGameLogic logic = new BaccaratGameLogic();
        Card card1 = new Card("H",4);
        Card card2 = new Card("D",8);
        Card card3 = new Card("S",1);
        Card card4 = new Card("C",10);
        Card card5 = new Card("D",12);
        Card card6 = new Card("S",2);
        ArrayList<Card> hand1 = new ArrayList<Card>();
        ArrayList<Card> hand2 = new ArrayList<Card>();
        hand1.add(card1);
        hand1.add(card2);
        hand1.add(card3);
        hand2.add(card4);
        hand2.add(card5);
        hand2.add(card6); // hand 1 = 1, hand 2 = 2

        assertEquals(3,logic.handTotal(hand1));
        assertEquals(2,logic.handTotal(hand2));
    }

    @Test // BaccaratGameLogic -> handTotal()
    void HandTotal2Test(){
        BaccaratGameLogic logic = new BaccaratGameLogic();
        Card card1 = new Card("H",4);
        Card card2 = new Card("D",2); // 7
        Card card3 = new Card("S",1);
        Card card4 = new Card("C",9);
        Card card5 = new Card("D",8); // 7
        ArrayList<Card> hand1 = new ArrayList<Card>();
        ArrayList<Card> hand2 = new ArrayList<Card>();
        hand1.add(card1);
        hand1.add(card2);
        hand1.add(card3);
        hand2.add(card4);
        hand2.add(card5);

        assertEquals(7,logic.handTotal(hand1));
        assertEquals(7,logic.handTotal(hand2));
    }

    @Test // BaccaratGameLogic -> evaluateBankerDraw()
    void EvaluateBankerDraw1Test(){
        BaccaratGameLogic logic = new BaccaratGameLogic();
        Card card1 = new Card("H",2);
        Card card3 = new Card("S",2);
        Card card4 = new Card("C",2);
        Card card5 = new Card("D",8);
        Card card6 = new Card("C",4);
        ArrayList<Card> hand1 = new ArrayList<Card>();
        ArrayList<Card> hand2 = new ArrayList<Card>();
        ArrayList<Card> hand3 = new ArrayList<Card>();
        hand1.add(card1); // hand1: 4
        hand1.add(card3);
        hand2.add(card4); // hand2: 10
        hand2.add(card5);
        hand3.add(card1); // hand3: 6
        hand3.add(card6);

        assertEquals(true,logic.evaluateBankerDraw(hand1,card4));
        assertEquals(true,logic.evaluateBankerDraw(hand2,card5));
        assertEquals(false,logic.evaluateBankerDraw(hand3,card5));
    }

    @Test // BaccaratGameLogic -> evaluateBankerDraw()
    void EvaluateBankerDraw2Test(){
        BaccaratGameLogic logic = new BaccaratGameLogic();
        Card card1 = new Card("H",2);
        Card card3 = new Card("S",3);
        Card card4 = new Card("C",2);
        Card card5 = new Card("D",6);
        Card card6 = new Card("C",1);
        Card card7 = new Card("C",4);
        ArrayList<Card> hand1 = new ArrayList<Card>();
        ArrayList<Card> hand2 = new ArrayList<Card>();
        ArrayList<Card> hand3 = new ArrayList<Card>();
        hand1.add(card1); // hand1: 5, card7 = 4
        hand1.add(card3);
        hand2.add(card4); // hand2: 8, card5 = 6
        hand2.add(card5);
        hand3.add(card1); // hand3: 3, card 5 = 6
        hand3.add(card6);

        assertEquals(true,logic.evaluateBankerDraw(hand1,card7));
        assertEquals(false,logic.evaluateBankerDraw(hand2,card5));
        assertEquals(true,logic.evaluateBankerDraw(hand3,card5));
    }

    @Test // BaccaratGameLogic -> evaluatePlayerDraw()
    void EvaluatePlayerDraw1Test(){
        BaccaratGameLogic logic = new BaccaratGameLogic();
        Card card1 = new Card("H",4);
        Card card3 = new Card("S",1);
        Card card4 = new Card("C",9);
        Card card5 = new Card("D",8);
        ArrayList<Card> hand1 = new ArrayList<Card>();
        ArrayList<Card> hand2 = new ArrayList<Card>();
        hand1.add(card1);
        hand1.add(card3);
        hand2.add(card4);
        hand2.add(card5);

        assertEquals(true,logic.evaluatePlayerDraw(hand1));
        assertEquals(false,logic.evaluatePlayerDraw(hand2));
    }

    @Test // BaccaratGameLogic -> evaluatePlayerDraw()
    void EvaluatePlayerDraw2Test(){
        BaccaratGameLogic logic = new BaccaratGameLogic();
        Card card1 = new Card("H",9);
        Card card3 = new Card("S",5); // 4
        Card card4 = new Card("C",3);
        Card card5 = new Card("D",1);
        ArrayList<Card> hand1 = new ArrayList<Card>();
        ArrayList<Card> hand2 = new ArrayList<Card>();
        hand1.add(card1);
        hand1.add(card3);
        hand2.add(card4);
        hand2.add(card5);

        assertEquals(true,logic.evaluatePlayerDraw(hand1));
        assertEquals(true,logic.evaluatePlayerDraw(hand2));
    }

    // BaccaratGame tests

    @Test // BaccaratGame -> evaluateWinnings()
    void EvaluateWinnings1Test(){
        BaccaratGame game = new BaccaratGame("Player", 50);
        ArrayList<Card> playerHand = new ArrayList<>();
        ArrayList<Card> bankerHand = new ArrayList<>();
        Card card1 = new Card("H",4);
        Card card3 = new Card("S",1);
        Card card4 = new Card("C",9);
        Card card5 = new Card("D",4);
        playerHand.add(card1); // playerHand = 5
        playerHand.add(card3);
        bankerHand.add(card4); // bankerHand = 3
        bankerHand.add(card5);
        game.playerHand.addAll(playerHand);
        game.bankerHand.addAll(bankerHand);

        assertEquals(50,game.evaluateWinnings());

        ArrayList<Card> playerHand2 = new ArrayList<>();
        ArrayList<Card> bankerHand2 = new ArrayList<>();
        playerHand2.add(card1);
        bankerHand2.add(card5);
        game.playerHand.clear();
        game.bankerHand.clear();
        game.playerHand.addAll(playerHand2);
        game.bankerHand.addAll(bankerHand2);

        game.bettingOn = "Draw";
        assertEquals(50.0*8,game.evaluateWinnings());
    }

    @Test
    void EvaluateWinnings2Test(){
        BaccaratGame game = new BaccaratGame("Player", 50);
        ArrayList<Card> playerHand = new ArrayList<>();
        ArrayList<Card> bankerHand = new ArrayList<>();
        Card card1 = new Card("H",1);
        Card card3 = new Card("S",2);
        Card card4 = new Card("C",4);
        Card card5 = new Card("D",4);
        playerHand.add(card1); // playerHand = 3
        playerHand.add(card3);
        bankerHand.add(card4); // bankerHand = 8
        bankerHand.add(card5);
        game.playerHand.addAll(playerHand);
        game.bankerHand.addAll(bankerHand);

        assertEquals(-50,game.evaluateWinnings());
        game.bettingOn = "Banker";
        assertEquals(47.5,game.evaluateWinnings());
    }

    @Test // BaccaratGame constructor
    void BaccaratGameConstructorTest(){
        BaccaratGame game = new BaccaratGame("Player", 10);
        assertNotNull(game.playerHand);
        assertNotNull(game.bankerHand);
        assertNotNull(game.dealer);
        assertNotNull(game.gameLogic);
    }
}