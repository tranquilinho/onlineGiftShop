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
<title>Menu usuario</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<% if(request.getSession().getAttribute("err") != null){ %>
<p><%= request.getSession().getAttribute("err") %>
<% request.getSession().removeAttribute("err");
} %>
 

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
    <input name="pagina_actual" type="hidden" value="menu_usuario">
  Usuario <%= usuario.getNombre() %> 
  <input type="submit" name="salir" value="Salir">
  <input name="baja_usuario" type="submit" id="baja_usuario" value="Darse de baja">
</p>
  <p>Ver articulos disponibles en sede 
    <select name="sede" id="sede">
	<% Vector sedes=(Vector) sede.getSedes();
    Enumeration s=sedes.elements();
	while (s.hasMoreElements()){
	    ogs.SedeBean sb=(ogs.SedeBean) s.nextElement();
	%>
	<option><%= sb.getNombre() %></option>
	<% } %>
    </select>

    <input name="ver_articulos_sede" type="submit" id="ver_articulos_sede" value="Ver">
  </p>
</form>
<hr>
<div align="center">
  <% Vector pedidos=(Vector) pedido.getPedidosUsuario(usuario.getLogin());
    Enumeration e=pedidos.elements();
    
%> 
Estado articulos comprados
</div>
<table width="100%"  border="1">
  <tr>
    <th scope="col">Tipo</th>
    <th scope="col">Nombre</th>
    <th scope="col">Estado</th>
    <th scope="col">Cantidad</th>    
    <th scope="col">Precio total </th>
  </tr>
  <% while (e.hasMoreElements()){
        ogs.PedidoBean pb=(ogs.PedidoBean) e.nextElement();
        Vector articulos=(Vector) pb.getArticulos();
        Enumeration a=articulos.elements();
        while (a.hasMoreElements()){
            ogs.ArticuloBean ab= (ogs.ArticuloBean) a.nextElement();
            // Tipo, Estado y precio se consiguen mediante el servicio de articulos
  %>
  <tr>
    <td><%= ab.getTipo() %>&nbsp;</td>
    <td><%= ab.getNombre() %> &nbsp;</td>
    <td><%= ab.getEstado() %> &nbsp;</td>
    <td><%= ab.getCantidad() %> </td>
    <td><%= ab.getCantidad() * ab.getPrecio() %> &nbsp;</td>
  </tr>
  <% } // while a
  } // while e %>
</table>
<p>&nbsp;</p>
</body>
</html>
