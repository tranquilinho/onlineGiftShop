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

<jsp:useBean id="temporada" scope="page" class="ogs.TemporadaBean" />
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
  &nbsp;<input name="pagina_actual" type="hidden" value="alta_articulos">
  Alta de art&iacute;culos
  <input name="volver" type="submit" id="volver" value="Volver">
  <input type="submit" name="salir" value="Salir">

<hr>
<table width="100%"  border="1">
  <tr>
    <th width="13%" scope="col"><div align="left">Tipo</div></th>
    <th width="87%" scope="col"><div align="left">
      <input name="tipo" type="text" id="tipo">
</div></th>
    </tr>
  <tr>
    <td>Nombre &nbsp;</td>
    <td> 
      <input name="nombre" type="text" id="nombre"></td>
    </tr>
  <tr>
    <td>Temporada</td>
    <td>&nbsp;
    <% Vector temporadas= (Vector) temporada.getTemporadas();
    Enumeration e=temporadas.elements();
    while (e.hasMoreElements()){
        ogs.TemporadaBean t=(ogs.TemporadaBean) e.nextElement();
        String nombre=t.getNombre();
    %>
    <%= nombre %> <input name="temporada" type="checkbox" id="temporada" value="<%= nombre %>">
    <% } %>
    </td>
  </tr>
  <tr>
    <td>P&uacute;blico</td>
    <td><select name="publico" id="publico">
        <option selected>infantil</option>
        <option>masculino</option>
        <option>femenino</option>
        <option>ancianos</option>
    </select>
</td>
  </tr>
  <tr>
    <td>Precio</td>
    <td><input name="precio" type="text" id="precio"></td>
  </tr>


</table>
<input name="alta_articulo" type="submit" id="alta_articulo" value="Dar de alta">
</form>
<p>&nbsp;</p>
</body>
</html>
