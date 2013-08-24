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
<html>
<head>
<title>Pedidos sede</title>
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

<% String sede = (String) request.getSession().getAttribute("sede"); %>

<form name="form1" method="post" action="/servidor_central/servlet/ServletCentral">
  &nbsp;<input name="pagina_actual" type="hidden" value="pedidos_sede">
  Sede <%= sede %>
  <input name="volver" type="submit" id="volver" value="Volver">
  <input type="submit" name="salir" value="Salir">
</form>
<hr>
<% Vector pedidos=(Vector) pedido.getPedidos(null);
    Enumeration e=pedidos.elements();
    
%>
<table width="100%"  border="1">
  <tr>
    <th scope="col">Fecha</th>
    <th scope="col">Direccion</th>
    <th scope="col">Art&iacute;culos</th>
    <th scope="col">Precio</th>    
  </tr>
  <% while (e.hasMoreElements()){
    ogs.PedidoBean pb=(ogs.PedidoBean) e.nextElement();
  %>
  <tr>
    <td><%= pb.getFecha() %> &nbsp;</td>
    <td><%= pb.getDireccion() %> &nbsp;</td>
    <td>
    <% Vector articulos=(Vector) pb.getArticulos();
        Enumeration a=articulos.elements();
    %>
    <ul>
        <% while (a.hasMoreElements()){
            ogs.ArticuloBean ab= (ogs.ArticuloBean) a.nextElement();
        %>
      <li><%= ab.getCantidad() %> <%= ab.getNombre() %> - <%= ab.getEstado() %></li>
      <% } %>
   </ul></td>
       <td><%= pb.getPrecio() %> &nbsp;</td>
  </tr>
  </form>
  <% } // while %>
</table>
<p>&nbsp;</p>
</body>
</html>
