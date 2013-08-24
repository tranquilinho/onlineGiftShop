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
<title>Compra artículo</title>
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
    <input name="pagina_actual" type="hidden" value="compra_articulo">
    <input name="articulo" type="hidden" value="<%= request.getSession().getAttribute("articulo") %>">
  Usuario<%= usuario.getNombre() %>
  <input name="cancelar" type="submit" id="cancelar" value="Cancelar">
  <input type="submit" name="salir" value="Salir">
</p>
  <p>Art&iacute;culo: <%= request.getSession().getAttribute("articulo") %></p>
  <p>&iquest;Cu&aacute;ntas unidades desea comprar? 
    <input name="unidades" type="text" id="unidades">
    <input name="comprar" type="submit" id="comprar" value="Comprar">
</p>
</form>
</body>
</html>
