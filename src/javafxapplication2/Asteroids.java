/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author Sonja
 */
public class Asteroids extends CollisionGame {

  final private int MAX_ROCKS = 10;
  final private int START_LIVES = 5;


  private Timeline animation;
  private Timeline rockSpawner;


  boolean playing = false;
  

  public Asteroids(Pane gamePane) {        
    super(gamePane);
    init();
  }

  private void init() {
    ImageView bg = new ImageView(new Image("file:images\\nebula_blue.f2014.png"));
    gamePane.getChildren().add(bg);

    EventHandler<ActionEvent> eventHandler = e -> {
      tick();
    };

    EventHandler<ActionEvent> rockSpawnHandler = e -> {
      rockSpawner();
    };

    animation = new Timeline(new KeyFrame(Duration.millis(50), eventHandler));
    animation.setCycleCount(Timeline.INDEFINITE);

    rockSpawner = new Timeline(new KeyFrame(Duration.millis(1000), rockSpawnHandler));
    rockSpawner.setCycleCount(Timeline.INDEFINITE);

    showSplash();
//    startGame();
  }

  private void showSplash() {

    ImageView splash = new ImageView(new Image("file:images/splash.png"));
    splash.setX(200);
    splash.setY(150);
    gamePane.getChildren().add(splash);
    gamePane.setOnMouseClicked(event -> {
      gamePane.getChildren().clear();

      ImageView bg = new ImageView(new Image("file:images\\nebula_blue.f2014.png"));
      gamePane.getChildren().add(bg);

      gamePane.setOnMouseClicked(null);
      
      gamePane.getChildren().remove(splash);  
      startGame();
    });
  }

  @Override
  protected void stopGame() {
    animation.pause();
    rockSpawner.pause();
    showSplash();
  }

  private void startGame() {
    lives = START_LIVES;
    score = 0;

    // Create ship first because rock checks not to spawn to close.
    createShip();
    createStartingRocks();
    createLabels();

    animation.play();
    rockSpawner.play();

  }

  private void createShip() {
    ImageInfo shipInfo = ImgInfoFactory.getInstance().getShipInfo();
    ship = new ShipPane(new Tuple(100, 100),
            new Tuple(0, 0),
            0, 0, shipInfo, null, gamePane);
    gamePane.getChildren().add(ship);
    setShipListeners();
  }

  private void createLabels() {
    DropShadow ds = new DropShadow();
    ds.setOffsetY(3.0f);
    ds.setColor(Color.RED);
    // create Vbox to hold info.
    VBox box = new VBox();
    // create scoreLabel
    scoreLabel = new Label("Score: " + score);
    scoreLabel.setStyle("-fx-text-fill: white; -fx-stroke: black;");
    scoreLabel.setFont(Font.font("Verdana", 18));
    scoreLabel.setEffect(ds);
    box.getChildren().add(scoreLabel);

    // create lives label:
    livesLabel = new Label("Lives: " + lives);
    livesLabel.setStyle("-fx-text-fill: white");
    livesLabel.setFont(Font.font("Verdana", 18));
    box.getChildren().add(livesLabel);

    gamePane.getChildren().add(box);
  }

  private void tick() {
    ship.update();
    
    
    // TODO update enemies.
    updateSpriteList(enemies);
    updateSpriteList(explosions);

    updateScoreAndLives();

  }
  
  private void setShipListeners() {
    gamePane.setOnKeyPressed(e -> {

      switch (e.getCode()) {
        case UP:
          ship.setThrust(true);
          break;
        case RIGHT:
          ship.rightPressed();
          break;
        case LEFT:
          ship.leftPressed();
          break;
      }
    });

    gamePane.setOnKeyReleased(e -> {
      switch (e.getCode()) {
        case UP:
          ship.setThrust(false);
          break;
        case RIGHT: // same as left
        case LEFT:
          ship.releaseKey();
          break;
        case SPACE: 
          ship.shoot();
      }
    });

  }

  private void rockSpawner() {
    // less than 10 enemies

    if (enemies.size() < MAX_ROCKS) {
      Tuple rockPos = new Tuple(Math.round(Math.random() * WIDTH), Math.round(Math.random() * HEIGHT));
      while (rockPos.distance(ship.getPosition()) < 110) {
        // Spawning to close to ship, recreate start position. 
        rockPos = new Tuple(Math.round(Math.random() * WIDTH), Math.round(Math.random() * HEIGHT));
      }

      int rockSpeed = 10 + score/2;

      // speed minus maximum speed/2 so you have a possible negative speed as well.
      double intVelX = Math.random() * rockSpeed - rockSpeed/2;
      double intVelY = Math.random() * rockSpeed - rockSpeed/2;
      
      Tuple rockVel = new Tuple(intVelX, intVelY);
      double angle = Math.random();
      double angleVel = Math.random() * 0.5;

      // create the rock and add it to the gamePane
      ImageInfo rockInfo = ImgInfoFactory.getInstance().getRockInfo();
      Sprite rock = new Sprite(rockPos, rockVel, angle, angleVel, rockInfo, null, gamePane);
      gamePane.getChildren().add(rock);
      enemies.add(rock);

    }
  }

  private void createStartingRocks() {
    for (int i = 0; i < 5; i++) {
      rockSpawner();
    }
  }

  

}
