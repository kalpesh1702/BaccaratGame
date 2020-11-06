import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ServerMain extends Application {

    Stage window;
    Text login;
    Label lblPort;
    TextField txtPort;
    Button startServerBtn;

    ServerConnection serverConnection;
    Rectangle2D screenSize;
    static ListView listView;

    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("Server");
        screenSize = Screen.getPrimary().getVisualBounds();
        setLoginScene();
        window.setMaximized(true);
        window.show();

    }

    public static void main(String args[]){
        launch(args);
    }

    public void setLoginScene(){

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

        StackPane stackPane = new StackPane();
        Label portLabel = new Label("Port Number");
        portLabel.setStyle("-fx-font: baghdad;-fx-font-size: 36;-fx-text-fill: white");

        Rectangle rectanglePortLabel = new Rectangle();
        rectanglePortLabel.setFill(Color.DARKGREEN);
        rectanglePortLabel.setHeight(60);
        rectanglePortLabel.setWidth(250);
        stackPane.getChildren().addAll(rectanglePortLabel,portLabel);

        TextField txtPort = new TextField("");
        txtPort.setMinWidth(170);
        txtPort.setMaxWidth(170);
        txtPort.setMinHeight(60);
        txtPort.setMaxHeight(60);
        txtPort.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black");
        txtPort.setAlignment(Pos.CENTER);

        Button startServerBtn = new Button("Start");
        startServerBtn.setMinWidth(170);
        startServerBtn.setMaxWidth(170);
        startServerBtn.setMinHeight(60);
        startServerBtn.setMaxHeight(60);
        startServerBtn.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black;-fx-background-color: darkorange;");

        startServerBtn.setOnAction(e -> {
            int port;
            try{
                port = Integer.parseInt(txtPort.getText());
            }
            catch (NumberFormatException ex){
                Helper.alertBox("Details of login", "please enter valid port number");
                return;
            }
            if(port <= 0 ){
                Helper.alertBox("Details of login", "please enter valid port number");
                return;
            }
            setServerInformationScene();
            serverConnection = new ServerConnection(port);
        });

        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.add(stackPane, 0, 0);
        gridPane.add(txtPort, 1, 0);
        gridPane.add(startServerBtn, 2, 0);
        gridPane.setAlignment(Pos.CENTER);

        mainVbox.getChildren().addAll(welcome,baccarat, gridPane);
        borderPane.setCenter(mainVbox);
        borderPane.setOpacity(1);

        window.setScene(new Scene(borderPane,screenSize.getWidth(), screenSize.getHeight()));
    }

    public void setServerInformationScene(){

        Button closeButton = new Button("Close Server");
        closeButton.setMinWidth(300);
        closeButton.setMaxWidth(300);
        closeButton.setMinHeight(60);
        closeButton.setMaxHeight(60);
        closeButton.setStyle("-fx-font: baghdad;-fx-font-size: 30;-fx-text-fill: black;-fx-background-color: darkorange;");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               serverConnection.quit();
               window.close();
            }
        });

        HBox hboxCenterTop = new HBox();
        hboxCenterTop.setMargin(closeButton, new Insets(20,0,0,500));
        hboxCenterTop.getChildren().add(closeButton);

        listView = new ListView();
        setMessagesOnServerinfo("Welcome to Baccarat!");
        listView.setStyle("-fx-control-inner-background: darkgreen;-fx-font-size: 15pt;");
        listView.setMinHeight(500);
        listView.setMaxHeight(500);
        listView.setMinWidth(800);
        listView.setMaxWidth(800);

        HBox hboxCenter = new HBox();
        hboxCenter.getChildren().addAll(listView);
        hboxCenter.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hboxCenterTop);
        borderPane.setCenter(hboxCenter);
        borderPane.setStyle("-fx-background-color: green;");
        borderPane.setOpacity(1);

        window.setScene(new Scene(borderPane,screenSize.getWidth(), screenSize.getHeight()));
        onlineClients();

    }

    public static void setMessagesOnServerinfo(String messages){

        Platform.runLater(()-> {
            listView.getItems().add(messages);
            listView.scrollTo(listView.getItems().size());
        });

    }

    public void onlineClients(){
        Thread onlineClients = new Thread(" online clients status Thread") {
            @Override
            public void run() {
                while (true) {
                    try {
                        // every 20 seconds check how many numbers of clients are online.
                        sleep(20000);
                        setMessagesOnServerinfo("Number of Clients are online = "+ serverConnection.clients.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        onlineClients.start();
    }

}
