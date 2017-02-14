/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import javafx.scene.layout.Pane;

/**
 *
 * @author Sonja
 */
public class HorizontalShipPane extends ShipPane {
  final protected double decelerationSpeed = 0.8;
  final protected double accelerationSpeed = 3;
  final protected double MAX_SPEED = 10;
  final protected double bulletSpeed = 12;
  private boolean moveRight = false;
  private boolean moveLeft = false;
  private double horizontalSpeed = 0;


  public HorizontalShipPane(Tuple position, Tuple velocity, double angle, double angleVelocity, ImageInfo imgInfo, Integer lifespan, Pane parent) {
    super(position, velocity, angle, angleVelocity, imgInfo, lifespan, parent);
  }
  
  @Override
  public void rightPressed() {
    moveRight = true;
  }
  
  @Override
  public void leftPressed() {
    moveLeft = true;
  }  
  
  @Override
  public void releaseKey() {
    moveRight = false;
    moveLeft = false;
  }

  
  @Override
  public void setThrust(boolean thrust) {
    this.thrust = thrust;
  }
  
  protected void updateShip() {
    if(moveRight) {
      velocity.setX(velocity.getX() + accelerationSpeed);

    } else if(moveLeft) {
      velocity.setX(velocity.getX() - accelerationSpeed);
    }
    
    updatePosition();

    velocity.setX(velocity.getX() * decelerationSpeed);
    velocity.setY(velocity.getY() * decelerationSpeed);    
  }
    
  
  public void shoot() {
    Tuple missilePosition = new Tuple(position.getX() + imgInfo.getCenter().getX(),
                                       position.getY());
    Tuple missileVelocity = new Tuple(0, -bulletSpeed);
    ImageInfo bulletInfo = ImgInfoFactory.getInstance().getBullet2Info();

    Sprite bullet = new Sprite(missilePosition, missileVelocity, 0, 0, bulletInfo, 45, this);
    missileGroup.add(bullet);
    getChildren().add(bullet);

  }
}
