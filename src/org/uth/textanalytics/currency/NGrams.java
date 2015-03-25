package org.uth.textanalytics.currency;

import java.util.HashMap;
import java.util.Map;

/**
 * Currency class representing a set of n-grams with frequencies
 * @author Ian Lawson
 */
public class NGrams
{
  private Map<String,Integer> _ngrams = new HashMap<>();
  private int _nsize = 0;
  
  /**
   * Constructor taking n-gram size.
   * @param size n-gram size to store
   */
  public NGrams( int size )
  {
    _nsize = size;
  }
  
  /**
   * Incremental add - this adds one to the stored count for the target (or creates it with 1 if it
   * does not exist).
   * @param ngram target value to increment count for
   */
  public void incrementalAdd( String ngram )
  {
    if( !( _ngrams.containsKey(ngram)))
    {
      _ngrams.put(ngram, 1);
    }
    else
    {
      int currentCount = _ngrams.get(ngram);
      _ngrams.remove(ngram);
      _ngrams.put(ngram, (currentCount + 1));
    }
  }

  /**
   * Add a count for an ngram.
   * @param ngram ngram to store count for
   * @param count count to store for ngram
   * @return true if the ngram is storable, false otherwise
   */
  public boolean add( String ngram, int count )
  {
    if( ngram.length() != _nsize ) { return false;}
    
    if( _ngrams.containsKey(ngram)) { return false;}
    
    _ngrams.put(ngram, count);
    return true;
  }

  /**
   * Returns the total count of all stored ngram counts.
   * @return total of all stored ngram counts
   */
  public int getTotalCount()
  {
    int workingCount = 0;
    
    for( String key : _ngrams.keySet())
    {
      workingCount += _ngrams.get(key);
    }
    
    return workingCount;
  }

  /**
   * Returns basic frequency for the target ngram. Basic frequency if defined as the
   * total count for the ngram divided by the total of all counts of ngrams stored. For
   * example if we had a total of 10 counts and the count for the target ngram was 4 the
   * returned frequency would be 4/10, or 0.4
   * @param target ngram to return frequency for
   * @return the calculated frequency, or 0.0f if this ngram does not exist in the store
   */
  public float getFrequency( String target )
  {
    if( !( _ngrams.containsKey(target))) { return 0.0f; }
    
    return ((float)_ngrams.get(target))/(float)this.getTotalCount();
  }
  
  /**
   * Returns the frequency for the target ngram stated as a percentage of the total
   * counts for all ngrams.
   * @param target ngram to return percentage frequency for
   * @return the count for the ngram divided by 1% of the total counts
   */
  public float getFrequencyPercentage( String target )
  {
    if( !( _ngrams.containsKey(target))) { return 0.0f; }
    
    float onePerc = ((float)this.getTotalCount()) / 100.0f;
    
    return ((float)_ngrams.get(target))/onePerc;
  }

  /**
   * Accessor for the underlying set of ngram counts.
   * @return a map of ngram name against the current count for the ngram
   */
  public Map<String,Integer> getNGrams()
  {
    return _ngrams;
  }
  
  public int getSize()
  {
    return _nsize;
  }
}
