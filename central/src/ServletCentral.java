import java.io.*;
import java.net.*;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;



import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.xml.rpc.*;

import ogs.*;

public class ServletCentral extends HttpServlet {
    
    public void init() throws ServletException{
        ogs.ConexionBean.setDbURL(getServletContext().getInitParameter("dbURL"));
        ogs.ConexionBean.setDbUser(getServletContext().getInitParameter("dbUser"));
        ogs.ConexionBean.setDbPassword(getServletContext().getInitParameter("dbPassword"));
        
    }
    
    private static Stub createProxy() {
        return (Stub) (new ArticulosService_Impl().getArticuloIFPort());
    }
    
    
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        
        if (request.getParameter("salir") != null){
            // Terminar la sesion y volver a la pagina de inicio
            request.getSession().invalidate();
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            if (dispatcher != null)
                dispatcher.forward(request, response);
            
        }else if (request.getParameter("pagina_actual").equals("menu_central") ){
            if( request.getParameter("ver_pedidos") != null){
                request.getSession().setAttribute("sede",request.getParameter("sede"));
                RequestDispatcher dispatcher = request.getRequestDispatcher("/pedidos_sede.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
                
            }else if (request.getParameter("gestion_sedes") != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("/gestion_sedes.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
                
            }else if (request.getParameter("gestion_usuarios") != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("/gestion_usuarios.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
                
            }
        }else if (request.getParameter("pagina_actual").equals("pedidos_sede") ){
            if (request.getParameter("volver") != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("/menu_central.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
            }
            
        }else if (request.getParameter("pagina_actual").equals("gestion_usuarios") ){
            if (request.getParameter("volver") != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("/menu_central.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
            }else if (request.getParameter("baja_usuario") != null){
                String login= request.getParameter("login");
                ogs.UsuarioBean ub = new ogs.UsuarioBean();
                ub.setLogin(login);
                try{
                    ub.eliminar();
                }catch (Exception ex) {}
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("/gestion_usuarios.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
                
            }
        }else if (request.getParameter("pagina_actual").equals("menu_usuario") ){
            if (request.getParameter("baja_usuario") != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("/gestion_sedes.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
            }else if (request.getParameter("ver_articulos_sede") != null){
                String sede=request.getParameter("sede");
                request.getSession().setAttribute("sede",sede);
                ogs.SedeBean sb = new ogs.SedeBean();
                sb.setNombre(sede);
                String url_sede = null;
                try{
                    url_sede=sb.getUrl();
                }catch (Exception ex){
                    request.getSession().setAttribute("err",ex.toString());
                }
                
                request.getSession().setAttribute("url_sede",url_sede);
                request.getSession().setAttribute("id_sede",new Integer(sb.getId()));
                RequestDispatcher dispatcher = request.getRequestDispatcher("/pedido.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
            }
            
        }else if (request.getParameter("pagina_actual").equals("pedido") ){
            if (request.getParameter("comprar") != null){
                request.getSession().setAttribute("articulo",request.getParameter("articulo"));
                RequestDispatcher dispatcher = request.getRequestDispatcher("/compra_articulo.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
            }else if (request.getParameter("terminar_pedido") != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("/eleccion_destino.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
            }else if (request.getParameter("cancelar") != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("/menu_usuario.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
                
            }
            
            
        }else if (request.getParameter("pagina_actual").equals("compra_articulo") ){
            if (request.getParameter("comprar") != null){
                Vector cesta = (Vector) request.getSession().getAttribute("cesta");
                ogs.ArticuloBean ab= new ogs.ArticuloBean();
                ab.setNombre((String) request.getSession().getAttribute("articulo"));
                ab.setCantidad(Integer.parseInt(request.getParameter("unidades")));
                String url_sede=(String) request.getSession().getAttribute("url_sede");
                Stub stub = createProxy();
                stub._setProperty(javax.xml.rpc.Stub.ENDPOINT_ADDRESS_PROPERTY, url_sede);
                ArticuloIF servicio_articulos = (ArticuloIF) stub;
                ab.setPrecio(servicio_articulos.getPrecio(ab.getNombre()));
                ab.setTipo(servicio_articulos.getTipo(ab.getNombre()));
                
                cesta.add(ab);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/pedido.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
            }
            
        }else if (request.getParameter("pagina_actual").equals("eleccion_destino") ){
            if (request.getParameter("cerrar") != null){
                try{
                    // cargar importe de la compra
                    float importe = 0;
                    Vector cesta=(Vector) request.getSession().getAttribute("cesta");
                    Enumeration e=cesta.elements();
                    while(e.hasMoreElements()){
                        ogs.ArticuloBean ab= (ogs.ArticuloBean) e.nextElement();
                        importe += ab.getCantidad() * ab.getPrecio();
                        
                    }
                    
                    ogs.UsuarioBean usuario = (ogs.UsuarioBean) request.getSession().getAttribute("usuario");
                    Stub stub = (Stub) new VisaService_Impl().getVisaIFPort();
                    VisaIF servicio_visa = (VisaIF) stub;
                    String numero_tarjeta=null;
                    numero_tarjeta=usuario.getNumero_tarjeta();
                    String establecimiento=(String) request.getSession().getAttribute("sede");
                    String fecha = DateFormat.getDateInstance(DateFormat.SHORT,new Locale("ES")).format(new Date());
                    
                    float saldo=servicio_visa.carga(numero_tarjeta,importe, establecimiento,fecha);
                    if(saldo < 0){
                        request.getSession().removeAttribute("cesta");
                        throw new Exception("La tarjeta no dispone de saldo suficiente");                      
                    }
                    
                    // enviar articulos
                    
                    String calle= request.getParameter("calle");
                    String ciudad= request.getParameter("ciudad");
                    String cp= request.getParameter("cp");
                    String query = "insert into direccion values(default,'" + calle + "','" + ciudad + "','" + cp + "')";
                    Statement stmt = ogs.ConexionBean.getConexion().createStatement();
                    stmt.executeUpdate(query);
                    // obtener el id recien insertado
                    java.sql.ResultSet rs = stmt.executeQuery("select last_insert_id()");
                    int id_direccion = -1;
                    if(rs.next()) {
                        id_direccion=rs.getInt(1);
                    }
                    
                    String url_sede=(String) request.getSession().getAttribute("url_sede");
                    int id_sede=((Integer) request.getSession().getAttribute("id_sede")).intValue();
                    Stub stub_articulos = createProxy();
                    stub_articulos._setProperty(javax.xml.rpc.Stub.ENDPOINT_ADDRESS_PROPERTY, url_sede);
                    ArticuloIF servicio_articulos = (ArticuloIF) stub_articulos;
                    
                    ogs.PedidoBean pb=new ogs.PedidoBean();
                    pb.setFecha(fecha);
                    
                    pb.setDireccionId(id_direccion);
                    pb.setCliente(usuario.getLogin());
                    int id_pedido = pb.crear();
                    
                    servicio_articulos.nuevo_pedido(id_pedido,fecha,calle,ciudad,cp);
                    e=cesta.elements();
                    while(e.hasMoreElements()){
                        ogs.ArticuloBean ab= (ogs.ArticuloBean) e.nextElement();
                        pb.anyade_articulo(ab.getNombre(),ab.getCantidad(),id_sede);
                        servicio_articulos.anyade_articulo(id_pedido,ab.getNombre(),ab.getCantidad());
                    }
                    
                    request.getSession().removeAttribute("cesta");
                    
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/menu_usuario.jsp");
                    if (dispatcher != null)
                        dispatcher.forward(request, response);
                }catch (Exception ex){
                    request.getSession().setAttribute("err",ex.toString());
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/menu_usuario.jsp");
                    if (dispatcher != null)
                        dispatcher.forward(request, response);
                }
                
            }
            
            
        }else if (request.getParameter("pagina_actual").equals("alta_cliente") ){
            if (request.getParameter("volver") != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
            }else if (request.getParameter("alta_cliente") != null){ 
                ogs.UsuarioBean ub = new ogs.UsuarioBean();
                ub.setLogin(request.getParameter("login"));
                ub.setNombre(request.getParameter("nombre"));
                ub.setClave(request.getParameter("clave"));
                ub.setCorreo(request.getParameter("correo"));
                ub.setNumero_tarjeta(request.getParameter("tarjeta"));
                ub.setCalle(request.getParameter("calle"));
                ub.setCiudad(request.getParameter("ciudad"));
                ub.setCp(request.getParameter("cp"));
                
                try{
                    ub.crear();
                }catch (Exception ex){
                    request.getSession().setAttribute("err",ex.toString());
                }
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
            }
            
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
