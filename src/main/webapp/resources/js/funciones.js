function busquedaLogin() {
	login=document.getElementById("login").value;
	clave=document.getElementById("clave").value;

	activaAjax("html","application/x-www-form-urlencoded",
			"/prfinal/login?login="+login+"&clave="+clave,null,loginC,problemas);

}

//Envia la peticion de login.
function loginC(datos){

	document.getElementById("errorLogin").innerHTML="";
	if (datos=="registro")
		document.getElementById("errorLogin").innerHTML="Login o clave no validos.";
	else
		window.location.reload();


} 

//Envia la peticion de registro.
function busquedaRegistro() {
	login=document.getElementById("login2").value;
	clave=document.getElementById("clave2").value;
	email=document.getElementById("email").value;
	nombre=document.getElementById("nombre").value;

	activaAjax("html","application/x-www-form-urlencoded",
			"/prfinal/registrar?login="+login+"&email="+email+"&nombre="+nombre+"&clave="+clave,
			null,registro,problemas);

}

//Funcion que muestra si el registro ha sido correcto o no.
//Si es correcto recarga la pagina para que muestre la pagina donde estaba pero ya logueado.
function registro(datos){

	document.getElementById("errorRegistro").innerHTML="";
	if (datos=="registro")
		document.getElementById("errorRegistro").innerHTML="Login o email duplicados.";
	else
		window.location.reload();

} 


function cargarPartidas() {

	activaAjax("html","application/x-www-form-urlencoded",
			"/prfinal/partidas",
			null,listaPartidas,problemas);
}

//Funcion que muestra el listado de partidas disponibles.
function listaPartidas(datos){
	var partidas = eval ("("+datos+")");
	var textoHTML="<table class='table'>"
		textoHTML+="<tr><th>Id</th><th>Creador</th><th>Jugadores</th><th> </th></tr>"		   
			for (var i=0; i<partidas.length; i++){
				textoHTML+="<tr>"
					textoHTML+="<td>"+partidas[i].id+"</td>"
					textoHTML+="<td>"+partidas[i].nombre+"</td>"
					textoHTML+="<td>"+partidas[i].jugadores+"</td>"
					textoHTML+="<td><input type='button' class='btn-success' value='Unirse' onclick='unirsePartida(&quot;"+partidas[i].id+"&quot;);'></td>"
					textoHTML+='</tr>'

			}
	textoHTML+='</table>'

		document.getElementById("lPartidas").innerHTML=textoHTML;

}

function crearPartida() {

	activaAjax("html","application/x-www-form-urlencoded",
			"/prfinal/partidas/crear",
			null,respuestaPartida,problemas);
}
//Funcion para crear una partida.
function respuestaPartida(datos){
	document.getElementById("errorPartida").innerHTML="";
	if (datos=="")
		window.location.reload();
	else
		document.getElementById("errorPartida").innerHTML=datos;

}

function unirsePartida(idPartida) {

	activaAjax("html","application/x-www-form-urlencoded",
			"/prfinal/partidas/unirse?idPartida="+idPartida,
			null,respuestaUnirse,problemas);
}

//Funcion para unirse a una partida
function respuestaUnirse(datos){
	document.getElementById("errorPartida").innerHTML="";
	if (datos=="OK")
		window.location = "/prfinal/partida";
	else
		document.getElementById("errorPartida").innerHTML=datos;

}


//Funciones que realizan la peticion AJAX
function activaAjax(data,contexto,direccion,datos,funcionOK,funcionErr){

	$.ajax({
		async:true,
		type: "POST",
		dataType: data,
		contentType: contexto,
		url:direccion,
		data:datos,
		beforeSend: inicioEnvio,
		success: funcionOK,
		timeout: 4000,
		error: funcionErr
	});
}

function inicioEnvio() {
	//no se hace nada
}
function problemas(datos) {
	$("#errores").text('Problemas en el servidor.'+datos);
}