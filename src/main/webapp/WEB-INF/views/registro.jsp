<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="Dados Zombies">
    <meta name="author" content="Jose Manuel Romero">
    
    <link rel="icon" href="resources/favicon.ico">

    <title>Dados Zombie v1.0</title>

    <!-- Bootstrap core CSS -->
    <link href="resources/css/bootstrap.css" rel="stylesheet">
    <!--<link href="../../resources/css/bootstrap.css" rel="stylesheet">-->
    <!-- Bootstrap theme -->
    <link href="resources/css/bootstrap-theme.css" rel="stylesheet">
	<!--<link href="../../resources/css/bootstrap-theme.css" rel="stylesheet">-->
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    
    
    <link href='https://fonts.googleapis.com/css?family=Creepster' rel='stylesheet' type='text/css'>
    
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/funciones.js"></script>	
  </head>

  <body role="document">
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>

        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li ><a href="/prfinal">Inicio</a></li>
            <li class="active"><a href="/prfinal/micuenta">Mi Cuenta</a></li>
            <li ><a href="/prfinal/reglas">Reglas</a></li>
            <li><a href="/prfinal/juego">Jugar</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
   <div class="container theme-showcase" role="main">
	<div class="col-md-6 col-md-offset-3" >
      <div class="jumbotron">
      
        <h2 style="font-family: 'Creepster', cursive;">Conviertete en un Zombie y come Cereeebros</h2>
        
        
			 <h3 style="font-family: 'Creepster', cursive;">Introduzca login y clave</h3> 
			 <label for="login">Login:</label><input	type="text" id="login" name="login" required class="form-control"> <br> 
			 <label for="clave">Clave:</label><input type="password" id="clave" name="clave" required class="form-control"> <br>
			 <input type='button' class="btn-success" onclick="busquedaLogin()" value='Entrar' style="width: 84px; height: 33px" >
			 
		
			<h3 id="errorLogin"></h3>
			
		
			 <h3 style="font-family: 'Creepster', cursive;">Si no eres un zombie puedes registrarte aqui.</h3> 
			 <label for="login2">Login:</label><input type="text" id="login2" name="login2" required class="form-control"> <br> 
			 <label for="clave2">Clave:</label><input type="password" id="clave2" name="clave2"  required class="form-control"> <br>
			 <label for="nombre">Nombre:</label><input type="text" id="nombre" name="nombre" required class="form-control"> <br>
			 <label for="email">Email:</label><input type="email" id="email" name="email" required class="form-control"> <br>
			 <input type='button' class="btn-success"  onclick="busquedaRegistro()" value='Registrar' style="width: 84px; height: 33px">
		
 			<h3 id="errorRegistro"></h3>
		</div>
      </div>
    
    
    
</div>
<div id="errores"></div>
  
  </body>
</html>
