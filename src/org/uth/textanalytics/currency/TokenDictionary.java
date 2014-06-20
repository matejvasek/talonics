package org.uth.textanalytics.currency;

import java.util.HashMap;
import java.util.Set;

/**
 * This currency class provides storage for a bi-directional lookup 
 * token dictionary (ID->word, word->ID)
 * @author Ian Lawson
 */
public class TokenDictionary
{
  private HashMap<Integer,String> _dictionary = new HashMap<>();

  public TokenDictionary()
  {
  }
  
  public void reset()
  {
    _dictionary = new HashMap<>();
  }
  
  public int size()
  {
    return _dictionary.size();
  }
  
  public boolean add( Integer ID, String value )
  {
    if( _dictionary.containsKey(ID)) { return false; }
    if( _dictionary.containsValue(value)) { return false; }
    
    _dictionary.put(ID, value);
    return true;
  }
  
  public boolean remove( Integer ID )
  {
    if( !( _dictionary.containsKey(ID))) { return false; }
    
    _dictionary.remove(ID);
    return true;
  }
  
  public String get( Integer ID )
  {
    if( !( _dictionary.containsKey(ID))) { return null; }
    
    return _dictionary.get(ID);
  }
  
  public Integer getID( String value )
  {
    if( !( _dictionary.containsValue(value))) { return null; }
    
    for( Integer key : _dictionary.keySet())
    {
      String compareValue = _dictionary.get(key);
      
      if( compareValue.equals(value) )
      {
        return key;
      }
    }
    
    return null;
  }
  
  public Set<Integer> getIDs()
  {
    return _dictionary.keySet();
  }
}
