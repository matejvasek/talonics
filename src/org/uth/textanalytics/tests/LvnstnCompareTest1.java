package org.uth.textanalytics.tests;

import org.uth.textanalytics.utils.FileUtils;
import org.uth.textanalytics.utils.LvnstnDistance;
import org.uth.textanalytics.utils.TextUtilities;

/**
 * This is a test to compare two file inputs using Lvnstn distances.
 * @author Ian Lawson
 */
public class LvnstnCompareTest1
{
  public LvnstnCompareTest1( String file1, String file2, boolean retainSpaces )
  {
    long start = System.currentTimeMillis();
    
    // Load each file into a string
    String file1Contents = FileUtils.loadString(file1);
    String file2Contents = FileUtils.loadString(file2);
    
    if( file1Contents == null )
    {
      System.out.println( "Unable to load and process file 1 (" + file1 + ").");
      System.exit(0);
    }
    
    if( file2Contents == null )
    {
      System.out.println( "Unable to load and process file 2 (" + file2 + ").");
      System.exit(0);
    }
    
    // Cleanse each file appropriately
    file1Contents = TextUtilities.alphaNumeric(file1Contents, retainSpaces);
    file2Contents = TextUtilities.alphaNumeric(file2Contents, retainSpaces);
    
    int lvnstnDistance = LvnstnDistance.getDistance(file1Contents, file2Contents);

    long end = System.currentTimeMillis();
    
    System.out.println( "Comparator sizes are (file1:" + file1Contents.length() + ") (file2:" + file2Contents.length() + ")" );
    System.out.println( "Evaluated Lvnstn Distance between inputs is " + lvnstnDistance + " calculated in " + ( end - start ) + "ms." );
  }
  
  public static void main( String[] args )
  {
    if( args.length != 3 )
    {
      System.out.println( "Usage: java LvnstnCompareTest1 filename1 filename2 retainSpaces(true|false)");
      System.exit(0);
    }
    
    new LvnstnCompareTest1( args[0], args[1], args[2].toLowerCase().equals("true"));
  }
}
