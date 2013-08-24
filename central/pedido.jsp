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
<jsp:useBean id="pedido" scope="page" class="ogs.PedidoBean" />
<jsp:useBean id="sede" scope="page" class="ogs.SedeBean" />
<html>
<head>
<title>Pedido</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<% if(request.getSession().getAttribute("err") != null){ %>
<p><%= request.getSession().getAttribute("err") %>
<% } %>

 <% if (request.getSession().getAttribute("usuario") == null){ 
       request.getSession().setAttribute("mensaje","Usuario no autenticado");
       RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
       if (dispatcher != null)
           dispatcher.forward(request, response);    
                
  }
  ogs.UsuarioBean usuario = (ogs.UsuarioBean) request.getSession().getAttribute("usuario");
%>

<form name="form1" method="post" action="/servidor_central/servlet/ServletCentral">
  &nbsp;
  <p>
    <input name="pagina_actual" type="hidden" value="pedido">
  Usuario <%= usuario.getNombre() %>
  <input name="terminar_pedido" type="submit" id="terminar_pedido" value="Elegir destino pedido">
  <input name="cancelar" type="submit" id="cancelar" value="Cancelar">
  <input type="submit" name="salir" value="Salir">
</p>
</form>
<hr>

<p align="center">
  <% String url_sede=(String)request.getSession().getAttribute("url_sede");
  ogs.ArticuloBean [] articulos_disponibles=articulo.getArticulosSede(url_sede);
    
    
%>
Art&iacute;culos disponibles en <%= request.getSession().getAttribute("sede") %></p>
<% if (articulos_disponibles != null) { %>
<table width="100%"  border="1">
  <tr>
    <th scope="col">Tipo</th>
    <th scope="col">Nombre</th>
    <th scope="col">Precio</th>
    <th scope="col">&nbsp;</th>
  </tr>
  <% for(int i=0; i< articulos_disponibles.length; i++){
        ogs.ArticuloBean ab=articulos_disponibles[i];
  %>
  <form name="ID" method="post" action="/servidor_central/servlet/ServletCentral">
    <input name="pagina_actual" type="hidden" value="pedido">
    <input name="articulo" type="hidden" value="<%= ab.getNombre() %>">
    <tr>
      <td><%= ab.getTipo() %> &nbsp;</td>
      <td><%= ab.getNombre() %> &nbsp;</td>
      <td><%= ab.getPrecio() %>&nbsp;</td>
      <td>          <input name="comprar" type="submit" id="comprar" value="Comprar"></td>
    </tr>
  </form>
  <% } // while %>
</table> 

<% } %>

<hr>
<%
    // crear cesta de la compra si no existe
    if(request.getSession().getAttribute("cesta") == null)
        request.getSession().setAttribute("cesta",new Vector());
%>
<div align="center">
  <% Vector cesta=(Vector) request.getSession().getAttribute("cesta");
    Enumeration e=cesta.elements();
    
%> 
  Cesta de compra
</div>
<table width="100%"  border="1">
  <tr>
    <th scope="col">Tipo</th>
    <th scope="col">Nombre</th>
    <th scope="col">Cantidad</th>    
    <th scope="col">Precio total </th>
  </tr>
  <% while (e.hasMoreElements()){
       ogs.ArticuloBean ab= (ogs.ArticuloBean) e.nextElement();
  %>
  <tr>
    <td><%= ab.getTipo() %>&nbsp;</td>
    <td><%= ab.getNombre() %> &nbsp;</td>
    <td><%= ab.getCantidad() %> </td>
    <td><%= ab.getCantidad() * ab.getPrecio() %> &nbsp;</td>
  </tr>
  <% } // while a
 %>
</table>
<p>&nbsp;</p>
</body>
</html>
