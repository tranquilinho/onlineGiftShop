package ogs;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ArticuloIF extends Remote {
    public String getEstado(String articulo, int pedido) throws RemoteException;
    public float getPrecio(String articulo)throws RemoteException;
    public String getTipo(String articulo)throws RemoteException;
    public ArticuloBean[] getArticulosDisponibles() throws RemoteException;
    public void nuevo_pedido(int id_pedido,String fecha,String calle,String ciudad,String cp) throws RemoteException;
    public void anyade_articulo(int id_pedido,String articulo,int cantidad) throws RemoteException;
}
