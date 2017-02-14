/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Sonja
 */
public class SpaceInvaders extends CollisionGame {
  private Timeline enemyAction;
  private Timeline tick;
  private int counter;
  private int level;
  private Label levelLabel;
  private ImageView bg;
  protected List<Sprite> obstacles = new ArrayList();

  public SpaceInvaders(Pane gamePane) {
    super(gamePane);
    init();
    
  }
  
  private void init(){
    bg = new ImageView(new Image("file:images\\space.jpg"));
    gamePane.getChildren().add(bg);

    EventHandler<ActionEvent> eventHandler = e -> {
      tick();
    };

    EventHandler<ActionEvent> rockSpawnHandler = e -> {
      moveEnemies();
    };
    
    tick = new Timeline(new KeyFrame(Duration.millis(50), eventHandler));
    tick.setCycleCount(Timeline.INDEFINITE);

    enemyAction = new Timeline(new KeyFrame(Duration.millis(3500), rockSpawnHandler));
    enemyAction.setCycleCount(Timeline.INDEFINITE);    
    showSplash();
  }
  private Pane getSplashPane() {
    StackPane pane = new StackPane();
    pane.setPrefSize(WIDTH, HEIGHT);


    Text splash = new Text("Space Invaders\n click to play");
//    splash.setStyle("-fx-text-fill: #FFFFFF; -fx-stroke: black; -fx-stroke-width:1px;");
    splash.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 55));
    splash.setFill(Color.YELLOWGREEN);
    
    DropShadow ds = new DropShadow();
    ds.setOffsetY(5.0f);
    ds.setColor(Color.GREEN);
    splash.setEffect(ds);
    
    
    pane.getChildren().add(splash);
    pane.setAlignment(splash, Pos.CENTER);
    return pane;
  }
  private void showSplash() {

    Pane pane = getSplashPane();

    
    gamePane.getChildren().add(pane);
    gamePane.setOnMouseClicked(event -> {
      gamePane.getChildren().clear();
      gamePane.getChildren().add(bg);

      gamePane.setOnMouseClicked(null);
      
      gamePane.getChildren().remove(pane);  
      startGame();
    });
  }  
  
  private void startGame() {
    lives = 1;
    score = 0;
    counter = 0;
    level = 1;
    createInvaders();
    createShip();
    createObstacles();
    createLabels();
    tick.playFromStart();
    enemyAction.playFromStart();
    
  }
  
  private void tick() {
    updateSpriteList(enemies);
    updateSpriteList(explosions);
    ship.update();
    stopBullets();
    updateScoreAndLives();
    
    if(enemies.size() == 0) {
      System.out.println("null");
      level++;
      levelLabel.setText("Level: " + level);
      resetEnemies();
    }
  }
  
  @Override
  protected void stopGame() {
    tick.pause();
    enemyAction.pause();
    showSplash();
  }
  
  private void resetEnemies() {
    enemyAction.playFromStart();
    createInvaders();
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
    levelLabel = new Label("Level: " + level);
    levelLabel.setStyle("-fx-text-fill: white");
    levelLabel.setFont(Font.font("Verdana", 18));
    box.getChildren().add(levelLabel);
    
    
    gamePane.getChildren().add(box);
  }

  
  private void stopBullets() {
    groupGroupCollide(obstacles, ship.missileGroup, false);
  }
  
  private void createShip() {
    ImageInfo shipInfo = ImgInfoFactory.getInstance().getShipInfo();
    ship = new HorizontalShipPane(new Tuple(400,550), new Tuple(0,0), Math.toRadians(270), 0, shipInfo, null, gamePane);
    gamePane.getChildren().add(ship);
    setShipListeners();
  }

  private void setShipListeners() {
    gamePane.setOnKeyPressed(e -> {

      switch (e.getCode()) {
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
        case RIGHT: // same as left
        case LEFT:
          ship.releaseKey();
          break;
        case SPACE: 
          ship.shoot();
      }
    });
  }
  
  private void createInvaders() {
    // 4 rows: starting from 1 because of image names.
    for(int i = 0; i < level +1; i++) {
      // invaders per row
      for(int j = 0; j < 9; j++) {
        Tuple pos = new Tuple(j*75 + 10, i*70 + 20);
        Tuple vel = new Tuple(2,0);
        ImageInfo invaderInfo = ImgInfoFactory.getInstance().getInvader(i);

        Sprite invader = new Sprite(pos, vel, 0, 0, invaderInfo, null, gamePane);
        gamePane.getChildren().add(invader);
        enemies.add(invader);
      }
    }
  }
  
  private void createObstacles() {
    for(int i =0; i < 5; i++) {
      buildObstacle(i *160+50.0, 500.0);
    }
  }
  
  private void buildObstacle(double offsetX, double offsetY) {      
    // top row
    for(int i = 0; i<3; i++) {
      addObstacle(offsetX+i*8 + 16, offsetY);
    }
    
    // second row   
    for(int i = 0; i < 5; i++) {
      addObstacle(offsetX + i*8 + 8, offsetY + 8);
    }
    
    // third row
    for(int i = 0; i < 5; i++) {
      addObstacle(offsetX + i*8 + 8, offsetY + 16);
    }
    
    // fourth row - leg 1
    for(int i = 0; i<3; i++) {
      addObstacle(offsetX+i*8, offsetY + 24);
    }
    
    // fourth row - leg 2
    for(int i = 0; i<3; i++) {
      addObstacle(offsetX+i*8 + 32, offsetY + 24);
    }

  }
  
  @Override
  protected ImageInfo getExplosionInfo() {
    return ImgInfoFactory.getInstance().getExplosion2Info();
  }
  
  private void addObstacle(double offsetX, double offsetY) {
    Tuple pos = new Tuple(offsetX, offsetY);
    ImageInfo obstacleInfo = ImgInfoFactory.getInstance().getObstacleSquareInfo();

    Sprite obstacle = new Sprite(pos, new Tuple(0,0), 0, 0, obstacleInfo, null, gamePane);
    gamePane.getChildren().add(obstacle);
    obstacles.add(obstacle);      

  }
  
  private void moveEnemies() {
    counter ++;
    if(counter % 2 == 0) {
      for(Sprite enemy: enemies) {
        enemy.reverseVelocity();
      }
    } else {
      for(Sprite enemy: enemies) {
        enemy.position.setY(enemy.position.getY() + 30 );
        enemy.reverseVelocity();
      }
    }
  }
  
  // WHY OH WHY?
  // won't show anything but child variables if i place this function in parent,
  // but only for space invaders, asteroids works fine.
  @Override
  protected void updateScoreAndLives() {
    // Collide missiles with enemies see if anything gets killed.
    score += groupGroupCollide(enemies, ship.missileGroup, true) * level;

    if (groupCollide(enemies, ship)) {
      lives -= 1;
      if (lives <= 0) {
        stopGame();
      }
    }
    scoreLabel.setText("Score: " + score * 100);
    int index = enemies.size() -1;
    if(index > -1 && enemies.get(index).position.getY() > 450)
      stopGame();
//    livesLabel.setText("Lives: " + lives);
  }
  
  // Checks for every dimension if the opposite of the second items falls outside it. 
  // If top of 1 if lower than bottom of 2 it doesn't collide
  @Override
  protected boolean collides(Sprite item1, Sprite item2) {
    HashMap<String, Double> sides1 = item1.getCornersMap();
    HashMap<String, Double> sides2 = item2.getCornersMap();
    
    // Bottom of 1 above the top of 2
    if(sides1.get("bottom") < sides2.get("top")) 
      return false;
    
    // Top of 1 
    if(sides1.get("top") > sides2.get("bottom"))
      return false;
    
    if(sides1.get("right") < sides2.get("left"))
      return false;
    
    if(sides1.get("left") > sides2.get("right"))
      return false;
    
    return true;
  }
  
  

}
