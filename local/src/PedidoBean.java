package ogs;

import java.beans.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class PedidoBean extends Object {

   
    public PedidoBean() {
    }

    private void load_from_db() throws Exception{
       
        // Se crea una consulta
	java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
					
	java.sql.ResultSet rs = stmt.executeQuery("select fecha,estado from pedido where id = '" + getId() + "'");
					
        if(rs.next()) {
                setFecha(rs.getString("fecha"));
                setEstado(rs.getString("estado"));
        }

    }

    /**
     * Holds value of property id.
     */
    private int id;

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
     * Holds value of property fecha.
     */
    private String fecha;

    /**
     * Getter for property fecha.
     * @return Value of property fecha.
     */
    public String getFecha() throws Exception{
        if (fecha==null)
            load_from_db();        
        return this.fecha;
    }

    /**
     * Setter for property fecha.
     * @param fecha New value of property fecha.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Holds value of property estado.
     */
    private String estado;

    /**
     * Getter for property estado.
     * @return Value of property estado.
     */
    public String getEstado() throws Exception {
        if (estado==null)
            load_from_db();        
        return this.estado;
    }

    /**
     * Setter for property estado.
     * @param estado New value of property estado.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

public Collection getPedidos(String estado) throws SQLException,Exception{
        Vector res=new Vector();
      
        // Se crea una consulta
	java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
					
	// limit dice el número máximo de respuestas. Su sintaxis es:
        // limit desplazamiento,limite
	java.sql.ResultSet rs = stmt.executeQuery("select * from pedido where estado = " + estado );
					
	// Se van obteniendo los resultados fila a fila
        while(rs.next()) {
               
                PedidoBean pb=new PedidoBean();
                pb.setId(rs.getInt("id"));
                pb.setFecha(rs.getString("fecha"));
                pb.setEstado(rs.getString("estado"));
                res.add(pb);

        }

        return res;
    }

    
}
