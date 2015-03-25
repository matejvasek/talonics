package org.uth.textanalytics.tests;

import java.util.ArrayList;
import java.util.List;
import org.uth.textanalytics.utils.TextUtilities;

/**
 * Simple Bigram generator test.
 * @author Ian Lawson
 */
public class BigramTest1
{
  public static void main( String[] args )
  {
    if( args.length != 2 )
    {
      System.out.println( "Usage: java BigramTest1 'terms' retainSpaces(true|false)");
      System.exit(0);
    }
    
    new BigramTest1( args[0], args[1].equalsIgnoreCase("true") );
  }
  
  public BigramTest1( String tokens, boolean retainSpaces )
  {
    String processedText = TextUtilities.alphaNumeric(tokens, retainSpaces);
    
    System.out.println( "Processed text: " + processedText);
    
    // Generate non-normalised ordered bigram list
    List<String> nnBigrams = new ArrayList<>();
    
    for( int loop = 0; loop < ( processedText.length() - 1 ); loop++ )
    {
      nnBigrams.add( processedText.substring(loop, loop + 2));
    }
    
    System.out.println( "Non-normalised ordered bigrams:");
    
    for( String bigram : nnBigrams )
    {
      System.out.print( "'" + bigram + "' " );
    }
    
    System.out.println();    
  }
}
