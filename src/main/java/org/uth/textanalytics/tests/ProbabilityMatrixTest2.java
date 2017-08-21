package org.uth.textanalytics.tests;

import org.uth.textanalytics.utils.FileUtils;
import org.uth.textanalytics.utils.ProbabilityMatrix;

/**
 * Test of generating probability matrix from file input.
 * @author Ian Lawson
 */
public class ProbabilityMatrixTest2
{
  public static void main( String[] args )
  {
    if( args.length != 6 )
    {
      System.out.println( "Usage: java ProbabilityMatrixTest2 sourceFile keySize ngramSize cleanse(true|false) retainSpaces(true|false) generateOutput(true|false)");
      System.exit(0);
    }
    
    new ProbabilityMatrixTest2( args[0], Integer.parseInt( args[1]), Integer.parseInt( args[2]), args[3].equalsIgnoreCase("true"), args[4].equalsIgnoreCase("true"), args[5].equalsIgnoreCase("true"));    
  }
  
  public ProbabilityMatrixTest2( String filename, int keySize, int ngramSize, boolean cleanse, boolean retainSpaces, boolean generateOutput )
  {
    String source = FileUtils.loadString(filename);
    
    long start = System.currentTimeMillis();
    
    ProbabilityMatrix probability = new ProbabilityMatrix( source, ngramSize, cleanse, retainSpaces );
    probability.generateProbabilities(keySize);
    
    long end = System.currentTimeMillis();

    if( generateOutput )
    {
      for( String key : probability.getMatrix().keySet())
      {
        for( String token : probability.getMatrix().get(key).keySet())
        {
          int count = probability.getMatrix().get(key).get(token);
          System.out.println( key + "," + token + "," + count );
        }
      }
    }
    else
    {
      System.out.println( "Generated matrix from " + filename + " with ngram size " + ngramSize + " and key size " + keySize + " in " + (end - start) + "ms.");
      System.out.println( "Generated matrix of size " + probability.getMatrix().size());
    }
  }
}
