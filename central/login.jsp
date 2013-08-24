<%@ page contentType="text/html; charset=iso-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <title>Identificaci&oacute;n usuario</title>
    </head>
    <body>

    <p><% String mensaje = (String) request.getSession().getAttribute("mensaje");
		if(mensaje != null){ %>
		<%= mensaje %>
		<% request.getSession().removeAttribute("mensaje");
                } %> &nbsp;</p>
    
    <form name="form1" method="post" action="/servidor_central/servlet/ServletLogin">
        <input name="pagina_actual" type="hidden" value="login">
      <p>Login: 
        <input name="login" type="text" id="login">
</p>
      <p>Contrase&ntilde;a: 
        <input name="password" type="text" id="password"> 
      </p>
      <p>
        <input name="entrar" type="submit" id="entrar" value="Entrar"> <input name="nuevo_usuario" type="submit" id="nuevo_usuario" value="Nuevo usuario">    
        </p>
    </form>

    </body>
</html>
