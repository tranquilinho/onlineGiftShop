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
<% String cola = (String) request.getSession().getAttribute("cola"); %>

<form name="form1" method="post" action="/servidor_local/servlet/ServletLocal">
  &nbsp;<input name="pagina_actual" type="hidden" value="cola_articulos">
  Cola <%= cola %>
  <input name="volver" type="submit" id="volver" value="Volver">
  <input type="submit" name="salir" value="Salir">
</form>
<hr>
<% Vector articulos=(Vector) articulo.getArticulosEnEstado(cola);
    Enumeration e=articulos.elements();
    
%>
<table width="100%"  border="1">
  <tr>
    <th scope="col">Tipo</th>
    <th scope="col">Nombre</th>
    <th scope="col">Cantidad</th>
    <th scope="col">&nbsp;</th>
  </tr>
  <% while (e.hasMoreElements()){
    ogs.ArticuloBean ab=(ogs.ArticuloBean) e.nextElement();
  %>
  <form name="ID" method="post" action="/servidor_local/servlet/ServletLocal">
      <input name="pagina_actual" type="hidden" value="cola_articulos">
      <input name="nombre" type="hidden" value="<%= ab.getNombre() %>">
      <input name="pedido" type="hidden" value="<%= ab.getPedido() %>">
  <tr>
    <td><%= ab.getTipo() %> &nbsp;</td>
    <td><%= ab.getNombre() %> &nbsp;</td>
    <td><%= ab.getCantidad() %>&nbsp;</td>
    <td>Estado: 
      <select name="estado" id="estado">
      <% if ("embalaje".equals(cola)){ %>
        <option selected>
      <% }else{ %>
        <option>
      <% } %>  
        embalaje</option>
      <% if ("entrega".equals(cola)){ %>
        <option selected>
      <% }else{ %>
        <option>
      <% } %>          
      entrega</option>
      <option>entregado</option>
    </select>
      <input name="cambiar_estado" type="submit" id="cambiar_estado" value="Cambiar"></td>
  </tr>
  </form>
  <% } // while %>
</table>
<p>&nbsp;</p>
</body>
</html>
