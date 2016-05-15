<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
            <li class="active"><a href="/prfinal">Inicio</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
   <div class="container theme-showcase" role="main">

      <div class="jumbotron">
      <img class="img-responsive" alt="Cabecera" src="resources/img/error.jpg">
      
	<c:if test="${not empty errMsg}">
		<h2>${errMsg}</h2>
	</c:if>
	</div>
</div>
</body>
</html>
