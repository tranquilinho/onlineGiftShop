package ogs;

import java.sql.*;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Properties;
import java.io.*;
import java.util.Vector;

public class ArticuloImpl implements ArticuloIF {
    
    private Connection con=null;
    
    // private static String DB_PARAMETERS_FILE = "articulos_db.properties";
    private static String dbUrl="jdbc:mysql://localhost/sede_local";
    private static String dbUser="root";
    private static String dbPassword="";
    
    private String error_trace="";
    
    // Abrimos la conexión con la base de datos en el constructor
    public ArticuloImpl() {
        try {
            // Se carga el driver en memoria
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            con = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public String getEstado(String articulo, int pedido){
        try {
            
            String query = "select estado from articulos_pedido where " +
                    "articulo = '" + articulo + "' and pedido = " + pedido;
            
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            String estado = null;
            if(rs.next())
                estado=rs.getString("estado");
            return estado;
        } catch (Exception e) {
            System.out.println(e.toString());
            error_trace += e.toString();
            if(con == null)
                error_trace += "; conexion nula";
            return (error_trace);
        }
        
        
    }
    
    public float getPrecio(String articulo){
        try {
            
            String query = "select precio from articulo where " +
                    "nombre = '" + articulo + "'";
            
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            float precio = -1;
            if(rs.next())
                precio=rs.getFloat("precio");
            return precio;
        } catch (Exception e) {
            System.out.println(e.toString());
            return -1;
        }
        
        
    }
    
    public String getTipo(String articulo){
        try {
            
            String query = "select tipo from articulo where " +
                    "nombre = '" + articulo + "'";
            
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            String tipo=null;
            if(rs.next())
                tipo=rs.getString("tipo");
            return tipo;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
        
        
    }
    
    public ArticuloBean[] getArticulosDisponibles(){
        try {
            
            String query = "select * from articulo";
            
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            Vector articulos=new Vector();
            while(rs.next()){
                ArticuloBean ab=new ArticuloBean();
                ab.setNombre(rs.getString("nombre"));
                ab.setPrecio(rs.getFloat("precio"));
                ab.setTipo(rs.getString("tipo"));
                ab.setPublico(rs.getString("publico"));
                
                articulos.add(ab);
            }
            
            ArticuloBean[] res=new ArticuloBean[articulos.size()];
            return (ArticuloBean[])(articulos.toArray(res));
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
        
    }
    
    public void nuevo_pedido(int id_pedido,String fecha,String calle,String ciudad,String cp) throws RemoteException {
        String query = "insert into direccion values(default,'" + calle + "','" + ciudad + "','" + cp + "')";
        
        try{
            Statement stmt = con.createStatement();
            
            stmt.executeUpdate(query);
            
            java.sql.ResultSet rs = stmt.executeQuery("select last_insert_id()");
            int id_direccion = -1;
            if(rs.next()) {
                id_direccion=rs.getInt(1);
            }
            java.sql.Date fecha_sql = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT,new Locale("ES")).parse(fecha).getTime());
            PreparedStatement stmt2 = con.prepareStatement("INSERT INTO pedido VALUES (?,?,?)");
            stmt2.setInt(1,id_pedido);
            stmt2.setDate(2,fecha_sql);
            stmt2.setInt(3,id_direccion);
            stmt2.executeUpdate();
            
        }catch (Exception ex){}
        
    }

public void anyade_articulo(int id_pedido,String articulo,int cantidad) throws RemoteException{
    
        String query = "insert into articulos_pedido values('" + articulo + "'," + id_pedido + "," + cantidad + ",'embalaje')";
        
        try{
            Statement stmt = con.createStatement();
            
            stmt.executeUpdate(query);
            
            
        }catch (Exception ex){}

    
}    
    
    
    public static void main(String[] args){
        // prueba la conexion a la BD
        ArticuloImpl imp= new ArticuloImpl();
        ArticuloBean [] articulos = imp.getArticulosDisponibles();
        for(int i=0; i < articulos.length; i++)
            System.out.println(articulos[i].getNombre());
        
    }
}
