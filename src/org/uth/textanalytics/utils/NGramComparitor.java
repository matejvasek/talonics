package org.uth.textanalytics.utils;

import org.uth.textanalytics.currency.NGrams;

/**
 * Comparitor utility for n-grams.
 * @author Ian Lawson
 */
public class NGramComparitor
{
  private NGrams _testSet = null;
  private NGrams _comparitor = null;
  
  public NGramComparitor( NGrams testSet, NGrams comparitor )
  {
    _testSet = testSet;
    _comparitor = comparitor;
  }
  
  /**
   * Simple token content similarity. This is simply the count
   * of matched keys in the test set and comparitor divided by the maximum of 
   * the set sizes.
   * @return the token content similarity
   */
  public float tokenContentSimilarity()
  {
    // Get the maximum token count
    int maxTokenCount = Math.max(_testSet.getNGrams().size(), _comparitor.getNGrams().size());
    
    int countMatch = 0;
    
    for( String key : _testSet.getNGrams().keySet())
    {
      if(_comparitor.getNGrams().containsKey(key))
      {
        countMatch++;
      }
    }
    
    return (float)countMatch/maxTokenCount;
  }
  
  /**
   * For each matching token the comparative frequency (min(x,y)/max(x,y) is added to
   * the match count, which is then divided by the max(totalSet1,totalSet2) to give a fine
   * grained similarity.
   * @return fine grain similarity
   */
  public float exactSimilarity()
  {
    // Store the max toekn count
    int maxTokenCount = Math.max(_testSet.getNGrams().size(), _comparitor.getNGrams().size());
    
    float countMatch = 0.0f;
    
    for( String key : _testSet.getNGrams().keySet())
    {
      if( _comparitor.getNGrams().containsKey(key))
      {
        int testSetTokenCount = _testSet.getNGrams().get(key);
        int comparitorTokenCount = _comparitor.getNGrams().get(key);
        
        countMatch += ( (float)Math.min(testSetTokenCount,comparitorTokenCount) / 
                        (float)Math.max(testSetTokenCount,comparitorTokenCount ) );
      }
    }
    
    return countMatch / (float)maxTokenCount;
  }
  
  /**
   * Simple check - is the ngram size the same for the test set and the comparitor.
   * @return true if the ngram datasets contain ngrams of the same size
   */
  private boolean sized()
  {
    if( _testSet == null || _comparitor == null )
    {
      return false;
    }
    
    return _testSet.getSize() == _comparitor.getSize();
  }
}
