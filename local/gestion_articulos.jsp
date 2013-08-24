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

<jsp:useBean id="articulo" scope="page" class="ogs.ArticuloBean" />
<html>
<head>
<title>Men&uacute; sede local</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
 <% if (! "si".equals(request.getSession().getAttribute("autenticado"))){ 
       request.getSession().setAttribute("mensaje","Usuario no autenticado");
       RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
       if (dispatcher != null)
           dispatcher.forward(request, response);    
                
  } %>
<form name="form1" method="post" action="/servidor_local/servlet/ServletLocal">
  &nbsp;<input name="pagina_actual" type="hidden" value="gestion_articulos">
  Gesti&oacute;n de art&iacute;culos 
  <% if( session.getAttribute("mensaje") != null){ %>
    - <%= session.getAttribute("mensaje") %>
  <% session.removeAttribute("mensaje");
  } %>
  <input name="alta_articulo" type="submit" id="alta_articulo" value="Alta de art&iacute;culo">
  <input name="volver" type="submit" id="volver" value="Volver">
  <input type="submit" name="salir" value="Salir">
</form>
<hr>
<div align="center">
  <% Vector articulos=(Vector) articulo.getArticulos();
    Enumeration e=articulos.elements();
    
%> 
Art&iacute;culos disponibles
</div>
<table width="100%"  border="1">
  <tr>
    <th scope="col">Tipo</th>
    <th scope="col">Nombre</th>
    <th scope="col">Publico</th>
    <th scope="col">&nbsp;</th>
  </tr>
  <% while (e.hasMoreElements()){
    ogs.ArticuloBean ab=(ogs.ArticuloBean) e.nextElement();
  %>
  <form name="ID" method="post" action="/servidor_local/servlet/ServletLocal">
      <input name="pagina_actual" type="hidden" value="gestion_articulos">
      <input name="nombre" type="hidden" value="<%= ab.getNombre() %>">
  <tr>
    <td><%= ab.getTipo() %> &nbsp;</td>
    <td><%= ab.getNombre() %> &nbsp;</td>
    <td><%= ab.getPublico() %>&nbsp;</td>
    <td><input name="baja_articulo" type="submit" id="baja_articulo" value="Dar de baja"></td>
  </tr>
  </form>
  <% } // while %>
</table>
<p>&nbsp;</p>
</body>
</html>
