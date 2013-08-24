package ogs;

import java.beans.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class TemporadaBean extends Object {

   
    public TemporadaBean() {
    }

    /**
     * Holds value of property nombre.
     */
    private String nombre;

    /**
     * Getter for property estado.
     * @return Value of property estado.
     */
    public String getNombre() throws Exception {
        return this.nombre;
    }

    /**
     * Setter for property estado.
     * @param estado New value of property estado.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

public Collection getTemporadas() throws SQLException,Exception{
        Vector res=new Vector();
      
        // Se crea una consulta
	java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
					
	// limit dice el número máximo de respuestas. Su sintaxis es:
        // limit desplazamiento,limite
	java.sql.ResultSet rs = stmt.executeQuery("select * from temporada");
					
	// Se van obteniendo los resultados fila a fila
        while(rs.next()) {
               
                TemporadaBean tb=new TemporadaBean();
                tb.setNombre(rs.getString("nombre"));
                res.add(tb);

        }

        return res;
    }

    
}
