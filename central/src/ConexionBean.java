package ogs;

import java.beans.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBean extends Object {
    
    private static Connection dbConnection=null;
    
    public ConexionBean() {

    }

    public static Connection getConexion() throws Exception{
        if(dbConnection == null){
            // Conectar con la base de datos
             try {
                    // Se carga el driver en memoria
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    // Se obtiene una conexión a la base de datos.

                    dbConnection = DriverManager.getConnection(getDbURL(),getDbUser(),getDbPassword());
                } catch (java.lang.ClassNotFoundException e) {
                       throw new Exception("Error en clase: "+e);			
                } catch (Exception ex) {
                    throw new Exception("No se pudo conectar con la base de datos. " +
                        ex.getMessage());
                }            
        }
        return dbConnection;
    }
    
    public static void disconnect() throws Exception{
        try {
            getConexion().close();
            dbConnection= null;
        } catch (SQLException ex) {
            throw new Exception("Error al desconectar: " + ex.getMessage());
        }
    } 
    
    /**
     * Holds value of property dbURL.
     */
    private static String dbURL;

    /**
     * Getter for property dbURL.
     * @return Value of property dbURL.
     */
    public static String getDbURL() {
        return dbURL;
    }

    /**
     * Setter for property dbURL.
     * @param dbURL New value of property dbURL.
     */
    public static void setDbURL(String dbURL) {
        ConexionBean.dbURL = dbURL;
    }

    /**
     * Holds value of property dbUser.
     */
    private static String dbUser;

    /**
     * Getter for property dbUser.
     * @return Value of property dbUser.
     */
    public static String getDbUser() {
        return dbUser;
    }

    /**
     * Setter for property dbUser.
     * @param dbUser New value of property dbUser.
     */
    public static void setDbUser(String dbUser) {
        ConexionBean.dbUser = dbUser;
    }

    /**
     * Holds value of property dbPassword.
     */
    private static String dbPassword;

    /**
     * Getter for property dbPassword.
     * @return Value of property dbPassword.
     */
    public static String getDbPassword() {
        return dbPassword;
    }

    /**
     * Setter for property dbPassword.
     * @param dbPassword New value of property dbPassword.
     */
    public static void setDbPassword(String dbPassword) {
        ConexionBean.dbPassword = dbPassword;
    }
       
}
