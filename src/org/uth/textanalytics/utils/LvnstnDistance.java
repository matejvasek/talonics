package org.uth.textanalytics.utils;

/**
 * Levenshtein Distance utility class.
 * @author Ian Lawson
 */
public class LvnstnDistance
{
  public static int getDistance( String s0, String s1 )
  {
    if( s0.length() > s1.length())
    {
      String swap = s0;
      s0 = s1;
      s1 = swap;
    }
    
    int len0 = s0.length() + 1;
    int len1 = s1.length() + 1;
    
    // Array of distances
    int[] cost = new int[len0];
    int[] newcost = new int[len1];
    
    // Initial cost of skipping prefix in String s0
    for( int i = 0; i <len0; i++ ) cost[i]=i;
    
    for( int j = 1; j<len1; j++ )
    {
      newcost[0] = j - 1;
      
      for( int i = 1; i < len0; i++ )
      {
        int match = (s0.charAt(i-1) == s1.charAt(j-1) ? 0 : 1 );
        
        // Compute cost for each transformation
        int cost_replace = cost[i-1]+match;
        int cost_insert = cost[i]+1;
        int cost_delete = newcost[i-1]+1;
        
        newcost[i] = Math.min(Math.min(cost_insert,cost_delete),cost_replace);
      }
      
      // Swap the cost/newcost arrays
      int[] swap = cost;
      cost = newcost;
      newcost = swap;
    }
    
    return cost[len0-1];
  }
  
  public static void main( String[] args )
  {
    if( args.length != 2 )
    {
      System.err.println( "Usage: java LvnstnDistance string1 string2");
      System.exit(0);
    }
    
    System.out.println( "Lvnstn Cost for string " + args[0] + " to string " + args[1] + " is [" + LvnstnDistance.getDistance(args[0], args[1]) +"]");
  }
}
