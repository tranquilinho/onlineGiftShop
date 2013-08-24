import java.io.*;
import java.net.*;
import java.util.Enumeration;

import javax.servlet.*;
import javax.servlet.http.*;
import ogs.ArticuloBean;
import ogs.UsuarioBean;


public class ServletLocal extends HttpServlet {
    
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
        }else if (request.getParameter("pagina_actual").equals("menu_local") ){
            if( request.getParameter("ver_cola_embalaje") != null){
                request.getSession().setAttribute("cola","embalaje");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/cola_articulos.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
                
            }else if (request.getParameter("ver_cola_entrega") != null){
                request.getSession().setAttribute("cola","entrega");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/cola_articulos.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
              
            }else if (request.getParameter("gestion_articulos") != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("/gestion_articulos.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
              
            }
        }else if (request.getParameter("pagina_actual").equals("cola_articulos") ){
            if (request.getParameter("volver") != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("/menu_local.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
            }else if (request.getParameter("cambiar_estado") != null){
                String nuevo_estado= request.getParameter("estado");
                ArticuloBean ab = new ArticuloBean();
                ab.setNombre(request.getParameter("nombre"));
                ab.setPedido(Integer.parseInt(request.getParameter("pedido")));
                try{
                    ab.setEstado(nuevo_estado);
                }catch (Exception ex){}
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("/cola_articulos.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
            }
        }else if (request.getParameter("pagina_actual").equals("gestion_articulos") ){
            if (request.getParameter("volver") != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("/menu_local.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
            }else if (request.getParameter("alta_articulo") != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("/alta_articulo.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);            
            }else if (request.getParameter("baja_articulo") != null){
                String nombre= request.getParameter("nombre");
                ArticuloBean ab = new ArticuloBean();
                ab.setNombre(nombre);
                try{
                    ab.eliminar();
                }catch (Exception ex) {}
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("/gestion_articulos.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);            
                
            }
        }else if (request.getParameter("pagina_actual").equals("alta_articulos") ){
            if (request.getParameter("volver") != null){
                RequestDispatcher dispatcher = request.getRequestDispatcher("/gestion_articulos.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);
            }else if (request.getParameter("alta_articulo") != null){
                ArticuloBean ab = new ArticuloBean();
                ab.setNombre(request.getParameter("nombre"));
                ab.setPrecio(Float.parseFloat(request.getParameter("precio")));
                ab.setPublico(request.getParameter("publico"));
                ab.setTipo(request.getParameter("tipo"));
                
                String [] temporadas = request.getParameterValues("temporada");
                try{
                    ab.crear();        
                    ab.setTemporadas(temporadas);
                }catch (Exception ex){
                    request.getSession().setAttribute("mensaje",ex.toString());
                }
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("/gestion_articulos.jsp");
                if (dispatcher != null)
                    dispatcher.forward(request, response);    
            }

        }

    }
    
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

}
