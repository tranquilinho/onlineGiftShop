package ogs;

import java.beans.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class SedeBean extends Object {

   
    public SedeBean() {
        
    }

    private void load_from_db() throws Exception{
       
        // Se crea una consulta
	java.sql.Statement stmt = ConexionBean.getConexion().createStatement();

        String query = "select * from sede ";
        if(getId() == -1)
            // buscar por nombre
            query += " where nombre ='" + getNombre() + "'";
        else
            // buscar por id
            query += "where id = " + getId();
        
	java.sql.ResultSet rs = stmt.executeQuery(query);
					
        if(rs.next()) {
                setId(rs.getInt("id"));
                setNombre(rs.getString("nombre"));
                setCiudad(rs.getString("ciudad"));
                setUrl(rs.getString("url"));
        }

    }

    /**
     * Holds value of property id.
     */
    private int id=-1;

    /**
     * Getter for property id.
     * @return Value of property id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter for property id.
     * @param id New value of property id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Holds value of property Nombre.
     */
    private String nombre;

    /**
     * Getter for property estado.
     * @return Value of property estado.
     */
    public String getNombre() throws Exception {
        if (nombre==null)
            load_from_db();        
        return nombre;
    }

    /**
     * Setter for property estado.
     * @param estado New value of property estado.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

public Collection getSedes() throws SQLException,Exception{
        Vector res=new Vector();
      
        // Se crea una consulta
	java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
					
	// limit dice el número máximo de respuestas. Su sintaxis es:
        // limit desplazamiento,limite
	java.sql.ResultSet rs = stmt.executeQuery("select * from sede" );
					
	// Se van obteniendo los resultados fila a fila
        while(rs.next()) {
               
                SedeBean sb=new SedeBean();
                sb.setId(rs.getInt("id"));
                sb.setNombre(rs.getString("nombre"));
                sb.setCiudad(rs.getString("ciudad"));
                sb.setUrl(rs.getString("url"));
                res.add(sb);

        }

        return res;
    }

    /**
     * Holds value of property ciudad.
     */
    private String ciudad;

    /**
     * Getter for property ciudad.
     * @return Value of property ciudad.
     */
    public String getCiudad() {
        return this.ciudad;
    }

    /**
     * Setter for property ciudad.
     * @param ciudad New value of property ciudad.
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Holds value of property url.
     */
    private String url;

    /**
     * Getter for property url.
     * @return Value of property url.
     */
    public String getUrl() throws Exception{
        if(url == null)
            load_from_db();
        return this.url;
    }

    /**
     * Setter for property url.
     * @param url New value of property url.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    
}
