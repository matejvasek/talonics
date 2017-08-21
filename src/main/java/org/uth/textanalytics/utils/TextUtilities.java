package org.uth.textanalytics.utils;

/**
 * Utilities for text manipulation.
 * @author Ian Lawson
 */
public class TextUtilities
{
  private TextUtilities()
  {
    
  }
  
  public static String alphaNumeric( String input, boolean retainSpaces )
  {
    input = input.toLowerCase();
    
    StringBuilder output = new StringBuilder();
    
    for( char test : input.toCharArray())
    {
      if( Character.isAlphabetic(test) || Character.isDigit(test) || ( test == ' ' && retainSpaces ))
      {
        output.append(test);
      }
    }
    
    return output.toString();
  }
}
