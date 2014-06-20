package org.uth.textanalytics.currency;

import java.util.ArrayList;
import java.util.List;

/**
 * An item is a sequential group of Chunks.
 * @author Ian Lawson
 */
public class Item
{
  private List<Chunk> _item = null;
  
  /**
   * Standard constructor.
   */
  public Item()
  {
    _item = new ArrayList<Chunk>();
  }
  
  /**
   * Deep copy constructor.
   * @param original original item to copy
   */
  public Item( List<Chunk> original )
  {
    _item = new ArrayList<Chunk>();
    
    // Deep copy
    for( Chunk chunk : original )
    {
      _item.add(chunk);
    }
  }

  /**
   * Return the number of chunks in the item.
   * @return the current number of chunks in this item.
   */
  public int size()
  {
    return _item.size();
  }

  /**
   * Get a positional chunk from the item.
   * @param position position of chunk to return
   * @return the chunk at the position or null if there is no chunk there
   */
  public Chunk get( int position )
  {
    return( ( position >= _item.size() ? null : _item.get(position)));
  }

  /**
   * Add a chunk to the end of the item.
   * @param chunk data chunk to store
   * @return true if the addition was successful, false otherwise
   */
  public boolean add( Chunk chunk )
  {
    return _item.add(chunk);
  }

  /**
   * Chunk overwrite method. This replaces the chunk at the given position with the provided one.
   * @param chunk chunk to store
   * @param position position to overwrite
   * @return false if the position exceeds the size, true if the chunk is replaced 
   */
  public boolean replace( Chunk chunk, int position )
  {
    if( position >= _item.size() ) return false;
    
    _item.set(position, chunk);
    
    return true;
  }
}
