package org.uth.textanalytics.utils;

/**
 * Probability Analytics Utilities. This class provides mechanisms for comparing
 * two probability matrices in order to determine levels of similarity between two
 * documents.
 * @author Ian Lawson
 */
public class ProbabilityAnalyticsUtils
{
  ProbabilityMatrix _matrix1 = null;
  ProbabilityMatrix _matrix2 = null;
  
  public ProbabilityAnalyticsUtils( ProbabilityMatrix matrix1, ProbabilityMatrix matrix2 )
  {
    _matrix1 = matrix1;
    _matrix2 = matrix2;
  }
  
  /**
   * Similarity Index. This is a coarse mechanism for probability threshold
   * comparison.
   * @param threshold the +/- allowed value between probabilities for association
   * @return the similarity index between the two matrices
   */
  public float similarityIndex( float threshold )
  {
    // Generate required statistics
    int countSource = _matrix1.totalStoredProbabilities();
    int countTarget = _matrix2.totalStoredProbabilities();
    int matchedCount = 0;
    int matchedProbabilities = 0;
    
    // Generate matched key/token count
    for( String key : _matrix1.getMatrix().keySet())
    {
      if( _matrix2.getMatrix().keySet().contains(key))
      {
        // Compare probabilities.
        for( String token : _matrix1.getMatrix().get(key).keySet() )
        {
          if( _matrix2.getMatrix().get(key).containsKey(token))
          {
            matchedCount++; 
            
            float srcProb = _matrix1.generateProbability(key, token);
            float trgProb = _matrix2.generateProbability(key, token);
            
            float min = Math.min(srcProb, trgProb);
            float max = Math.max(srcProb, trgProb);
            
            if( min + threshold >= max ) matchedProbabilities++;
          }
        }
      }
    }
    
    float matched = (float)matchedProbabilities / (float)matchedCount;
    
    float countDiff = Math.min((float)countSource, (float)countTarget) / Math.max((float)countSource, (float)countTarget);
    
    System.out.println( "MP: " + matchedProbabilities + " MC: " + matchedCount + " CS: " + countSource + " CT: " + countTarget );
    
    // Algorithm - matchedProb / matchedCount * ( min srcCount,trgCount / max srcCount,trgCount )
    return (matched * countDiff);    
  }
}
