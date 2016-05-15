<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
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
<!-- Bootstrap theme -->
<link href="resources/css/bootstrap-theme.css" rel="stylesheet">
<link href='https://fonts.googleapis.com/css?family=Creepster' rel='stylesheet' type='text/css'>

<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/funciones.js"></script>
<script src="resources/js/partida.js"></script>



</head>
<body onload="cargaDatos();setInterval(cargaDatos, 5000)" role="document">
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>

			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="/prfinal/finalPartida"> Salir </a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		
	</nav>
	<div class="container theme-showcase" role="main">
		<div class="jumbotron">
        <br><br>
        <div class="row">
			<div class="col-md-4" align="left">
				<div style="font-family: 'Creepster', cursive;font-size:large;" id="ljugadores"><br></div>
			</div>

			<div class="col-md-4" align="center">
				<div id="contDados" style="height:250px;">
					<img height="100" width="100" id="dado1" src="resources/img/d0.gif">
					<img height="100" width="100" id="dado2" src="resources/img/d0.gif">
					<img height="100" width="100" id="dado3" src="resources/img/d0.gif">
				</div>
				<div style="font-family: 'Creepster', cursive;font-size:xx-large;" id="resultado">
					
				</div>
				<div id="contBotones">					
					<input id="btnTirar" type='button' value='Tirar' class="btn-success"
						onclick='tirarDados()'>
					<input type='button' value='Plantarse' class="btn-danger" 
						onclick='plantarse()'><br><br>
				</div>

			</div>
            <div class="col-md-4" align="right">
                <div style="font-family: 'Creepster', cursive;font-size:large;" id="puntJugador">
                	<div id="disparos"></div>
                	<div id="cerebros"></div>
                </div>
                
            </div>
			</div>
            
		</div>
		<input style="color: #FFFFFF;background-color: #FFFFFF;	border-width:0;" type="text" value="" id="nombreval">
	</div>

</body>
</html>
