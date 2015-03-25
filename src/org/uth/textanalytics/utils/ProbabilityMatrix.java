package org.uth.textanalytics.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Utility classes for Probability Matrix generation and manipulation.
 * @author Ian Lawson
 */
public class ProbabilityMatrix
{
  Map<String,HashMap<String,Integer>> _matrix = new HashMap<>();
  List<String> _ngrams = new ArrayList<>();

  public ProbabilityMatrix( String source, int ngramSize, boolean cleanse, boolean retainSpaces )
  {
    // Generate the ngram list
    if( cleanse )
    {
      source = TextUtilities.alphaNumeric(source,retainSpaces);
    }
    
    // Linearly process string obtaining ngrams and incrementing counts
    for( int loop = 0; loop < ( source.length() - ( ngramSize - 1 )); loop++ )
    {
      String ngram = source.substring(loop, loop + ngramSize );
      _ngrams.add(ngram);
    }    
  }
  
  /**
   * Build the probability count matrix for (ngram sequence)->(ngram->count).
   * @param ngramCount how big to make the ngram sequence for next ngram probability
   */
  public void generateProbabilities( int ngramCount )
  {
    _matrix = new HashMap<>();
    
    // Build the matrix
    for( int loop = 0; loop < ( _ngrams.size() - ( ngramCount + 1 )); loop++ )
    {
      StringBuilder key = new StringBuilder();
      
      int loop2 = 0;
      for( loop2 = loop; loop2 < ( loop + ngramCount ); loop2++ )
      {
        key.append(_ngrams.get(loop2));
      }
      
      if( !_matrix.containsKey(key.toString()))
      {
        _matrix.put(key.toString(), new HashMap<String,Integer>());
      }
      
      // Get the next ngram *after* the key
      String nextNGram = _ngrams.get( loop2 );
      
      if( _matrix.get(key.toString()).containsKey(nextNGram) )
      {
        int currentCount = _matrix.get(key.toString()).get(nextNGram);
        
        _matrix.get(key.toString()).remove(nextNGram);
        _matrix.get(key.toString()).put(nextNGram, (currentCount + 1));
      }
      else
      {
        _matrix.get(key.toString()).put(nextNGram, 1);
      }
    }
  }
  
  /**
   * Returns a predicted ngram given a combinatorial key. This works by
   * generating a fully populated probability vector and then randomly
   * picking an ngram. This fully populated probability vector involves 
   * creating an array of length sum(ngrams for key).
   * @param key key to predict next ngram for
   * @return predicted ngram pr null if no key exists in current matrix
   */
  public String predictNGram( String key )
  {
    if( !( _matrix.containsKey(key)))
    {
      return null;
    }
    
    Map<String,Integer> ngrams = _matrix.get(key);
    
    int total = 0;
    for( int count : ngrams.values())
    {
      total += count;
    }
    
    Random random = new Random();
    
    int chosenNGram = random.nextInt(total);
    
    int runningCount = 0;
    for( String ngram : ngrams.keySet())
    {
      int ngramWindow = ngrams.get(ngram);
      
      if( runningCount <= chosenNGram && ((runningCount + ngramWindow) - 1 ) >= chosenNGram )
      {
        return ngram;
      }
      
      runningCount += ngramWindow;
    }
    
    return null;
  }
  
  /**
   * Count of all stored probabilities.
   * @return count of all key/token combinations
   */
  public int totalStoredProbabilities()
  {
    int total = 0;  
  
    for( String key : _matrix.keySet())
    {
      for( String token : _matrix.get(key).keySet())
      {
        total++;
      }
    }
    
    return total;
  }
  
  /**
   * Generate probability for target token in target key.
   * @param key key to examine
   * @param token following n-gram
   * @return probability that this n-gram will follow this key
   */
  public float generateProbability( String key, String token )
  {
    // Calculate total frequency
    Map<String,Integer> keyProbs = _matrix.get(key);
    
    int frequencyTotal = 0;
    
    for( String tokenCount : keyProbs.keySet())
    {
      frequencyTotal += keyProbs.get(tokenCount);
    }
    
    float onePercent = (float)frequencyTotal / 100.0f;
    
    if( !keyProbs.containsKey(token)) return 0.0f;
    
    return ((float)keyProbs.get(token) / onePercent );
  }
  
  /**
   * For test purposes - accessor for the probability matrix.
   * @return the current probability matrix stored
   */
  public Map<String,HashMap<String,Integer>> getMatrix()
  {
    return _matrix;
  }
}
