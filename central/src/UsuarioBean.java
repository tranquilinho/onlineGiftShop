package ogs;

import java.beans.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


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
        
        java.sql.ResultSet rs = stmt.executeQuery("select nombre,password,cliente from usuario where login = '" + getLogin() + "'");
        
        if(rs.next()) {
            setNombre(rs.getString("nombre"));
            setClave(rs.getString("password"));
            esCliente(rs.getBoolean("cliente"));
        }
        
    }
    
    private void load_direccion(){
        try{
            // Se crea una consulta
            java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
            
            java.sql.ResultSet rs = stmt.executeQuery("select * from direccion where id = " + getDireccion());
            
            if(rs.next()) {
                setCalle(rs.getString("calle"));
                setCiudad(rs.getString("ciudad"));
                setCp(rs.getString("cp"));
            }
        }catch (Exception ex){}
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
    
    private boolean es_cliente;
    
    public boolean esCliente(){
        return es_cliente;
    }
    
    public void esCliente(boolean b){
        es_cliente=b;
    }
    
    public Collection getUsuarios() throws Exception{
        Vector res=new Vector();
        
        // Se crea una consulta
        java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
        
        String query = "select * from usuario";
        
        java.sql.ResultSet rs = stmt.executeQuery(query);
        
        // Se van obteniendo los resultados fila a fila
        while(rs.next()) {
            
            UsuarioBean ub=new UsuarioBean();
            ub.setNombre(rs.getString("nombre"));
            ub.setLogin(rs.getString("login"));
            esCliente(rs.getBoolean("cliente"));
            res.add(ub);
            
        }
        
        return res;
    }
    
    public void crear() throws Exception{
        
        java.sql.PreparedStatement stmt3 = ConexionBean.getConexion().prepareStatement("INSERT INTO usuario VALUES (?,?,?,?,?)");
        stmt3.setString(1,getLogin());
        stmt3.setString(2,getNombre());
        stmt3.setString(3,getClave());
        stmt3.setBoolean(4,false);
        stmt3.setBoolean(5,true);
        stmt3.executeUpdate();        

        String query = "insert into direccion values(default,'" + getCalle() + "','" + getCiudad() + "','" + getCp() + "')";
        java.sql.Statement stmt = ogs.ConexionBean.getConexion().createStatement();
        stmt.executeUpdate(query);
        // obtener el id recien insertado
        java.sql.ResultSet rs = stmt.executeQuery("select last_insert_id()");
        int id_direccion = -1;
        if(rs.next()) {
            id_direccion=rs.getInt(1);
        }
        
        
        java.sql.PreparedStatement stmt2 = ConexionBean.getConexion().prepareStatement("INSERT INTO cliente VALUES (?,?,?,?)");
        stmt2.setString(1,getLogin());
        stmt2.setString(2,getCorreo());
        stmt2.setString(3,getNumero_tarjeta());
        stmt2.setInt(4,id_direccion);
        stmt2.executeUpdate();
    }
    
    public void eliminar() throws Exception{
        if (getLogin() != null){
            java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
            stmt.executeUpdate("delete from usuario where login = '" + getLogin() + "'");
        }
        
    }
    
    /**
     * Holds value of property numero_tarjeta.
     */
    private String numero_tarjeta;
    
    /**
     * Getter for property numero_tarjeta.
     * @return Value of property numero_tarjeta.
     */
    public String getNumero_tarjeta() throws Exception{
        if(numero_tarjeta == null){
            java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
            
            java.sql.ResultSet rs = stmt.executeQuery("select numero_tarjeta from cliente where login = '" + getLogin() + "'");
            
            if(rs.next()) {
                numero_tarjeta = (rs.getString("numero_tarjeta"));
            }
            
            
        }
        return this.numero_tarjeta;
    }
    
    /**
     * Setter for property numero_tarjeta.
     * @param numero_tarjeta New value of property numero_tarjeta.
     */
    public void setNumero_tarjeta(String numero_tarjeta) {
        this.numero_tarjeta = numero_tarjeta;
    }
    
    /**
     * Holds value of property calle.
     */
    private String calle;
    
    /**
     * Getter for property calle.
     * @return Value of property calle.
     */
    public String getCalle() {
        if(calle == null)
            load_direccion();
        return this.calle;
    }
    
    /**
     * Setter for property calle.
     * @param calle New value of property calle.
     */
    public void setCalle(String calle) {
        this.calle = calle;
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
        if(ciudad == null)
            load_direccion();
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
     * Holds value of property cp.
     */
    private String cp;
    
    /**
     * Getter for property cp.
     * @return Value of property cp.
     */
    public String getCp() {
        if(cp == null)
            load_direccion();
        return this.cp;
    }
    
    /**
     * Setter for property cp.
     * @param cp New value of property cp.
     */
    public void setCp(String cp) {
        this.cp = cp;
    }
    
    /**
     * Holds value of property direccion.
     */
    private int direccion = -1;
    
    /**
     * Getter for property direccion.
     * @return Value of property direccion.
     */
    public int getDireccion() {
        try{
            if(direccion == -1){
                
                if(esCliente()){
                    java.sql.Statement stmt = ConexionBean.getConexion().createStatement();
                    
                    java.sql.ResultSet rs = stmt.executeQuery("select direccion from cliente where login = '" + getLogin() + "'");
                    
                    if(rs.next()) {
                        direccion = (rs.getInt("direccion"));
                    }
                }
            }
        }catch (Exception ex){}
        return this.direccion;
    }
    
    /**
     * Setter for property direccion.
     * @param direccion New value of property direccion.
     */
    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }
    
    /**
     * Holds value of property correo.
     */
    private String correo;
    
    /**
     * Getter for property correo.
     * @return Value of property correo.
     */
    public String getCorreo() {
        return this.correo;
    }
    
    /**
     * Setter for property correo.
     * @param correo New value of property correo.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
    
    
}
