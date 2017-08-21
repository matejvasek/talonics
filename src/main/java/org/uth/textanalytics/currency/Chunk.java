package org.uth.textanalytics.currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simplistic data object for the storage of a set of tokenised values representing an individual
 * chunk of data (i.e. a sentence).
 * @author Ian Lawson
 */
public class Chunk
{
  private List<Integer> _components = null;
  private TokenDictionary _dictionary = null;
  
  /**
   * Standard constructor.
   */
  public Chunk( TokenDictionary dictionary )
  {
    _components = new ArrayList<>();
    _dictionary = dictionary;
  }
  
  /**
   * Deep copy constructor.
   * @param existingList existing IDs to store
   */
  public Chunk( List<Integer> existingList, TokenDictionary dictionary )
  {
    _components = new ArrayList<>();
    _dictionary = dictionary;
    
    // Deep copy
    for( Integer member : existingList )
    {
      _components.add(member);
    }
  }
  
  /**
   * Add member/ID to end of current chunk.
   * @param member ID to add
   * @return true if successfully added, false otherwise
   */
  public boolean add( Integer member )
  {
    return( _components.add(member));
  }
  
  /**
   * Add a member at a defined position in the chunk.
   * @param member ID to store
   * @param position position in chunk to store at
   */
  public void insert( Integer member, int position )
  {
    ArrayList<Integer> adjustedList = new ArrayList<>();
    
    int pointer = 0;
    boolean found = false;
    
    while( pointer < _components.size() )
    {
      if( pointer == position && !found )
      {
        adjustedList.add(member);
        found = true;
      }
      else
      {
        adjustedList.add( _components.get(position));
        position++;
      }
    }
    
    // Deep copy
    this.reset();
    
    for( Integer adjustedMember : adjustedList )
    {
      _components.add( adjustedMember );
    }
  }
  
  /**
   * Return the count of members in the chunk.
   * @return current size of chunk
   */
  public int size()
  {
    return _components.size();
  }
  
  /**
   * Reset the chunk values.
   */
  public void reset()
  {
    _components = new ArrayList<>();
  }
  
  /**
   * Return the chunk contents.
   * @return list of non-unique ordered IDs for this chunk
   */
  public List<Integer> getChunk()
  {
    return _components;
  }
  
  /**
   * Return distinct set of IDs in the chunk.
   * @return list of unique IDs in this chunk
   */
  public List<Integer> getMembers()
  {
    List<Integer> members = new ArrayList<>();
    
    for( Integer comparitor : _components )
    {
      if( !( members.contains(comparitor)))
      {
        members.add(comparitor);
      }
    }
    
    return members;
  }
  
  /**
   * Generate a frequency map for the existing members in the chunk.
   * @return map of IDs against frequency
   */
  public Map<Integer,Integer> getFrequencies()
  {
    Map<Integer,Integer> workingFreqs = new HashMap<>();
    
    for( Integer member : _components )
    {
      if( !( workingFreqs.containsKey(member)))
      {
        workingFreqs.put(member, 1);
      }
      else
      {
        int currentCount = workingFreqs.get(member);
        
        workingFreqs.remove(member);
        workingFreqs.put(member, currentCount + 1 );
      }
    }
    
    return workingFreqs;
  }

  /**
   * Dictionary lookup.
   * @param target ID to get actual word for
   * @return actual word or null if none exists
   */
  public String dictionaryLookup( int target )
  {
    return _dictionary.get(target);
  }
}
