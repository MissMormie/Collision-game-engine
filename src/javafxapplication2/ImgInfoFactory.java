/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

/**
 *
 * @author Sonja
 */
public class ImgInfoFactory {

  private static ImgInfoFactory instance = null;
  private ImageInfo shipInfo;
  private ImageInfo rockInfo;
  private ImageInfo explosionInfo;
  private ImageInfo invader1Info;
  private ImageInfo invader2Info;
  private ImageInfo invader3Info;
  private ImageInfo invader4Info;
  private ImageInfo obstacleSquareInfo;
  private ImageInfo bullet1Info;
  private ImageInfo bullet2Info;


  protected ImgInfoFactory() {

  }

  public static ImgInfoFactory getInstance() {
    if (instance == null) {
      instance = new ImgInfoFactory();
    }
    return instance;
  }

  public ImageInfo getShipInfo() {
    if (shipInfo == null) {
      shipInfo = new ImageInfo(new Tuple(40, 40), new Tuple(80, 80), 35, true, "file:images\\double_ship.png", 1);
    }
    return shipInfo;
  }

  public ImageInfo getRockInfo() {
    if (rockInfo == null) {
      rockInfo = new ImageInfo(new Tuple(45, 45), new Tuple(90, 90), 40, false, "file:images/asteroid_blue.png", 1);
    }
    return rockInfo;
  }

  public ImageInfo getExplosionInfo() {
    if (explosionInfo == null) {
      explosionInfo = new ImageInfo(new Tuple(64, 64), new Tuple(128, 128), 40, true, "file:images//explosion_alpha.png", 24);
    }
    return explosionInfo;
  }
  
  public ImageInfo getExplosion2Info() {
    if (explosionInfo == null) {
      explosionInfo = new ImageInfo(new Tuple(23, 25), new Tuple(46, 49), 40, true, "file:images//cartoon_explosion.png", 5);
    }
    return explosionInfo;
  }
  

  public ImageInfo getInvader(int num) {
    num = num % 4;
    switch (num) {
      case 0:
        return getSpace1Info();
      case 1:
        return getSpace2Info();
      case 2:
        return getSpace3Info();
      case 3:
        return getSpace4Info();
      default:
        return getSpace1Info();
    }
  }

  public ImageInfo getSpace1Info() {
    if (invader1Info == null) {
      invader1Info = new ImageInfo(new Tuple(24, 24), new Tuple(48, 48), 24, false, "file:images//spaceinvader1b.png", 1);
    }
    return invader1Info;
  }

  public ImageInfo getSpace2Info() {
    if (invader2Info == null) {
      invader2Info = new ImageInfo(new Tuple(33, 24), new Tuple(66, 48), 30, false, "file:images//spaceinvader2b.png", 1);
    }
    return invader2Info;
  }

  public ImageInfo getSpace3Info() {
    if(invader3Info == null) {
      invader3Info = new ImageInfo(new Tuple(33, 24), new Tuple(66, 48), 30, false, "file:images//spaceinvader3b.png", 1);
    }
    return invader3Info;
  }

  public ImageInfo getSpace4Info() {
    if(invader4Info == null) {
      invader4Info = new ImageInfo(new Tuple(24, 24), new Tuple(48, 48), 24, false, "file:images//spaceinvader4b.png", 1);
    }
    return invader4Info;
  }

  /*
  public ImageInfo getObstacleInfo() {
    return new ImageInfo(new Tuple(64, 64), new Tuple(60,60), 30, false, "file:images//obstacle.png");
  }
   */
  public ImageInfo getObstacleSquareInfo() {
    if( obstacleSquareInfo == null )
      obstacleSquareInfo = new ImageInfo(new Tuple(4, 4), new Tuple(8, 8), 4, false, "file:images//obstacle1.png", 1);
    return obstacleSquareInfo;
  }
  
  public ImageInfo getBullet1Info() {
    if(bullet1Info == null)
      bullet1Info = new ImageInfo(new Tuple(5,5), new Tuple(10,10), 3, false, "file:images/shot1.png", 1);
    return bullet1Info;
  }
  
  public ImageInfo getBullet2Info() {
    if(bullet1Info == null)
      bullet1Info = new ImageInfo(new Tuple(5,5), new Tuple(10,10), 3, false, "file:images/shot2.png", 1);
    return bullet1Info;
  }
}
