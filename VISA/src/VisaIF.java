package visa;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VisaIF extends Remote {
	public boolean compruebaTarjeta(String numero, String titular, String validaDesde, String validaHasta) 	throws RemoteException;
        public boolean altaTarjeta(String numero, String titular, String validaDesde, String validaHasta, float limite)	throws RemoteException;        
        public float carga(String numero_tarjeta, float importe, String establecimiento, String fecha) throws RemoteException;
}
