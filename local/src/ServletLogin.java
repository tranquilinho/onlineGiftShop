import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
import ogs.UsuarioBean;
import ogs.ConexionBean;


public class ServletLogin extends HttpServlet {
    
    /* Pagina a la que se accede cuando la identificacion es correcta */
    private static String PAGINA_PRINCIPAL = "/menu_local.jsp";
    
    public void init() throws ServletException{
        ConexionBean.setDbURL(getServletContext().getInitParameter("dbURL"));
        ConexionBean.setDbUser(getServletContext().getInitParameter("dbUser"));
        ConexionBean.setDbPassword(getServletContext().getInitParameter("dbPassword"));
        
    }
    
    public void destroy(){
        
        try{
            ogs.ConexionBean.disconnect();
        }catch (Exception ex) {}
    }
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        if (request.getParameter("pagina_actual").equals("login") ){
            if( request.getParameter("entrar") != null){
                // identificar al usuario
                String login= request.getParameter("login");
                
                /* if (login == null || login.length() == 0){
                    request.getSession().setAttribute("mensaje","Introduzca su login y password");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                    if (dispatcher != null)
                        dispatcher.forward(request, response);
                } */
                
                String password= request.getParameter("password");
                UsuarioBean ub = new UsuarioBean();
                ub.setLogin(login);
                try{
                    if(ub.getClave().equals(password)){
                        // identificacion correcta - pasar a menu principal
                        request.getSession().setAttribute("autenticado","si");
                        request.getSession().setAttribute("nombre_usuario",ub.getNombre());
                        RequestDispatcher dispatcher = request.getRequestDispatcher(PAGINA_PRINCIPAL);
                        if (dispatcher != null)
                            dispatcher.forward(request, response);
                    }else{
                        // notificar fallo en identificacion
                        request.getSession().setAttribute("mensaje","Login o password incorrectos");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                        if (dispatcher != null)
                            dispatcher.forward(request, response);
                    }
                }catch (Exception ex){
                    request.getSession().setAttribute("mensaje","Login o password incorrectos ");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
                    if (dispatcher != null)
                        dispatcher.forward(request, response);
                    
                }
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
