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
public class ImageInfo {
  final private Tuple center;
  final private Tuple size;
  final private double radius;
  final private boolean animated;
  final private String url;
  final private int frames;

  public ImageInfo(Tuple center, Tuple size, double radius, boolean animated, String url, int frames) {
    this.center = center;
    this.size = size;
    this.radius = radius;
    this.animated = animated;
    this.url = url;
    this.frames = frames;
  }
    
  
  public String getUrl() {
    return url;
  }

  public Tuple getCenter() {
    return center;
  }
  
  public double getRadius() {
    return radius;
  }

  public Tuple getSize() {
    return size;
  }

  public boolean isAnimated() {
    return animated;
  }

  public int getFrames() {
    return frames;
  }
}
