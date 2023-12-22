package com.roboticssimjava;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MainViewController {

    public Button menuButton, robotButton, playButton, smartDashBoard;
    public ImageView AreaImage;
    public Button settingsButton;
    public MenuButton AdminButton;
    public AnchorPane dynamicButtonsPane;
    public ScrollPane ScrollPaneSmart;
    public ImageView RobotImage;
    private boolean buttonSmartClicked = false;
    private int initY = 0;
    private int numGroup = 0;
    private boolean buttonStartClicked = false;
    private boolean buttonStopClicked = false;
    private boolean buttonResetClicked = false;
    private final Font font = new Font("Arial", 16);
   private static List<Group> views = new ArrayList<>();
    private static List<Double> variablesList = new ArrayList<>();

    static
    {
        initTimelineForSmartBoard();
    }
    @FXML
    private void initialize() {

        if(MenuController.holder != null)
        {
            AreaImage.setImage(MenuController.holder.getImage().getImage());
        }
        if(RobotChoice.holderRobot != null)
        {
            RobotImage.setImage(RobotChoice.holderRobot.getImage().getImage());
        }
        ScrollPaneSmart.setVisible(false);
        ScrollPaneSmart.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        ScrollPaneSmart.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        views.clear();
        numGroup = 0;
        initY = 0;
    }

    private void hideScrollPane()
    {
        ScrollPaneSmart.setVisible(buttonSmartClicked);
    }

    public void changeSettings()
    {
        try {
            FXMLLoader loader;
            if(Main.positionRight.equals("Admin")){
                loader = new FXMLLoader(getClass().getResource("SettingsAdminPage.fxml"));
            }else{
                loader = new FXMLLoader(getClass().getResource("SettingsPage.fxml"));
            }

            Parent root = loader.load();
            settingsButton.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void changeMenuPage() {
        try {
            FXMLLoader loader;
            if(Main.positionRight.equals("Admin")){
                loader = new FXMLLoader(getClass().getResource("menuAdminPage.fxml"));
            }else{
                loader = new FXMLLoader(getClass().getResource("menuPage.fxml"));
            }
            Parent root = loader.load();
            menuButton.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeRobotPage() {
        try {
            FXMLLoader loader;
            if(Main.positionRight.equals("Admin")){
                loader = new FXMLLoader(getClass().getResource("robotAdminChoice.fxml"));
            }else{
                loader = new FXMLLoader(getClass().getResource("robotChoice.fxml"));
            }
            Parent root = loader.load();
            robotButton.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeAdminPageArea() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminPageArea.fxml"));
            Parent root = loader.load();
            AdminButton.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeAdminPageRobot() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminPageRobot.fxml"));
            Parent root = loader.load();
            AdminButton.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeAdminPageRight() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminPageRight.fxml"));
            Parent root = loader.load();
            AdminButton.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initTimelineForSmartBoard(){
        Timeline updateTimeValue = new Timeline(new KeyFrame(Duration.millis(300), e -> updateValue()));
        updateTimeValue.setCycleCount(Animation.INDEFINITE);
        updateTimeValue.play();
    }

    @FXML
    private void actionSmartDashBoard() {
        try {
            buttonSmartClicked = !buttonSmartClicked;
            hideScrollPane();

            if(buttonSmartClicked)
            {
                createTextView("posX", (buttonStartClicked)?1:0);
                createTextView("posY",(buttonStopClicked)?1:0);

                addViewsOnPanel();
            }else
            {
                views.clear();
                numGroup = 0;
                initY = 0;
            }
        } catch (Exception ignored) {

        }
    }

    private void createTextView(String nameLabel, double value){
        Group group = new Group();

        Rectangle rectangle = new Rectangle(120,100);
        rectangle.setArcHeight(5);
        rectangle.setArcWidth(5);
        rectangle.setFill(Color.rgb(182, 163, 155));
        rectangle.setStroke(Color.BLACK);

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setLayoutY(33);
        textArea.setPrefSize(120, 67);
        textArea.setStyle("-fx-border-color: #808080;");
        textArea.setFont(font);
        textArea.setFont(Font.font(font.getFamily(), FontWeight.BOLD, font.getSize()));
        textArea.setMouseTransparent(true);
        textArea.setFocusTraversable(false);
        textArea.setText(String.valueOf(value));

        Label label = new Label(nameLabel);
        label.setAlignment(Pos.CENTER);
        label.setPrefSize(120, 20);
        label.setFont(font);
        label.setFont(Font.font(font.getFamily(), FontWeight.BOLD, font.getSize()));
        group.getChildren().addAll(rectangle, textArea, label);
        setLayoutForGroupOnSmartBoard(group);
    }

    private void setLayoutForGroupOnSmartBoard(Group group){
        double[] coordinateY = {0, 120, 240, 360, 480, 600, 720, 840, 960, 1080};
        double x = 15;
        double y = 10;
        numGroup++;
        if(numGroup>2 && (numGroup-1)%2==0){
            y +=coordinateY[initY];
        } else if(numGroup%2 == 0){
            x += 143;
            y +=coordinateY[initY];
            initY++;
        }
        group.setLayoutX(x);
        group.setLayoutY(y);
        views.add(group);
    }

    private void addViewsOnPanel(){
        for (Group view : views) {
            dynamicButtonsPane.getChildren().addAll(view);
        }
    }

    private static void updateValue(){
        variablesList.add((double) 0);
        variablesList.add((double) 0);

        for (int i = 0; i < views.size(); i++) {
            for (Node node : views.get(i).getChildren()) {
                if (node instanceof TextArea textArea) {
                    textArea.setText(String.valueOf(variablesList.get(i)));
                }
            }
        }
        variablesList.clear();
    }



    public boolean actionStartButton()
    {
        buttonStartClicked = !buttonStartClicked;
        return buttonStartClicked;
    }


    public boolean actionStopButton()
    {
        buttonStopClicked = !buttonStopClicked;
        return buttonStopClicked;
    }

    public boolean actionResetButton()
    {
        buttonResetClicked = !buttonResetClicked;
        return buttonResetClicked;
    }
}
