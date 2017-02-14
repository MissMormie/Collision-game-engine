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
public class Tuple {
  private double x;
  private double y;
  
  public Tuple(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }
  
  @Override
  public String toString() {
    return "x: " + x + " y: " + y;
  }
  
  public double distance(Tuple compareTo) {
    // a^2 + b^2 = c^2 
    return Math.sqrt( Math.pow(x-compareTo.getX(),2) + Math.pow(y - compareTo.getY(),2));
  }
}
