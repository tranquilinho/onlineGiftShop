package visa;

import java.sql.*;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Locale;

public class VisaImpl implements VisaIF {
    
    Connection con=null;
    
    public VisaImpl() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            con = DriverManager.getConnection("jdbc:mysql://localhost/visa","root","");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public boolean compruebaTarjeta(String numero, String titular, String validaDesde, String validaHasta)  {
        try {
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("select * from tarjetas "+
                    "where numero='"+numero+
                    "' and titular='"+titular+
                    "' and validaDesde='"+validaDesde+
                    "' and validaHasta='"+validaHasta+"'");
            
            return rs.next();
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public boolean altaTarjeta(String numero, String titular, String validaDesde, String validaHasta, float limite) throws RemoteException{
        try {
            
            PreparedStatement stmt = con.prepareStatement("INSERT INTO tarjetas VALUES (?,?,?,?,?)");
            
            stmt.setString(1,numero);
            stmt.setString(2,titular);
            stmt.setString(3,validaDesde);
            stmt.setString(4,validaHasta);
            stmt.setFloat(5,limite);
            
            stmt.executeUpdate();
            
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
        return true;        
        
    }
    
    
    public float carga(String numero_tarjeta, float importe, String establecimiento, String fecha) throws RemoteException{
        float saldo=0,importe_cargos=(float)0.0, limite=(float)0.0;
        try {

            PreparedStatement stmt2 = con.prepareStatement("select sum(importe) from cargos where numero_tarjeta = ?");
            stmt2.setString(1,numero_tarjeta);
            ResultSet rs=stmt2.executeQuery();
            if(rs.next())
                importe_cargos = rs.getFloat(1);
            
            PreparedStatement stmt3 = con.prepareStatement("select limite from tarjetas where numero = ?");
            stmt3.setString(1,numero_tarjeta);
            rs=stmt3.executeQuery();
            if(rs.next())
                limite = rs.getFloat(1);
            saldo = limite - importe_cargos;
            
            if(saldo >= importe){
            
                java.sql.Date fecha_sql = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT,new Locale("ES")).parse(fecha).getTime());
                // Se crea una consulta
                PreparedStatement stmt = con.prepareStatement("INSERT INTO cargos VALUES (DEFAULT,?,?,?,?)");

                // Establecer los parametros del cargo en la tabla.
                // La fecha como string tiene formato americano
                stmt.setFloat(1,importe);
                stmt.setString(2,numero_tarjeta);
                stmt.setString(3,establecimiento);
                stmt.setDate(4,fecha_sql);

                stmt.executeUpdate();            
            }
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
        return saldo;
    }
}
