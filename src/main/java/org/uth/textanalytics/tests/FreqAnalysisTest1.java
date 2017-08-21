package org.uth.textanalytics.tests;

/**
 * Frequency Analysis (simple character) test 1.
 * @author Ian Lawson
 */
public class FreqAnalysisTest1
{
  public static void main( String[] args )
  {
    if( args.length != 1 )
    {
      System.out.println( "Usage: java FreqAnalysisTest1 exampleString");
      System.exit(0);
    }
    
    new FreqAnalysisTest1( args[0] );
  }
  
  public FreqAnalysisTest1( String token )
  {
    
  }
}
