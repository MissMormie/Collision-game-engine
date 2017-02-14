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
public class Util {
  public static Tuple angleToVector(double angle) {
    return new Tuple(Math.cos(angle), Math.sin(angle));
  }
}
