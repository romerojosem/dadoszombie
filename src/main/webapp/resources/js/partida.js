
function cargaJugadores() {

	activaAjax("html","application/x-www-form-urlencoded",
			"/prfinal/jugadores",
			null,listaJugadores,problemas);

}

//Funcion que muestra en listado de jugadores de la partida, 
//si esta en estado para jugar envia a la pagina para jugar.
function listaJugadores(datos){
	if(datos=="OK"){
		window.location.replace("/prfinal/jugada");
	}
	else{
		var jugadores = eval ("("+datos+")");
		var textoHTML="<table class='table'>"
			textoHTML+="<tr><th>Nombre</th><th>Turno</th></tr>"

				if(jugadores[0].nombre==1)
					$('#btnComenzar').show();
				else
					$('#btnComenzar').hide();

		for (var i=1; i<jugadores.length; i++){
			textoHTML+="<tr>"
				textoHTML+="<td>"+jugadores[i].nombre+"</td>"
				textoHTML+="<td>"+jugadores[i].turno+"</td>"
				textoHTML+='</tr>'
		}
		textoHTML+='</table>'

			document.getElementById("lJugadores").innerHTML=textoHTML;

	}


}

function comenzarPartida() {

	activaAjax("html","application/x-www-form-urlencoded",
			"/prfinal/comenzar",
			null,partida,problemas);
}

//Funcion para marcar la partida como activa, si no hay al menos 2 jugadores no empieza.
function partida(datos){

	if(datos=="OK"){

	}
	else
		document.getElementById("errorPartida").innerHTML="Faltan zombies para comenzar la partida.";


}

function cargaDatos() {

	activaAjax("html","application/x-www-form-urlencoded",
			"/prfinal/listaJugadores",
			null,datosJugadores,problemas);

}

//Funcion que muestra el listado de jugadores con sus puntos.
//Se encarga de mostrar los mensajes de la partida y los botones.
//Comprueba si hay algun jugador que haya ganado y en este caso,
//envia la señal de fin de partida.
function datosJugadores(datos){

	var jugadores = eval ("("+datos+")");
	var textoHTML="<table class='table'>"
		if(jugadores==""){
			//se termina la partida
			finalPartida();
		}
		else{
			//continuamos

			if ($("#nombreval").val()=="TIRADA"){
				$("#contBotones").hide();
				$("#resultado").hide();
			}
			else if ($("#nombreval").val()=="MUERTO"){

				$("#contBotones").hide();
				$("#resultado").show();
			}
			else if ($("#nombreval").val()=="GANADOR"){

				$("#contBotones").hide();
				$("#resultado").show();
			}
			else{

				if(jugadores[0].nombre==jugadores[0].login){
					$("#contDados").show();
					$("#contBotones").show();
					$("#puntJugador").show();
					$("#resultado").html("");
				}
				else{
					$("#contBotones").hide();
					$("#puntJugador").hide();
					$("#resultado").html("ESPERANDO A QUE COMAN LOS DEMAS ZOMBIES");
					$("#resultado").show();
				}
			}

			textoHTML+="<tr><th>Nombre</th><th>Cerebros</th></tr>"		   
				for (var i=1; i<jugadores.length; i++){
					textoHTML+="<tr>"
						textoHTML+="<td>"+jugadores[i].nombre+"</td>"
						textoHTML+="<td>"+jugadores[i].puntos+"</td>"
						//compruebo si hay algun jugador que haya ganado
						if(jugadores[i].puntos>12){
							//ha ganado
							$("#contBotones").hide();
							$("#resultado").html("!!!HAY UN GANADOR!!!");
							$("#nombreval").val("GANADOR");
							$("#resultado").show();
							setTimeout(function(){
								finalPartida();
								//$("#nombreval").val("");
							},3000);
						}
					textoHTML+='</tr>'
				}
			textoHTML+='</table>'

				document.getElementById("ljugadores").innerHTML=textoHTML;
		}
}

function tirarDados() {

	$("#nombreval").val("TIRADA");

	activaAjax("html","application/x-www-form-urlencoded",
			"/prfinal/tirada",
			null,tirada,problemas);

}

//Funcion que realiza una tirada y muestra el resultado.
//Si el usuario muere finaliza el turno.
function tirada(datos){

	var tirada = eval ("("+datos+")");
	$("#dado1").attr("src","resources/img/"+tirada[0]);
	$("#dado2").attr("src","resources/img/"+tirada[1]);
	$("#dado3").attr("src","resources/img/"+tirada[2]);
	$("#contBotones").hide();
	$("#resultado").hide();

	setTimeout(function(){
		$("#disparos").html("Disparos: "+tirada[3]);
		$("#cerebros").html("Cerebros: "+tirada[4]);
		if(tirada[3]>2){
			//ha muerto
			$("#resultado").html("!!!HAS MUERTO!!!");
			$("#nombreval").val("MUERTO");
			$("#resultado").show();
			setTimeout(function(){
				plantarse();
				$("#nombreval").val("");
			},3000);

		}

		else{
			$("#resultado").html("");
			$("#nombreval").val("");
			$("#resultado").hide();
		}
	}, 3000);



}

//Funcion que resetea los datos de la jugada.
//Envia a /plantarse para finalizar la jugada, guardar los puntos y pasar de jugador.
function plantarse() {
	$("#disparos").html("Disparos: "+0);
	$("#cerebros").html("Cerebros: "+0);
	$("#resultado").html("ESPERANDO A QUE COMAN LOS DEMAS ZOMBIES");
	$("#dado1").attr("src","resources/img/d0.gif");
	$("#dado2").attr("src","resources/img/d0.gif");
	$("#dado3").attr("src","resources/img/d0.gif");
	$("#contBotones").hide();
	$("#resultado").hide();
	activaAjax("html","application/x-www-form-urlencoded",
			"/prfinal/plantarse",
			null,nada,problemas);

}

//Funcion que envia al final de la partida para mostrar las puntuaciones.
function finalPartida() {

	window.location = "/prfinal/finalPartida";


}

function nada(datos){

}

