/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.util.HashMap;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author Sonja
 */
public class Sprite extends Pane {
  final protected Tuple position;
  final protected Tuple velocity;
  protected Integer lifespan;
  final protected ImageView sprite;
  final protected ImageInfo imgInfo;
  
  public static int counter = 0;
  private int id;

  protected double angle;
  protected double angleVelocity;
  protected int age = 0;
  protected Pane parent;
  
  public int showId() {
    return id;
  }
  
  
  public Sprite(Tuple position, Tuple velocity, double angle, double angleVelocity, ImageInfo imgInfo, Integer lifespan, Pane parent) {
    Sprite.counter += 1;
    id = Sprite.counter;
    this.position = position;
    this.velocity = velocity;
    this.angle = angle;
    this.angleVelocity = angleVelocity;
    this.imgInfo = imgInfo;
    this.lifespan = lifespan;
    if(lifespan == null && imgInfo.isAnimated())
      this.lifespan = imgInfo.getFrames();
    this.sprite = new ImageView(new Image(imgInfo.getUrl()));
    this.parent = parent;
    update();
    this.getChildren().add(sprite);
  }
  
  public void reverseVelocity() {
    velocity.setX(velocity.getX()*-1);
    velocity.setY(velocity.getY()*-1);
  }

  public void setLifespan(Integer lifespan) {
    
    this.lifespan = lifespan;
  }
  
          
  public void update() {
    if(imgInfo.isAnimated())
      updateBoundingBox();
    
    updatePosition();
    
    // update age
    age += 1;
    
    draw();

  }
  
  protected void updatePosition() {
        // set angle
    angle += angleVelocity;
    
    // update position            
    position.setX((position.getX() + velocity.getX()) % (CollisionGame.WIDTH - imgInfo.getCenter().getX())); 
    position.setY((position.getY() + velocity.getY()) % (CollisionGame.HEIGHT -imgInfo.getCenter().getY())); 

    correctLocationOutOfBounds();
  }
  
  
  public HashMap getCornersMap() {
    HashMap<String, Double> hashMap = new HashMap<> ();
    hashMap.put("bottom", position.getY() + imgInfo.getSize().getY());
    hashMap.put("top", position.getY());
    hashMap.put("right", position.getX() + imgInfo.getSize().getX());
    hashMap.put("left", position.getX());
    return hashMap;
  }
  protected void correctLocationOutOfBounds() {
    // Check we're not off the screen by more than half the sprite.
    if(position.getX() < - imgInfo.getCenter().getX())
      position.setX(Asteroids.WIDTH + position.getX());
    
    if(position.getY() < - imgInfo.getCenter().getY())
      position.setY(Asteroids.HEIGHT + position.getY());  
  }
  
  private void updateBoundingBox() {
    Rectangle2D viewportRect = new Rectangle2D(imgInfo.getSize().getX() * age, 0 ,imgInfo.getSize().getX(),imgInfo.getSize().getY());
    sprite.setViewport(viewportRect);

    
  }
  public void draw() {
    sprite.setX(position.getX());
    sprite.setY(position.getY());
    sprite.setRotate(Math.toDegrees(angle));
  }
  
  public boolean isAlive() {
    if(lifespan == null)
      return true;
    return age < lifespan;
  }
  
  public double getRadius() {
    return imgInfo.getRadius();
  }
  
  public Tuple getPosition() {
    return position;
  }
  
  public ImageInfo getImgInfo() {
    return imgInfo;
  }
  
  public Tuple getCenterTuple() {
    double centerY = position.getY() + imgInfo.getCenter().getY();
    double centerX = position.getX() + imgInfo.getCenter().getX();
    return new Tuple(centerX, centerY);
  }
  
  public void removeSprite() {
    parent.getChildren().remove(this);
  }
}
