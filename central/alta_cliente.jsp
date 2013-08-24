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
<title>Alta de cliente</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<form name="form1" method="post" action="/servidor_central/servlet/ServletCentral">
  &nbsp;<input name="pagina_actual" type="hidden" value="alta_cliente">
  Alta de cliente 
  <input name="volver" type="submit" id="volver" value="Volver">
  <input type="submit" name="salir" value="Salir">

<hr>
<table width="100%"  border="1">
  <tr>
    <th width="13%" scope="col"><div align="left">Login</div></th>
    <th width="87%" scope="col"><div align="left">
      <input name="login" type="text" id="login">
</div></th>
    </tr>
  <tr>
    <td>Nombre &nbsp;</td>
    <td> 
      <input name="nombre" type="text" id="nombre"></td>
    </tr>
  <tr>
    <td>Clave</td>
    <td>&nbsp;
      <input name="clave" type="text" id="clave"></td>
  </tr>
  <tr>
    <td>Correo electr&oacute;nico</td>
    <td><input name="correo" type="text" id="correo"></td>
  </tr>
  <tr>
    <td>Tarjeta </td>
    <td>N&uacute;mero: 
      <input name="tarjeta" type="text" id="tarjeta">
      . Caducidad: 
      <input name="caducidad" type="text" id="caducidad"></td>
  </tr>
  <tr>
    <td>Direcci&oacute;n</td>
    <td>Calle: 
      <input name="calle" type="text" id="calle">
      . Ciudad: 
      <input name="ciudad" type="text" id="ciudad">
      . CP: 
      <input name="cp" type="text" id="cp"></td>
  </tr>


</table>
<input name="alta_cliente" type="submit" id="alta_cliente" value="Dar de alta">
</form>
<p>&nbsp;</p>
</body>
</html>
