<%@ page contentType="text/html; charset=iso-8859-1"%>
<%@page import="java.util.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="sede" scope="page" class="ogs.SedeBean" />
<html>
<head>
<title>Men&uacute; sede central</title>
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
  &nbsp;
  <p>
    <input name="pagina_actual" type="hidden" value="menu_central">
  Usuario <%= usuario.getNombre() %>
  <input type="submit" name="salir" value="Salir">
</p>
  <hr>
  <p>
    <input name="gestion_usuarios" type="submit" id="gestion_usuarios" value="Gesti&oacute;n de usuarios">
  </p>
  <p>      Ver pedidos 
    <select name="sede" id="sede">
	<% Vector sedes=(Vector) sede.getSedes();
    Enumeration e=sedes.elements();
	while (e.hasMoreElements()){
	    ogs.SedeBean sb=(ogs.SedeBean) e.nextElement();
	%>
	<option><%= sb.getNombre() %></option>
	<% } %>
    </select>
    <input name="ver_pedidos" type="submit" id="ver_pedidos" value="Ver">
</p>
  <p>
    <input name="gestion_sedes" type="submit" id="gestion_sedes" value="Gesti&oacute;n de sedes locales">
</p>
  <p>&nbsp;   </p>
</form>
<p>&nbsp;</p>
</body>
</html>
