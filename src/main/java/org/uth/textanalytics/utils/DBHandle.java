package org.uth.textanalytics.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.uth.textanalytics.currency.NGrams;

/**
 * Wrapper class for DB handle interaction for storing NGrams.
 * @author Ian Lawson
 */
public class DBHandle
{
  private Connection _connection = null;
  private String _username = null;
  private String _password = null;
  
  /**
   * Standard constructor.
   * @param username username for DB access to store
   * @param password password for DB access to store
   */
  public DBHandle( String username, String password )
  {
    _username = username;
    _password = password;
  }
  
  /**
   * Performs connection to target database.
   * @param connectionString connection string to use for JDBC connectivity
   * @param driverClassName Driver Class Name for JDBC access. This is flexed using Class.forname to preload the class
   * @throws SQLException if any exception occurs communicating with the database
   * @throws ClassNotFoundException if the JDBC class name provided is unresolvable on the classpath
   */
  public void connect( String connectionString, String driverClassName ) throws SQLException, ClassNotFoundException
  {
    System.out.println( "Driver Class: " + driverClassName );
    
    Properties connectionProps = new Properties();
    
    connectionProps.put("user", _username);
    connectionProps.put("password", _password);
    
    Class.forName(driverClassName);
    
    _connection = DriverManager.getConnection(connectionString, connectionProps);
  }

  /**
   * Tidy up and release the connection.
   * @throws SQLException if interaction with the database fails
   */
  public void disconnect() throws SQLException
  {
    _connection.close();
  }

  /**
   * Store the target set of ngrams to the database.
   * @param target ID to associate with the target set of ngrams
   * @param ngrams map of ngram size against ngram set 
   * @throws SQLException if interactions with the database fail
   */
  public void pushTargetSet( String target, Map<Integer,NGrams> ngrams ) throws SQLException
  {
    Statement statement = _connection.createStatement();
    
    for( int size : ngrams.keySet())
    {
      NGrams ngram = ngrams.get(size);
      
      for( String key : ngram.getNGrams().keySet())
      {
        int count = ngram.getNGrams().get(key);
        
        int results = statement.executeUpdate("insert into ngrams (id,ngram,count) values (\"" + target + "\",\"" + key + "\",\"" + count + "\")");
        
        if( results != 1 )
        {
          System.err.println( "Failed to add " + target + ":" + ngram + ":" + count );
        }
      }
    }
  }

  /**
   * Cleans the target set from the database.
   * @param target ID of target set to remove from the database
   * @throws SQLException if interactions with the database fail
   */
  public void removeTargetSet( String target ) throws SQLException
  {
    Statement statement = _connection.createStatement();
    
    statement.executeUpdate("delete from ngrams where id=\"" + target + "\"");
  }

  /**
   * Reads ngram sets from the database and constructs a set of size->ngrams.
   * @param target ID of target set to read from database
   * @return map of size->ngram set read from the database
   * @throws SQLException if interactions with the database fail
   */
  public Map<Integer,NGrams> loadTargetSet( String target ) throws SQLException
  {
    Map<Integer,NGrams> output = new HashMap<>();
    
    Statement statement = _connection.createStatement();
    
    ResultSet results = statement.executeQuery("select ngram,count from ngrams where id=\"" + target + "\"");
    
    while( results.next())
    {
      String ngram = results.getString("ngram");
      int count = results.getInt("count");
      
      if( output.containsKey((Integer)ngram.length()))
      {
        // Grab set
        NGrams existingSet = output.get(ngram.length());
        output.remove(ngram.length());
        
        existingSet.add(ngram, count);
        
        output.put(ngram.length(), existingSet);
      }
      else
      {
        // New set
        NGrams newSet = new NGrams(ngram.length());
        
        newSet.add(ngram, count);
        
        output.put(ngram.length(), newSet);
      }
    }
    
    return output;
  }
}
