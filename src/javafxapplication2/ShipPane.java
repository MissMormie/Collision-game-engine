/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;

/**
 *
 * @author Sonja
 */
public class ShipPane extends Sprite {
//  final private Tuple position;
//  final private Tuple velocity = new Tuple(0,0);
  protected boolean thrust = false;
  final protected double bulletSpeed = 12;

  final protected double accelerationSpeed = 1.5;
  final protected double decelerationSpeed = 0.97;
  final protected double angleVelocitySpeed = 0.15;
  
  
  public List<Sprite> missileGroup = new ArrayList(); 

  public ShipPane(Tuple position, Tuple velocity, double angle, double angleVelocity, ImageInfo imgInfo, Integer lifespan, Pane parent) {
    super(position, velocity, angle, angleVelocity, imgInfo, lifespan, parent);
    Rectangle2D viewportRect = new Rectangle2D(0,0,imgInfo.getSize().getX(),imgInfo.getSize().getY());
    sprite.setViewport(viewportRect);

  }

  
  
  @Override
  public void update() {
    
    updateShip();
    updateShots();
    
    drawShip();
  }
  
  private void updateShots() {
    if( missileGroup == null)
      return;
    
    Iterator<Sprite> missiles = missileGroup.iterator();
    while(missiles.hasNext()) {
      Sprite shot = missiles.next();
      shot.update();
      if(!shot.isAlive()) {
        getChildren().remove(shot);
        missiles.remove();
      }
    }
  }
  
  protected void updateShip() {
    updatePosition();
    // update velocity
    if(thrust) {
      Tuple acceleration = Util.angleToVector(angle);
      velocity.setX(velocity.getX() + acceleration.getX() * accelerationSpeed);
      velocity.setY(velocity.getY() + acceleration.getY() * accelerationSpeed);
    }
    
    velocity.setX(velocity.getX() * decelerationSpeed);
    velocity.setY(velocity.getY() * decelerationSpeed);    
  }
  
  public void setThrust(boolean thrust) {
    this.thrust = thrust;
    Rectangle2D viewportRect;
    if(thrust) {
      viewportRect = new Rectangle2D(90,0,imgInfo.getSize().getX(),imgInfo.getSize().getY());

      // space for sounds and other effects
    } else {
      viewportRect = new Rectangle2D(0,0,imgInfo.getSize().getX(),imgInfo.getSize().getY());
    }
    sprite.setViewport(viewportRect);
  }
  
  public void rightPressed() {
    angleVelocity = angleVelocitySpeed;
  }
  
  public void leftPressed() {
    angleVelocity = -angleVelocitySpeed;
  }
  
  public void releaseKey() {
    angleVelocity = 0;
  }
  
  
  public void shoot() {
    Tuple forward = Util.angleToVector(angle);
    
    
    Tuple missilePosition = new Tuple(position.getX() + imgInfo.getCenter().getX() + imgInfo.getRadius() * forward.getX(),
                                       position.getY()+ imgInfo.getCenter().getY() + imgInfo.getRadius() * forward.getY());
    Tuple missileVelocity = new Tuple(velocity.getX() + bulletSpeed * forward.getX(),
                                      velocity.getY() + bulletSpeed * forward.getY());

    ImageInfo bulletInfo = ImgInfoFactory.getInstance().getBullet1Info();
    Sprite bullet = new Sprite(missilePosition, missileVelocity, angle, 0, bulletInfo, 35, this);
    missileGroup.add(bullet);
    getChildren().add(bullet);
  }
  
  public void drawShip() {
    if(sprite == null)
      return;
    
    sprite.setX(position.getX());
    sprite.setY(position.getY());
    sprite.setRotate(Math.toDegrees(angle));
  }

    
}
