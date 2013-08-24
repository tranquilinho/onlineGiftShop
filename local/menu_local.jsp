<%@ page contentType="text/html; charset=iso-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
  &nbsp;
  <p>
    <input name="pagina_actual" type="hidden" value="menu_local">
  Usuario <%= request.getSession().getAttribute("nombre_usuario") %>
  <input type="submit" name="salir" value="Salir">
</p>
  <hr>
  <p>
    <input name="ver_cola_embalaje" type="submit" id="ver_cola_embalaje" value="Ver cola de embalaje">
  </p>
  <p>      <input name="ver_cola_entrega" type="submit" id="ver_cola_entrega" value="Ver cola de entrega">
</p>
  <p>
    <input name="gestion_articulos" type="submit" id="gestion_articulos" value="Gesti&oacute;n de art&iacute;culos">
</p>
  <p>&nbsp;   </p>
</form>
<p>&nbsp;</p>
</body>
</html>
