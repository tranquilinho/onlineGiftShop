package ogs;

import java.beans.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class UsuarioBean extends Object {

   
    public UsuarioBean() {
    }

    /**
     * Holds value of property nombre.
     */
    private String nombre;

    /**
     * Getter for property nombre.
     * @return Value of property nombre.
     */
    public String getNombre() throws Exception{
        if (this.nombre == null){
            // extraerlo de la base de datos
            load_from_db();
        }
        return this.nombre;
    }

    /**
     * Setter for property nombre.
     * @param nombre New value of property nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Holds value of property clave.
     */
    private String clave;

    /**
     * Getter for property clave.
     * @return Value of property clave.
     */
    public String getClave() throws Exception{
        if (this.clave == null){
            // extraerla de la base de datos
            load_from_db();
        }
        return this.clave;
    }

    /**
     * Setter for property clave.
     * @param clave New value of property clave.
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    private void load_from_db() throws Exception{
       
        // Se crea una consulta
	java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
					
	java.sql.ResultSet rs = stmt.executeQuery("select nombre,password from usuario where login = '" + getLogin() + "'");
					
        if(rs.next()) {
                setNombre(rs.getString("nombre"));
                setClave(rs.getString("password"));
        }

    }

    /**
     * Holds value of property login.
     */
    private String login;

    /**
     * Getter for property login.
     * @return Value of property login.
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * Setter for property login.
     * @param login New value of property login.
     */
    public void setLogin(String login) {
        this.login = login;
    }
    
}
