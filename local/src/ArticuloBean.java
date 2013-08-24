package ogs;

import java.beans.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class ArticuloBean extends Object {

   
    public ArticuloBean() {
    }

    private void load_from_db() throws Exception{
       
        // Se crea una consulta
	java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
					
	java.sql.ResultSet rs = stmt.executeQuery("select * from articulo where nombre = '" + getNombre() + "'");
					
        if(rs.next()) {
                setPrecio(rs.getFloat("precio"));
                setPublico(rs.getString("publico"));
                setTipo(rs.getString("tipo"));
        }

    }

    public void eliminar() throws Exception{
        if (getNombre() != null){
            java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
            stmt.executeUpdate("delete from articulo where nombre = '" + getNombre() + "'");
        }
        
    }
    
    public void crear() throws Exception{
        // Comprobar que los atributos no nulos estan definidos
        if (getNombre() == null)
                throw new Exception("ArticuloBean.crear - el nombre no esta definido");
        java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
        stmt.executeUpdate("insert into articulo values (" + 
                "'" + getNombre() + "','" + getTipo() + "'," + getPrecio() + ",'" + getPublico() + "')");
        
    }

/* Devuelve los articulos que resultan de la query */
private Collection getArticulos (String query) throws Exception{
        Vector res=new Vector();
      
        // Se crea una consulta
	java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
					        
        java.sql.ResultSet rs = stmt.executeQuery(query);
					
	// Se van obteniendo los resultados fila a fila
        while(rs.next()) {
               
                ArticuloBean ab=new ArticuloBean();
                ab.setNombre(rs.getString("nombre"));
                ab.setPrecio(rs.getFloat("precio"));
                ab.setPublico(rs.getString("publico"));
                ab.setTipo(rs.getString("tipo"));
                try{
                    ab.setCantidad(rs.getInt("cantidad"));
                    ab.setEstado(rs.getString("estado"));
                    ab.setPedido(rs.getInt("pedido"));
                }catch (Exception ex){
                    /* Estas columnas son opcionales: puede que no aparezcan en la consulta */
                }
                res.add(ab);

        }

        return res;
}
    
/* Devuelve todos los articulos de la sede */    
public Collection getArticulos() throws SQLException,Exception{
	String query = "select nombre, " +
                "precio, publico, tipo  from " + 
                "articulo";
        
        return getArticulos(query);
}

/* Devuelve los articulos que se encuentran en un cierto estado (cola) */
public Collection getArticulosEnEstado(String estado) throws SQLException,Exception{
					
	String query = "select articulo.nombre as nombre, " +
                "precio, publico, tipo, cantidad,pedido,estado from (articulos_pedido join pedido on articulos_pedido.pedido = pedido.id)" + 
                "join articulo on articulo = articulo.nombre" + " where estado = '" + estado +"' "
                + " order by fecha";
        
        return getArticulos(query);
    }

    /**
     * Holds value of property nombre.
     */
    private String nombre;

    /**
     * Getter for property nombre.
     * @return Value of property nombre.
     */
    public String getNombre() {
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
     * Holds value of property tipo.
     */
    private String tipo;

    /**
     * Getter for property tipo.
     * @return Value of property tipo.
     */
    public String getTipo() {
        return this.tipo;
    }

    /**
     * Setter for property tipo.
     * @param tipo New value of property tipo.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Holds value of property publico.
     */
    private String publico;

    /**
     * Getter for property publico.
     * @return Value of property publico.
     */
    public String getPublico() {
        return this.publico;
    }

    /**
     * Setter for property publico.
     * @param publico New value of property publico.
     */
    public void setPublico(String publico) {
        this.publico = publico;
    }

    /**
     * Holds value of property precio.
     */
    private float precio;

    /**
     * Getter for property precio.
     * @return Value of property precio.
     */
    public float getPrecio() {
        return this.precio;
    }

    /**
     * Setter for property precio.
     * @param precio New value of property precio.
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     * Holds value of property cantidad.
     */
    private int cantidad;

    /**
     * Getter for property cantidad.
     * @return Value of property cantidad.
     */
    public int getCantidad() {
        return this.cantidad;
    }

    /**
     * Setter for property cantidad.
     * @param cantidad New value of property cantidad.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Holds value of property estado.
     */
    private String estado;

    /**
     * Getter for property estado.
     * @return Value of property estado.
     */
    public String getEstado() {
        return this.estado;
    }

    /**
     * Setter for property estado.
     * @param estado New value of property estado.
     */
    public void setEstado(String estado) throws Exception {
        this.estado = estado;
        // Se crea una consulta
	java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
        stmt.executeUpdate("update articulos_pedido set estado='"+estado +"' " +
                "where articulo='" + getNombre() + "' and pedido=" + getPedido());
        
        
    }

    /**
     * Holds value of property pedido.
     */
    private int pedido;

    /**
     * Getter for property pedido.
     * @return Value of property pedido.
     */
    public int getPedido() {
        return this.pedido;
    }

    /**
     * Setter for property pedido.
     * @param pedido New value of property pedido.
     */
    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

    public void setTemporadas(String [] temporadas) throws Exception{
        for(int i=0; i < temporadas.length; i++){
            String temporada = temporadas[i];
            java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
            stmt.executeUpdate("insert into temporadas_articulo values ('"+ getNombre() + "','" +
                temporada + "')");
        }
    }
    
}
