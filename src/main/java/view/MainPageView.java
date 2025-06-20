package view;

import control.MainPageController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This view is the main page
 *
 * @author 
 */
public class MainPageView extends Stage {
    /**
     * Button to choose recipe
     */
    public Button chooseRecipe;
    /**
     * Button to add recipe
     */
    public Button addRecipe;
    private ImageView imageView;

    /**
     * Instantiates a new main page
     */
    public MainPageView(){
        this.setTitle("Digital Recipe");
        this.setResizable(false);
        this.setWidth(800);
        this.setHeight(600);
        init();
    }

    private void init() {
        AnchorPane background = new AnchorPane();
        background.setPrefSize(800, 600);
        setupImageBackground();

        Label welcome = setupWelcomeLabel();
        setLeftButton();
        setRightButton();
        background.getChildren().addAll(
                imageView,
                addRecipe,
                chooseRecipe,
                welcome
        );

        Scene scene = new Scene(background);
        this.setScene(scene);

    }
    private void setRightButton() {
        addRecipe = new Button();
        File imageFile = new File("src/images/UIDesign/right.PNG");
        if(imageFile.exists()){
            try {
                Image imageRight = new Image(new FileInputStream(imageFile));
                ImageView imageViewRight = new ImageView(imageRight);
                imageViewRight.setFitHeight(300);
                imageViewRight.setFitWidth(250);
                addRecipe.setOnAction(new MainPageController(this));
                addRecipe.setLayoutX(445);
                addRecipe.setLayoutY(150);
                addRecipe.setGraphic(imageViewRight);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }else{
            System.out.println("Image file not found");
        }
    }
    private void setLeftButton() {
        chooseRecipe = new Button();
        File imageFile = new File("src/images/UIDesign/left.PNG");
        if(imageFile.exists()){
            try {
                Image imageLeft = new Image(new FileInputStream(imageFile));
                ImageView imageViewLeft = new ImageView(imageLeft);
                imageViewLeft.setFitHeight(300);
                imageViewLeft.setFitWidth(250);
                chooseRecipe.setOnAction(new MainPageController(this));
                chooseRecipe.setLayoutX(75);
                chooseRecipe.setLayoutY(150);
                chooseRecipe.setGraphic(imageViewLeft);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else {
            System.out.println("File not found");
        }
    }
    private void setupImageBackground() {
        imageView = new ImageView(new Image("file:src/images/background/bg.png"));
        imageView.setFitWidth(800);
        imageView.setFitHeight(600);
        imageView.setPreserveRatio(false);
        imageView.setPickOnBounds(true);
    }
    private Label setupWelcomeLabel(){
        Label welcomeLabel = new Label("Welcome to Cookbook :D");
        welcomeLabel.setLayoutX(100);
        welcomeLabel.setLayoutY(50);
        Font welcomeFont = Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.ITALIC, 50);
        welcomeLabel.setFont(welcomeFont);
        return welcomeLabel;
    }
}
