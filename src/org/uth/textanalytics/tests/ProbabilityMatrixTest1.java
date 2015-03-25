package org.uth.textanalytics.tests;

import java.util.HashMap;
import java.util.Map;
import org.uth.textanalytics.utils.ProbabilityMatrix;

/**
 * Probability Matrix test 1.
 * @author Ian Lawson
 */
public class ProbabilityMatrixTest1
{
  public static void main( String[] args )
  {
    if( args.length != 5 )
    {
      System.err.println( "Usage: java ProbabilityMatrixTest1 'source' ngramSize cleanse(true|false) retainSpaces(true|false) ngramKeySize");
      System.exit(0);
    }
    
    new ProbabilityMatrixTest1( args[0], Integer.parseInt(args[1]), ( args[2].equalsIgnoreCase( "true" )), ( args[3].equalsIgnoreCase( "true")), Integer.parseInt( args[4]));
  }

  public ProbabilityMatrixTest1( String source, int ngramSize, boolean cleanse, boolean retainSpaces, int ngramKeySize )
  {
    ProbabilityMatrix matrix = new ProbabilityMatrix( source, ngramSize, cleanse, retainSpaces );
    
    matrix.generateProbabilities(ngramKeySize);
    
    Map<String,HashMap<String,Integer>> output = matrix.getMatrix();
    
    System.out.println( "Key Count: " + output.size());
    
    for( String key : output.keySet())
    {
      HashMap<String,Integer> probs = output.get(key);
      
      System.out.println( key + ":" );
      
      for( String prob : probs.keySet() )
      {
        System.out.println( "  " + prob + ":" + probs.get(prob));
      }
    }
  }
}
