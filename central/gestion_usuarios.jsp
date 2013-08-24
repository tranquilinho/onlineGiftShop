<%@page import="java.util.*" %>
<%!
    public void jspInit(){
        ogs.ConexionBean.setDbURL(getServletContext().getInitParameter("dbURL")); 
        ogs.ConexionBean.setDbUser(getServletContext().getInitParameter("dbUser"));
        ogs.ConexionBean.setDbPassword(getServletContext().getInitParameter("dbPassword"));    
    }
    
    public void jspDestroy() {
        try{
            ogs.ConexionBean.disconnect();
        }catch (Exception ex) {}
    }
   
%>

<%@ page contentType="text/html; charset=iso-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Men&uacute; sede local</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>


 <% if (request.getSession().getAttribute("usuario") == null){ 
       request.getSession().setAttribute("mensaje","Usuario no autenticado");
       RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
       if (dispatcher != null)
           dispatcher.forward(request, response);    
                
  }
  ogs.UsuarioBean usuario = (ogs.UsuarioBean) request.getSession().getAttribute("usuario");
%>

<form name="form1" method="post" action="/servidor_central/servlet/ServletCentral">
  &nbsp;<input name="pagina_actual" type="hidden" value="gestion_usuarios">
  Gesti&oacute;n de usuarios 
  <% if( session.getAttribute("mensaje") != null){ %>
    - <%= session.getAttribute("mensaje") %>
  <% session.removeAttribute("mensaje");
  } %>
  
  <input name="volver" type="submit" id="volver" value="Volver">
  <input type="submit" name="salir" value="Salir">
</form>
<hr>
<div align="center">
  <% Vector usuarios=(Vector) usuario.getUsuarios();
    Enumeration e=usuarios.elements();
    
%> 
  Usuarios 
</div>
<table width="100%"  border="1">
  <tr>
    <th scope="col">Login</th>
    <th scope="col">Nombre</th>
    <th scope="col">&nbsp;</th>
  </tr>
  <% while (e.hasMoreElements()){
    ogs.UsuarioBean ab=(ogs.UsuarioBean) e.nextElement();
  %>
  <form name="ID" method="post" action="/servidor_central/servlet/ServletCentral">
      <input name="pagina_actual" type="hidden" value="gestion_usuarios">
      <input name="login" type="hidden" value="<%= ab.getLogin() %>">
  <tr>
    <td><%= ab.getLogin() %> &nbsp;</td>
    <td><%= ab.getNombre() %> &nbsp;</td>
    <td><input name="baja_usuario" type="submit" id="baja_usuario" value="Dar de baja"></td>
  </tr>
  </form>
  <% } // while %>
</table>
<p>&nbsp;</p>
</body>
</html>
