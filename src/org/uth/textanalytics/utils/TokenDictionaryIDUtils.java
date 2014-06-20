package org.uth.textanalytics.utils;

import org.uth.textanalytics.currency.TokenDictionary;

/**
 * Utilities class for the generation and maintenance of unique IDs within a
 * token dictionary.
 * @author Ian Lawson
 */
public class TokenDictionaryIDUtils
{
  private TokenDictionary _dictionary = null;
  
  public TokenDictionaryIDUtils( TokenDictionary dictionary )
  {
    _dictionary = dictionary;
  }
  
  public Integer nextID()
  {
    if( _dictionary.getIDs().isEmpty()) { return 1; }
    
    int lastID = 0;
    
    for( Integer ID : _dictionary.getIDs())
    {
      if( ID > lastID )
      {
        lastID = ID;
      }
    }
    
    return lastID + 1;
  }
  
  public Integer lowestAvailableID()
  {
    if( _dictionary.getIDs().isEmpty()) { return 1; }
    
    // Get the uppermost ID
    int highest = 0;
    
    for( Integer ID : _dictionary.getIDs())
    {
      if( ID > highest) highest = ID;
    }
    
    int testID = 1;
    
    while( testID != highest )
    {
      if( _dictionary.get(testID) == null ) { return testID; }
    }
    
    return highest + 1;
  }
}
