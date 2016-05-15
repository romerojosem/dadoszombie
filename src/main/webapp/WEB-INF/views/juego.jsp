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
    <link href='https://fonts.googleapis.com/css?family=Creepster' rel='stylesheet' type='text/css'>
    <link rel="icon" href="resources/favicon.ico">

    <title>Dados Zombie v1.0</title>

    <!-- Bootstrap core CSS -->
    <link href="resources/css/bootstrap.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="resources/css/bootstrap-theme.css" rel="stylesheet">

    
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/funciones.js"></script>	
    </head>  
  <body onload="cargarPartidas();setInterval(cargarPartidas, 5000)" role="document">
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
            <li><a href="/prfinal">Inicio</a></li>
            <li><a href="/prfinal/micuenta">Mi Cuenta</a></li>
            <li><a href="/prfinal/reglas">Reglas</a></li>
            <li class="active" > <a href="#"> Jugar </a></li>
            <li> <a href="/prfinal/salir"> Salir </a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
   <div class="container theme-showcase" role="main">

      <div class="jumbotron">
      
        <h2 style="font-family: 'Creepster', cursive;">Lista de partidas disponibles</h2>
		<div id="lPartidas"></div><br>
		<input type='button' class="btn-success" value='Crear Partida' onclick='crearPartida()'>

      </div>
    <div id="errorPartida"></div><br>
</div>


  </body>
</html>
