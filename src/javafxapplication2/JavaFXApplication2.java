/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Sonja
 */
public class JavaFXApplication2 extends Application {
  
  
//  ShipPane ship = new ShipPane(new Tuple(Asteroids.width/2, Asteroids.height/2));
  //Sprite sprite;
  Pane gamePane = new Pane();
  CollisionGame game;


  @Override
  public void start(Stage primaryStage) {
//    System.out.println(javafx.scene.text.Font.getFamilies());
    Scene scene = new Scene(gamePane, 800, 600);
    primaryStage.setScene(scene);
    primaryStage.show();
    
    Pane pane = getStartPane();
    gamePane.getChildren().add(pane);

    
  }
  
  private void startInvaders() {
    gamePane.getChildren().clear();
    game = new SpaceInvaders(gamePane);
    game.setFocus();

  }
  
  private void startAsteroids() {    
    gamePane.getChildren().clear();
    game = new Asteroids(gamePane);
    game.setFocus();
  }
  
  private Pane getStartPane() {
    HBox paneForButtons = new HBox(20);
    Button btnAsteroids = new Button("Asteroids");
    Button btnInvaders = new Button("Invaders");
    paneForButtons.getChildren().addAll(btnAsteroids, btnInvaders);
    paneForButtons.setAlignment(Pos.CENTER);
    paneForButtons.setStyle("-fx-border-color: green");
    
    BorderPane pane = new BorderPane();
    pane.setPrefSize(800, 600);
    pane.setBottom(paneForButtons);
    Pane paneForText = new Pane();
    Text text = new Text(50, 50, "Welk spel wil je spelen?");
    paneForText.getChildren().add(text);
    pane.setCenter(paneForText);
    
    btnAsteroids.setOnAction(e -> startAsteroids());
    btnInvaders.setOnAction(e -> startInvaders());
    return pane;
  }
  
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

}
