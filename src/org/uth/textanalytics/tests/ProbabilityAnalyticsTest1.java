package org.uth.textanalytics.tests;

import org.uth.textanalytics.utils.FileUtils;
import org.uth.textanalytics.utils.ProbabilityAnalyticsUtils;
import org.uth.textanalytics.utils.ProbabilityMatrix;

/**
 * Probability Analytics Test 1.
 * @author Ian Lawson
 */
public class ProbabilityAnalyticsTest1
{
  public static void main( String[] args )
  {
    if( args.length != 7 )
    {
      System.err.println( "Usage: java ProbabilityTest1 file1 file2 cleanse(true|false) retainSpaces(true|false) threshold keySize ngramSize");
      System.exit(0);
    }
    
    new ProbabilityAnalyticsTest1( args[0], args[1], args[2].equalsIgnoreCase("true"), args[3].equalsIgnoreCase("true"), Float.parseFloat(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
  }

  public ProbabilityAnalyticsTest1( String filename1, 
                                    String filename2,
                                    boolean cleanse,
                                    boolean retainSpaces,
                                    float threshold,
                                    int keySize,
                                    int ngramSize )
  {
    // Create the matrices
    System.out.println( "Loading Matrix 1...");
    long start = System.currentTimeMillis();
    String src = FileUtils.loadString(filename1);
    ProbabilityMatrix srcMatrix = new ProbabilityMatrix( src, ngramSize, cleanse, retainSpaces );
    srcMatrix.generateProbabilities(keySize);
    long end = System.currentTimeMillis();
    System.out.println( " Completed in " + ( end - start ) + "ms.");

    System.out.println( "Loading Matrix 2...");
    start = System.currentTimeMillis();
    String trg = FileUtils.loadString(filename2);
    ProbabilityMatrix trgMatrix = new ProbabilityMatrix( trg, ngramSize, cleanse, retainSpaces );
    trgMatrix.generateProbabilities(keySize);
    end = System.currentTimeMillis();
    System.out.println( " Completed in " + ( end - start ) + "ms.");
    
    ProbabilityAnalyticsUtils analytics = new ProbabilityAnalyticsUtils(srcMatrix, trgMatrix );

    System.out.println( "Calculating Probability Similarity Index...");
    start = System.currentTimeMillis();
    float simIndex = analytics.similarityIndex(threshold);
    end = System.currentTimeMillis();
    System.out.println( "Completed in " + ( end - start ) + "ms.");
    System.out.println( "Probability Similarity: " + simIndex + " (0..1)");
  }
}
