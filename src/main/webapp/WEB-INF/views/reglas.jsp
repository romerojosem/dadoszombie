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


  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="resources/js/bootstrap.min.js"></script>
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
            <li><a href="/prfinal/micuenta">Mi Cuenta</a></li>
            <li class="active"><a href="/prfinal/reglas">Reglas</a></li>
            <li><a href="/prfinal/juego">Jugar</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
   <div class="container theme-showcase" role="main">

      <div class="jumbotron">
      
        <h2 style="font-family: 'Creepster', cursive;">!Mmm! !Cereeebros!</h2>

<p>En tu turno, agita el cubilete con los dados dentro, coge tres dados sin mirarlos, y lanzalos. Cada dado es una victima humana.</p>
<p> Los rojos son las mas resistentes. Los verdes son las mas debiles, y los amarillos son las de resistencia media.</p>

<p>Los dados tienen tres simbolos:</p>

<p>Cerebro - Te zampas el cerebro de tu victima. Pon los dados con el Cerebro a tu izquierda. </p>

<p>Disparo - !Tu victima se ha defendido! Pon los dados con el Disparo a tu derecha.</p>

<p>Huellas - Tu victima ha escapado. Manten los dados con las Huellas delante de ti. Si escoges lanzar los dados de nuevo, volveras a lanzar estos dados, junto con otros nuevos, suficientes para llegar al total de tres.</p>
<p>!Cereeebros!</p>

<p>Continua jugando hasta que alguien llegue a los trece Cerebros. Entonces se finaliza la ronda. El jugador que tenga mas puntos al final de la ronda es el ganador.</p>

<div class="embed-responsive embed-responsive-16by9">
  <iframe class="embed-responsive-item" 
          src="https://www.youtube.com/embed/QwdmXbYouDE"
          >Video Reglas</iframe>
</div>
      </div>
    
    
    
</div>
    
  </body>
</html>
