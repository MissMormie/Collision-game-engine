/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 *
 * @author Sonja
 */
public class CollisionGame {
  final public static int WIDTH = 800;
  final public static int HEIGHT = 600;

  final protected Pane gamePane;
  protected List<Sprite> enemies = new ArrayList();
  protected List<Sprite> explosions = new ArrayList();
  
  protected ShipPane ship;
  protected int lives;
  protected Label livesLabel;
  protected int score;
  protected Label scoreLabel;


  
  public CollisionGame(Pane gamePane) {
    this.gamePane = gamePane;
  }
    // call update function for every sprite in the list, 
  // check if it's still alive after updating
  // if not, remove from list and parent pane. 
  protected void updateSpriteList(List<Sprite> sprites) {
    if (sprites != null) {
      ListIterator<Sprite> spriteList = sprites.listIterator();
      while( spriteList.hasNext()) {
        // get next index and move to next item in the list
        int index = spriteList.nextIndex();
        spriteList.next();
        sprites.get(index).update();
        if(!sprites.get(index).isAlive()) {
          sprites.get(index).removeSprite();
          spriteList.remove();
        }
      }
    }
  }
  
  // remove items from group if they collide.
  protected boolean groupCollide(List<Sprite> group, Sprite sprite) {
    int hits = 0;
    ListIterator<Sprite> groupItem = group.listIterator();
    while (groupItem.hasNext()) {
      int index = groupItem.nextIndex();
      groupItem.next();
      if (collides(group.get(index), sprite)&& group.get(index).isAlive()) {
        group.get(index).setLifespan(0);
        // it they collide, remove the item from the group and the screen.
        //gamePane.getChildren().remove(group.get(index));
        //groupItem.remove();
        return true;
      }
    }
    return false;
  }

  protected boolean collides(Sprite item1, Sprite item2) {
    double distanceFromCenters = item1.getCenterTuple().distance(item2.getCenterTuple());
    double totalSizeOfObjects = item1.getImgInfo().getRadius() + item2.getImgInfo().getRadius();
    if (distanceFromCenters <= totalSizeOfObjects) {
//      System.out.println("collision id's:" + item1.showId() + " " + item2.showId());
            
      return true;
    }
    return false;
  }

  // remove items from both groups if they collide.
  protected int groupGroupCollide(List<Sprite> group1, List<Sprite> group2, boolean explosions) {
    int hits = 0;
    ListIterator<Sprite> groupItem = group1.listIterator();
    while (groupItem.hasNext()) {
      int index = groupItem.nextIndex();
      groupItem.next();
      if (groupCollide(group2, group1.get(index))) {
        if(explosions) {
          showExplosion(group1.get(index).getCenterTuple());
        }
        // remove from pane
        group1.get(index).removeSprite();
        // remove from list
        groupItem.remove();
        hits++;
      }
    }

    return hits;
  }
  
  protected void showExplosion(Tuple position) {
    ImageInfo explosionInfo = getExplosionInfo();
    position.setX(position.getX() - explosionInfo.getCenter().getX());
    position.setY(position.getY() - explosionInfo.getCenter().getY());

    Sprite rock = new Sprite(position, new Tuple(0,0), 0, 0, explosionInfo, null, gamePane);
    gamePane.getChildren().add(rock);
    explosions.add(rock);
  }
  
  protected ImageInfo getExplosionInfo() {
    return ImgInfoFactory.getInstance().getExplosionInfo();
  }
  

  void setFocus() {
    gamePane.requestFocus();
  }
  
  protected void stopGame() {
    
  };
  
  protected void updateScoreAndLives() {
    // Collide missiles with enemies see if anything gets killed.
    score += groupGroupCollide(ship.missileGroup, enemies, true);

    if (groupCollide(enemies, ship)) {
      lives -= 1;
      if (lives <= 0) {
        stopGame();
      }
    }

    scoreLabel.setText("Score: " + score * 1000);
    livesLabel.setText("Lives: " + lives);
  }

}
