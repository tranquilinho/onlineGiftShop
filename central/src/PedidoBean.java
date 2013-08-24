package ogs;

import java.beans.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import javax.xml.rpc.Stub;
import java.text.DateFormat;
import java.util.Locale;


public class PedidoBean extends Object {
    
    
    public PedidoBean() {
    }
    
    private void load_from_db() throws Exception{
        
        // Se crea una consulta
        java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
        
        java.sql.ResultSet rs = stmt.executeQuery("select fecha,estado,cliente,direccion from pedido where id = '" + getId() + "'");
        
        if(rs.next()) {
            setFecha(rs.getString("fecha"));
            setEstado(rs.getString("estado"));
            setCliente(rs.getString("cliente"));
            setDireccionId(rs.getInt("direccion"));
        }
        
    }
    
    private static Stub createProxy() {
        return (Stub) (new ArticulosService_Impl().getArticuloIFPort());
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
    
    public int crear() throws Exception{
        int res=-1;
        java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
        
        java.sql.Date fecha_sql = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT,new Locale("ES")).parse(getFecha()).getTime());
        PreparedStatement stmt2 = ConexionBean.getConexion().prepareStatement("INSERT INTO pedido VALUES (default,?,?,?)");
        stmt2.setDate(1,fecha_sql);
        stmt2.setInt(2,getDireccionId());
        stmt2.setString(3,getCliente());
        stmt2.executeUpdate();
        
        java.sql.ResultSet rs = stmt.executeQuery("select last_insert_id()");
        
        if(rs.next()) {
            res=rs.getInt(1);
        }
        id= res;
        return res;
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
    
    private Collection getPedidosQuery(String query) throws SQLException,Exception{
        Vector res=new Vector();
        
        // Se crea una consulta
        java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
        
        java.sql.ResultSet rs = stmt.executeQuery(query);
        
        // Se van obteniendo los resultados fila a fila
        while(rs.next()) {
            
            PedidoBean pb=new PedidoBean();
            pb.setId(rs.getInt("id"));
            pb.setFecha(rs.getString("fecha"));
            pb.setDireccion(rs.getString("calle") + " - " + rs.getString("cp") + " " + rs.getString("ciudad"));
            
            res.add(pb);
            
        }
        
        return res;
    }
    
    public Collection getPedidos(String estado) throws SQLException,Exception{
        
        String query = "select * from pedido join direccion on direccion_pedido = direccion.id ";
        
        if (estado != null)
            query = query + "where estado = " + estado ;
        
        return getPedidosQuery(query);
    }
    
    public Collection getPedidosUsuario(String login) throws SQLException,Exception{
        
        String query = "select * from pedido join direccion on direccion_pedido = direccion.id " +
                " where cliente = '" + login + "'";
        
        return getPedidosQuery(query);
    }
    
    
    public Collection getArticulos() throws SQLException,Exception{
        Vector res=new Vector();
        
        // Obtener el proxy al servidor
        Stub stub = null;
        ArticuloIF servicio_articulos = null;
        
        // Se crea una consulta
        java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
        
        // limit dice el número máximo de respuestas. Su sintaxis es:
        // limit desplazamiento,limite
        String query = "select * from articulos_pedido join sede on sede = sede.id where pedido = " + getId();
        
        java.sql.ResultSet rs = stmt.executeQuery(query);
        float precio_pedido=0;
        // Se van obteniendo los resultados fila a fila
        while(rs.next()) {
            
            ArticuloBean ab=new ArticuloBean();
            ab.setNombre(rs.getString("articulo"));
            ab.setCantidad(rs.getInt("cantidad"));
            
            String url_sede=rs.getString("url");
            /* Obtener estado,tipo y precio del servicio de articulos */
            //ab.setEstado(rs.getString("estado"));
            if(stub == null){
                // Inicializar
                stub = createProxy();
                stub._setProperty(javax.xml.rpc.Stub.ENDPOINT_ADDRESS_PROPERTY, url_sede);
                servicio_articulos = (ArticuloIF) stub;
            }
            ab.setPrecio(servicio_articulos.getPrecio(ab.getNombre()));
            ab.setTipo(servicio_articulos.getTipo(ab.getNombre()));
            precio_pedido += ab.getPrecio();
            ab.setEstado(servicio_articulos.getEstado(ab.getNombre(),getId()));
            res.add(ab);
            
        }
        setPrecio(precio_pedido);
        return res;
    }
    
    
    
    /**
     * Holds value of property direccion.
     */
    private String direccion;
    
    /**
     * Getter for property direccion.
     * @return Value of property direccion.
     */
    public String getDireccion() {
        return this.direccion;
    }
    
    /**
     * Setter for property direccion.
     * @param direccion New value of property direccion.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
     * Holds value of property cliente.
     */
    private String cliente;
    
    /**
     * Getter for property cliente.
     * @return Value of property cliente.
     */
    public String getCliente() {
        return this.cliente;
    }
    
    /**
     * Setter for property cliente.
     * @param cliente New value of property cliente.
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    /**
     * Holds value of property direccionId.
     */
    private int direccionId;
    
    /**
     * Getter for property direccionId.
     * @return Value of property direccionId.
     */
    public int getDireccionId() {
        return this.direccionId;
    }
    
    /**
     * Setter for property direccionId.
     * @param direccionId New value of property direccionId.
     */
    public void setDireccionId(int direccionId) {
        this.direccionId = direccionId;
    }
    
    public void anyade_articulo(String articulo,int cantidad,int sede) throws Exception{
        
        String query = "insert into articulos_pedido values('" + articulo + "'," + getId() + "," + cantidad + "," + sede + ")";
        
        // Se crea una consulta
        java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
        
        stmt.executeUpdate(query);
        
        
    }
    
    
    
}
