package com.javalego.util;

import java.util.Date;

/**
 * Clase que implementa la comparación entre objetos y tipos primarios Java.
 */
public final class EqualsUtil {

  static public boolean areEqual(boolean aThis, boolean aThat){
    return aThis == aThat;
  }

  static public boolean areEqual(char aThis, char aThat){
    return aThis == aThat;
  }

  static public boolean areEqual(long aThis, long aThat){
    /*
    * Implementation Note
    * Note that byte, short, and int are handled by this method, through
    * implicit conversion.
    */
    //System.out.println("long");
    return aThis == aThat;
  }

  static public boolean areEqual(float aThis, float aThat){
    return Float.floatToIntBits(aThis) == Float.floatToIntBits(aThat);
  }

  static public boolean areEqual(double aThis, double aThat){
    return Double.doubleToLongBits(aThis) == Double.doubleToLongBits(aThat);
  }

  static public boolean areEqual(String aThis, String aThat){
    return aThis == null ? aThat == null : aThis.equals(aThat);
  }

  static public boolean areEqual(Date aThis, Date aThat){
    return aThis == null ? aThat == null : aThis.equals(aThat);
  }

  /**
   * Comparar el resto de objetos por el valor del método toString() que implementan
   * ya que de los contario siempre devolveremos false.
   * @param aThis
   * @param aThat
   * @return
   */
  static public boolean areEqual(Object aThis, Object aThat){
    return aThis == null ? aThat == null : aThis.toString().equals(aThat.toString());
  }
}
