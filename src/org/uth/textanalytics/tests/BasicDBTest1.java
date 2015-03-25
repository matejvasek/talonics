package org.uth.textanalytics.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Basic DB connectivity test.
 * @author Ian Lawson
 */
public class BasicDBTest1
{
  public static void main( String[] args )
  {
    if( args.length != 4 )
    {
      System.out.println( "Usage: java BasicDBTest1 port database username password" );
      System.exit(0);
    }
    
    new BasicDBTest1( args[0], args[1], args[2], args[3] );
  }
  
  public BasicDBTest1( String port, String database, String username, String password )
  {
    Connection connection = null;
    Properties connectionProperties = new Properties();
    connectionProperties.put("user", username );
    connectionProperties.put("password", password);
    
    String connectionString = "jdbc:mysql://localhost:" + port + "/" + database;
    
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      
      connection = DriverManager.getConnection(connectionString, connectionProperties);
      
      Statement statement = connection.createStatement();
      
      String sql = "select * from ngrams";
      
      ResultSet results = statement.executeQuery(sql);
      
      int count = 1;
      
      while( results.next())
      {
        System.out.println( count++ + ": " + results.getString("id") + " " + results.getString("ngram") + " " + results.getString("count"));
      }
      
      connection.close();
    }
    catch( ClassNotFoundException | SQLException exc )
    {
      System.err.println( "Exception occured: " + exc.toString());
    }
  }
  
}
