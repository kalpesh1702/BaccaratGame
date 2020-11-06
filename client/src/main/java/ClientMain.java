
import java.util.*;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static java.lang.Thread.sleep;

/**
 *
 * @author ashmeet
 */
public class ClientMain extends Application  {

    Stage window;
    VBox centerBox;
    HBox bankerCards,playerCards;
    ListView listView;
    Button playButton,exitButton,shuffleButton,bankerBidButton,playerBidButton,drawBidButton;
    TextField bidTextField;
    Rectangle2D screenSize;

    Label winningsAmount;
    double totalWinnings = 0;
    String lastClickedButton = "";
    boolean playAgain = false;

    ClientConnection clientConnection;
    String clientName = "";

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        screenSize = Screen.getPrimary().getVisualBounds();
        setLoginScene();
        window.show();
    }

    public void setLoginScene(){
        window.setTitle("Login Window");

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: green;");

        VBox mainVbox = new VBox();
        mainVbox.setAlignment(Pos.CENTER);

        Label welcome = new Label("♣ Welcome to ♦");
        welcome.setStyle("-fx-font: baghdad;-fx-font-size: 27;-fx-text-fill: gold");

        Label baccarat = new Label("Baccarat");
        baccarat.setStyle("-fx-font: Phosphate;-fx-font-size: 115;-fx-text-fill: gold");

        mainVbox.setMargin(welcome, new Insets(0,3,-40,0));
        mainVbox.setMargin(baccarat, new Insets(0,2,0,0));


        // UserName Details
        StackPane stackPane = new StackPane();
        Label nameLabel = new Label("UserName");
        nameLabel.setStyle("-fx-font: baghdad;-fx-font-size: 36;-fx-text-fill: white");

        Rectangle rectangleNameLabel = new Rectangle();
        rectangleNameLabel.setFill(Color.DARKGREEN);
        rectangleNameLabel.setHeight(60);
        rectangleNameLabel.setWidth(250);
        stackPane.getChildren().addAll(rectangleNameLabel,nameLabel);

        TextField txtUser = new TextField("");
        txtUser.setMinWidth(170);
        txtUser.setMaxWidth(170);
        txtUser.setMinHeight(60);
        txtUser.setMaxHeight(60);
        txtUser.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black");
        txtUser.setAlignment(Pos.CENTER);

        // IpAddress Details
        StackPane stackPane2 = new StackPane();
        Label ipAddressLabel = new Label("IPAddress");
        ipAddressLabel.setStyle("-fx-font: baghdad;-fx-font-size: 36;-fx-text-fill: white");

        Rectangle rectangleIpAddressLabel = new Rectangle();
        rectangleIpAddressLabel.setFill(Color.DARKGREEN);
        rectangleIpAddressLabel.setHeight(60);
        rectangleIpAddressLabel.setWidth(250);
        stackPane2.getChildren().addAll(rectangleIpAddressLabel,ipAddressLabel);

        TextField txtIpAddress = new TextField("");
        txtIpAddress.setMinWidth(170);
        txtIpAddress.setMaxWidth(170);
        txtIpAddress.setMinHeight(60);
        txtIpAddress.setMaxHeight(60);
        txtIpAddress.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black");
        txtIpAddress.setAlignment(Pos.CENTER);

        // Port Details
        StackPane stackPane3 = new StackPane();
        Label portLabel = new Label("Port");
        portLabel.setStyle("-fx-font: baghdad;-fx-font-size: 36;-fx-text-fill: white");

        Rectangle rectanglePortLabel = new Rectangle();
        rectanglePortLabel.setFill(Color.DARKGREEN);
        rectanglePortLabel.setHeight(60);
        rectanglePortLabel.setWidth(250);
        stackPane3.getChildren().addAll(rectanglePortLabel,portLabel);

        TextField txtPort = new TextField("");
        txtPort.setMinWidth(170);
        txtPort.setMaxWidth(170);
        txtPort.setMinHeight(60);
        txtPort.setMaxHeight(60);
        txtPort.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black");
        txtPort.setAlignment(Pos.CENTER);

        Button loginBtn = new Button("Connect");
        loginBtn.setMinWidth(250);
        loginBtn.setMaxWidth(250);
        loginBtn.setMinHeight(60);
        loginBtn.setMaxHeight(60);
        loginBtn.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black;-fx-background-color: darkorange;");

        Button clearBtn = new Button("Clear");
        clearBtn.setMinWidth(170);
        clearBtn.setMaxWidth(170);
        clearBtn.setMinHeight(60);
        clearBtn.setMaxHeight(60);
        clearBtn.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black;-fx-background-color: lightpink;");


        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.add(stackPane, 0, 0);
        gridPane.add(txtUser, 1,0);
        gridPane.add(stackPane2, 0, 1);
        gridPane.add(txtIpAddress, 1,1);
        gridPane.add(stackPane3, 0, 2);
        gridPane.add(txtPort, 1,2);
        gridPane.add(loginBtn, 0, 3);
        gridPane.add(clearBtn, 1, 3);
        gridPane.setAlignment(Pos.CENTER);

        loginBtn.setOnAction(e -> {
            String portText = txtPort.getText();
            String name = txtUser.getText();
            String ipAddress = txtIpAddress.getText();
            validateLoginDetails(name, ipAddress, portText);
        });

        clearBtn.setOnAction(e -> {
            txtUser.clear();
            txtPort.clear();
            txtIpAddress.clear();
        });

        mainVbox.getChildren().addAll(welcome,baccarat, gridPane);
        borderPane.setCenter(mainVbox);
        borderPane.setOpacity(1);

        window.setOnCloseRequest(e -> {
            window.close();
        });

        window.setScene(new Scene(borderPane,screenSize.getWidth(), screenSize.getHeight()));
        window.getScene().getStylesheets().add("style.css");
    }

    public void setGameScene(){

        window.setTitle("Game Window");
        lastClickedButton = "";

        // display messages on bottom

        listView = new ListView();
        listView.getItems().add("Welcome to Baccarat!");
        listView.getItems().add("[1] Enter your bid in the box on the right.");
        listView.getItems().add("[2] You can bid on either the Player, Banker, or Draw.");
        listView.getItems().add("[3] enter amount which accept only postive numbers");
        listView.getItems().add("click on play button!");
        listView.setMinHeight(200);
        listView.setMaxHeight(200);
        listView.setMinWidth(600);
        listView.setMaxWidth(600);
        listView.setStyle("-fx-control-inner-background: darkgreen;");

        HBox hboxBottom = new HBox();
        hboxBottom.setMargin(listView, new Insets(0,0,80,35));
        hboxBottom.getChildren().addAll(listView);
        hboxBottom.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(hboxBottom);


        // Boarder Pan Center for Banker
        centerBox = new VBox(15);
        borderPane.setCenter(centerBox);
        centerBox.setAlignment(Pos.CENTER);
        Label labelBanker = new Label("♥                        Banker                          ♦");
        labelBanker.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black");
        StackPane stackPaneBanker = new StackPane();
        Rectangle rectangleBid4 = new Rectangle();
        rectangleBid4.setFill(Color.DARKGREEN);
        rectangleBid4.setHeight(60);
        rectangleBid4.setWidth(600);
        stackPaneBanker.getChildren().addAll(rectangleBid4,labelBanker);
        bankerCards = new HBox(5);
        bankerCards.setAlignment(Pos.CENTER);

        // Boarder Pan Center for Player
        Label labelPlayer = new Label("♠                         Player                         ♣");
        labelPlayer.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black;");
        StackPane stackPanePlayer = new StackPane();
        Rectangle rectangleBid5 = new Rectangle();
        rectangleBid5.setFill(Color.DARKGREEN);
        rectangleBid5.setHeight(60);
        rectangleBid5.setWidth(600);
        stackPanePlayer.getChildren().addAll(rectangleBid5,labelPlayer);
        playerCards = new HBox(5);
        playerCards.setAlignment(Pos.CENTER);

        centerBox.getChildren().addAll(stackPaneBanker,bankerCards,playerCards,stackPanePlayer);

        // BoarderPan Left Side
        VBox vboxLeft = new VBox(5);
        borderPane.setLeft(vboxLeft);
        Image deckCard = new Image("Red_back.jpg");
        ImageView imageViewDeckCard = new ImageView(deckCard);
        imageViewDeckCard.setPreserveRatio(true);
        imageViewDeckCard.setFitHeight(150);
        imageViewDeckCard.setRotate(imageViewDeckCard.getRotate() + 90);

        shuffleButton = new Button("Shuffle");
        shuffleButton.setMinWidth(150);
        shuffleButton.setMaxWidth(150);
        shuffleButton.setMinHeight(60);
        shuffleButton.setMaxHeight(60);
        shuffleButton.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black;-fx-background-color: palegreen;");
        shuffleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                listView.getItems().add("The Dealer has shuffled the deck!");
                listView.scrollTo(listView.getItems().size());
            }
        });

        StackPane stackPane2 = new StackPane();
        Label winningsLabel = new Label("Winnings");
        winningsLabel.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: white");

        Rectangle rectangleBid2 = new Rectangle();
        rectangleBid2.setFill(Color.DARKGREEN);
        rectangleBid2.setHeight(60);
        rectangleBid2.setWidth(150);
        stackPane2.getChildren().addAll(rectangleBid2,winningsLabel);

        StackPane stackPane3 = new StackPane();
        winningsAmount = new Label("$0.0");
        winningsAmount.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black");

        Rectangle rectangleBid3 = new Rectangle();
        rectangleBid3.setFill(Color.WHITE);
        rectangleBid3.setHeight(60);
        rectangleBid3.setWidth(150);
        stackPane3.getChildren().addAll(rectangleBid3,winningsAmount);

        exitButton = new Button("Quit Game");
        exitButton.setMinWidth(185);
        exitButton.setMaxWidth(185);
        exitButton.setMinHeight(60);
        exitButton.setMaxHeight(60);
        exitButton.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black;-fx-background-color: darkorange;");

        window.setOnCloseRequest(e -> {
            exitButton.fire();
        });
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                disableAllButton();

                listView.getItems().add("Player want to quit game. Please wait....");
                listView.scrollTo(listView.getItems().size());

                HashMap<String,Object> message = new HashMap<String, Object>();
                message.put("type", "disconnect");
                message.put("name", clientName);
                clientConnection.send(message);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Map<String,Object> receivedMessage = clientConnection.receive();

                            if(receivedMessage == null){
                                Helper.alertBox("Details of login", "server not found with given details");
                                enableAllButton();
                                return;
                            }

                            if(receivedMessage.get("status").equals("Success")){
                                window.close();
                            }
                            else{
                                Helper.showErrorAlertBox();
                            }
                            enableAllButton();

                        } catch (Exception ex) {
                            Helper.showErrorAlertBox();
                            enableAllButton();
                        }
                    }
                });


            }
        });

        vboxLeft.getChildren().addAll(imageViewDeckCard, shuffleButton, stackPane2,stackPane3, exitButton);
        vboxLeft.setAlignment(Pos.CENTER);
        borderPane.setMargin(vboxLeft, new Insets(0,0,0,70));
        vboxLeft.setMargin(stackPane3, new Insets(-5,0,0,0));
        vboxLeft.setMargin(shuffleButton, new Insets(0,0,20,0));
        vboxLeft.setMargin(imageViewDeckCard, new Insets(0,0,-25,0));
        vboxLeft.setMargin(exitButton, new Insets(30,0,0,0));

        // BoarderPan Right
        VBox vboxRight = new VBox(5);
        borderPane.setRight(vboxRight);
        StackPane stackPane1 = new StackPane();

        Label bidLabel = new Label("Bid");
        bidLabel.setStyle("-fx-font: baghdad;-fx-font-size: 36;-fx-text-fill: white");

        Rectangle rectangleBid = new Rectangle();
        rectangleBid.setFill(Color.DARKGREEN);
        rectangleBid.setHeight(60);
        rectangleBid.setWidth(170);
        stackPane1.getChildren().addAll(rectangleBid,bidLabel);

        bankerBidButton = new Button("Banker");
        bankerBidButton.setMinWidth(170);
        bankerBidButton.setMaxWidth(170);
        bankerBidButton.setMinHeight(60);
        bankerBidButton.setMaxHeight(60);
        bankerBidButton.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black;-fx-background-color: gold;");

        bankerBidButton.setOnAction(e -> {
            lastClickedButton = "Banker";
        });


        drawBidButton = new Button("Draw");
        drawBidButton.setMinWidth(170);
        drawBidButton.setMaxWidth(170);
        drawBidButton.setMinHeight(60);
        drawBidButton.setMaxHeight(60);
        drawBidButton.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black;-fx-background-color: cornsilk ;");

        drawBidButton.setOnAction(e -> {
            lastClickedButton = "Draw";
        });

        playerBidButton = new Button("Player");
        playerBidButton.setMinWidth(170);
        playerBidButton.setMaxWidth(170);
        playerBidButton.setMinHeight(60);
        playerBidButton.setMaxHeight(60); // yellow green red
        playerBidButton.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black;-fx-background-color: crimson;");

        playerBidButton.setOnAction(e -> {
            lastClickedButton = "Player";
        });

        bidTextField = new TextField("");
        bidTextField.setMinWidth(170);
        bidTextField.setMaxWidth(170);
        bidTextField.setMinHeight(60);
        bidTextField.setMaxHeight(60);
        bidTextField.setPromptText("amount($)");
        bidTextField.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black");
        bidTextField.setAlignment(Pos.CENTER);

        playButton = new Button("Play");
        playButton.setMinWidth(170);
        playButton.setMaxWidth(170);
        playButton.setMinHeight(60);
        playButton.setMaxHeight(60); // yellow green red
        playButton.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black;-fx-background-color: darkorange;");

        playButton.setOnAction(e -> {
            if(lastClickedButton.equals("")){
                Helper.alertBox("Play Game", "Please select Bid on");
                return;
            }
            int amount;
            try{
                amount = Integer.parseInt(bidTextField.getText());
            }
            catch (NumberFormatException ex){
                Helper.alertBox("play Game", "please enter valid amount");
                return;
            }

            if(amount <= 0){
                Helper.alertBox("play Game", "please enter valid amount");
                return;
            }

            centerBox.setMargin(bankerCards, new Insets(0,0,130,0));
            centerBox.setMargin(playerCards, new Insets(130,0,0,0));

            if(playerCards.getChildren().size() == 3) { // clear cards at start of each game
                playerCards.getChildren().remove(0);
                playerCards.getChildren().remove(0);
                playerCards.getChildren().remove(0);
            } else if(playerCards.getChildren().size() == 2) {
                playerCards.getChildren().remove(0);
                playerCards.getChildren().remove(0);
            }

            if(bankerCards.getChildren().size() == 3) {
                bankerCards.getChildren().remove(0);
                bankerCards.getChildren().remove(0);
                bankerCards.getChildren().remove(0);
            } else if(bankerCards.getChildren().size() == 2) {
                bankerCards.getChildren().remove(0);
                bankerCards.getChildren().remove(0);
            }

            bankerCards.setOpacity(1);
            playerCards.setOpacity(1);

            disableAllButton();

            listView.getItems().add("");
            listView.getItems().add("Current bet is $" + amount + " on " + lastClickedButton+ ".");
            listView.scrollTo(listView.getItems().size());

            HashMap<String,Object> message = new HashMap<String, Object>();

            if(playAgain == false){
                message.put("type", "Play");
            }
            else{
                message.put("type", "Play Again");
            }

            message.put("bettingOn", lastClickedButton);
            message.put("amount", amount);
            message.put("name", clientName);
            clientConnection.send(message);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String,Object> receivedMessage = clientConnection.receive();

                        if(receivedMessage == null){
                            Helper.alertBox("Details of login", "server not found with given details");
                        }
                        else{
                            String status = (String) receivedMessage.get("status");
                            String content = (String) receivedMessage.get("content");

                            if ( status.equals("Failed")) {
                                Helper.alertBox("Details of Login", content);
                            }

                            setupPauseTransition(receivedMessage);
                        }

                        PauseTransition pause4 = new PauseTransition(Duration.seconds(8));
                        pause4.setOnFinished(e1-> {
                            listView.getItems().add("");
                            listView.getItems().add("Want to play again? Make another bid!");
                            listView.scrollTo(listView.getItems().size());
                            playAgain = true;

                            FadeTransition ft = new FadeTransition(Duration.seconds(1),playerCards);
                            ft.setFromValue(1);
                            ft.setToValue(0.5);
                            ft.setCycleCount(1);
                            ft.setAutoReverse(false);
                            ft.play();
                            FadeTransition ft2 = new FadeTransition(Duration.seconds(1),bankerCards);
                            ft2.setFromValue(1);
                            ft2.setToValue(0.5);
                            ft2.setCycleCount(1);
                            ft2.setAutoReverse(false);
                            ft2.play();

                            PauseTransition pause5 = new PauseTransition(Duration.seconds(2));
                            pause5.setOnFinished(w-> {
                                lastClickedButton = "";
                                enableAllButton();
                            });
                            pause5.play();
                        });
                        pause4.play();


                    } catch (Exception ex) {
                        Helper.showErrorAlertBox();
                    }
                }
            });


        });

        vboxRight.getChildren().addAll(stackPane1,bankerBidButton,drawBidButton,playerBidButton,bidTextField,playButton);
        vboxRight.setAlignment(Pos.CENTER);
        borderPane.setMargin(vboxRight, new Insets(0,50,0,0));

        centerBox.setMargin(bankerCards, new Insets(0,0,130,0));
        centerBox.setMargin(playerCards, new Insets(130,0,0,0));

        borderPane.setStyle("-fx-background-color: green;");
        borderPane.setOpacity(1);

        window.setScene(new Scene(borderPane,screenSize.getWidth(), screenSize.getHeight()));
        window.getScene().getStylesheets().add("style.css");

    }

    public void validateLoginDetails(String name, String ipAddress, String portText){

        int port;

        try{
            port = Integer.parseInt(portText);
        }
        catch (NumberFormatException ex){
            Helper.alertBox("Details of login", "please enter valid port number");
            return;
        }

        if(name.isEmpty() || ipAddress.isEmpty()) {
            Helper.alertBox("Details of login", "please enter valid details");
            return;
        }

        try {
            clientConnection = new ClientConnection(ipAddress, port);
        } catch (Exception ex) {
            Helper.alertBox("Details of login", "server not found with given details");
            return;
        }

        HashMap<String,Object> message = new HashMap<String, Object>();
        message.put("type", "connect");
        message.put("name", name);
        clientConnection.send(message);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String,Object> receivedMessage = clientConnection.receive();

                    if(receivedMessage == null){
                        Helper.alertBox("Details of login", "server not found with given details");
                        return;
                    }
                    String status = (String) receivedMessage.get("status");
                    String content = (String) receivedMessage.get("content");

                    if ( status.equals("Failed")) {
                        Helper.alertBox("Details of Login", content);
                    }

                    clientName = name;

                    setGameScene();

                } catch (Exception ex) {
                    Helper.showErrorAlertBox();
                }
            }
        });

    }

    public void setupPauseTransition(Map<String,Object> result){

        ArrayList<Card> playerHand = (ArrayList<Card>) result.get("playerHand");
        ArrayList<Card> bankerHand = (ArrayList<Card>) result.get("bankerHand");
        int playerHandTotal = (int) result.get("playerHandTotal");
        int bankerHandTotal = (int) result.get("bankerHandTotal");
        String winner = (String) result.get("winner");
        String bettingOn = (String) result.get("bettingOn");
        double winningAmount = (double) result.get("winningAmount");

        PauseTransition pauseBankerCard1 = new PauseTransition(Duration.seconds(2));
        pauseBankerCard1.setOnFinished(e-> {
            Image bankerCard1 = new Image(bankerHand.get(0).value+bankerHand.get(0).suite+".jpg");
            displayImageSetUp(bankerCard1, "Banker");
        });

        PauseTransition pauseBankerCard2 = new PauseTransition(Duration.seconds(4));
        pauseBankerCard2.setOnFinished(e-> {
            Image bankerCard1 = new Image(bankerHand.get(1).value+bankerHand.get(1).suite+".jpg");
            displayImageSetUp(bankerCard1, "Banker");
        });

        PauseTransition pauseBankerCard3 = new PauseTransition(Duration.seconds(6));
        pauseBankerCard3.setOnFinished(e-> {
            Image bankerCard1 = new Image(bankerHand.get(2).value+bankerHand.get(2).suite+".jpg");
            displayImageSetUp(bankerCard1, "Banker");
        });

        PauseTransition pausePlayerCard1 = new PauseTransition(Duration.seconds(1));
        pausePlayerCard1.setOnFinished(e-> {
            Image playerCard1 = new Image(playerHand.get(0).value+playerHand.get(0).suite+".jpg");
            displayImageSetUp(playerCard1, "Player");
        });

        PauseTransition pausePlayerCard2 = new PauseTransition(Duration.seconds(3));
        pausePlayerCard2.setOnFinished(e-> {
            Image playerCard1 = new Image(playerHand.get(1).value+playerHand.get(1).suite+".jpg");
            displayImageSetUp(playerCard1, "Player");
        });

        PauseTransition pausePlayerCard3 = new PauseTransition(Duration.seconds(5));
        pausePlayerCard3.setOnFinished(e-> {
            Image playerCard1 = new Image(playerHand.get(2).value+playerHand.get(2).suite+".jpg");
            displayImageSetUp(playerCard1, "Player");
        });

        pausePlayerCard1.play();
        pauseBankerCard1.play();
        pausePlayerCard2.play();
        pauseBankerCard2.play();

        if(playerHand.size() == 3){
            pausePlayerCard3.play();
        }

        if(bankerHand.size() == 3){
            pauseBankerCard3.play();
        }

        PauseTransition pause1 = new PauseTransition(Duration.seconds(6.5));
        pause1.setOnFinished(e-> {
            listView.getItems().add("");
            listView.getItems().add("Player Total: " + playerHandTotal + "  Banker Total: " + bankerHandTotal);
            listView.scrollTo(listView.getItems().size());
        });
        pause1.play();

        PauseTransition pause2 = new PauseTransition(Duration.seconds(7));
        pause2.setOnFinished(e-> {
            listView.getItems().add(winner + " wins!");
            listView.scrollTo(listView.getItems().size());
        });
        pause2.play();

        PauseTransition pause3 = new PauseTransition(Duration.seconds(7.5));
        pause3.setOnFinished(e-> {
            if(bettingOn.equals(winner)){
                listView.getItems().add("Congrats, you bet " + winner + "! You won your bet!");
            } else{
                listView.getItems().add("Sorry, you bet " + bettingOn + "! You lost your bet!");
            }
            listView.scrollTo(listView.getItems().size());
            totalWinnings += winningAmount;
            winningsAmount.setText("$" + Double.toString(totalWinnings));
        });
        pause3.play();

    }

    public void displayImageSetUp(Image image, String imageSetupFor){

        ImageView imageViewPlayer1 = new ImageView(image);
        imageViewPlayer1.setPreserveRatio(true);
        imageViewPlayer1.setFitHeight(130);

        if (imageSetupFor.equals("Player")){
            centerBox.setMargin(playerCards, new Insets(0,0,0,0));
            playerCards.getChildren().addAll(imageViewPlayer1);
        }
        else{
            centerBox.setMargin(bankerCards, new Insets(0,0,0,0));
            bankerCards.getChildren().addAll(imageViewPlayer1);
        }

        imageViewPlayer1.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.seconds(2),imageViewPlayer1);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    public void disableAllButton(){
        bankerBidButton.setDisable(true);
        drawBidButton.setDisable(true);
        playerBidButton.setDisable(true);
        bidTextField.setDisable(true);
        playButton.setDisable(true);
        shuffleButton.setDisable(true);
        exitButton.setDisable(true);
    }

    public void enableAllButton(){
        bankerBidButton.setDisable(false);
        drawBidButton.setDisable(false);
        playerBidButton.setDisable(false);
        bidTextField.setDisable(false);
        shuffleButton.setDisable(false);
        playButton.setDisable(false);
        bidTextField.setText("");
        bidTextField.setPromptText("amount($)");
        exitButton.setDisable(false);
    }

}
