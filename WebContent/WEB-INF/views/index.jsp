<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<meta charset="ISO-8859-1">
<style>
body {
  background-color: #b8adad;
}

.container {
  display: block;
  position: relative;
  padding-left: 35px;
  margin-bottom: 12px;
  cursor: pointer;
  font-size: 22px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

.container input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}

.checkmark {
  position: absolute;
  top: 0;
  left: 0;
  height: 25px;
  width: 25px;
  background-color: #eee;
}

.container:hover input ~ .checkmark {
  background-color: #ccc;
}

.container input:checked ~ .checkmark {
  background-color: #2196F3;
}

.checkmark:after {
  content: "";
  position: absolute;
  display: none;
}

.container input:checked ~ .checkmark:after {
  display: block;
}

.container .checkmark:after {
  left: 9px;
  top: 5px;
  width: 5px;
  height: 10px;
  border: solid white;
  border-width: 0 3px 3px 0;
  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}
</style>
<body>

	Questa � la pagina di ingresso al tuo Quiz Online<br>
	<br>
	Seleziona da quale libro prendere le domande: <br>
	
<<<<<<< HEAD
	<label class="container"><input type="checkbox">OCA Oracle Certified Associate Java SE 8 [2014]
		<span class="checkmark">
		</span>
	</label>
=======
	<form action="#" method="post">
		<input type="checkbox" id="libro1" name="libro1" value="oca_manual"> OCA Oracle Certified Associate Java SE 8 [2014] <br>
		<input type="checkbox" id="libro2" name="libro2" value="oca_certification_guide_manning">  OCA Java SE 8 Programmer I Certification Guide <br><br>
>>>>>>> refs/remotes/origin/Progetto_Dinal
	
	<label class="container"><input type="checkbox">OCA Java SE 8 Programmer I Certification Guide
		<span class="checkmark">
		</span>
	</label>
	
	Totale domande caricate : ${ totDomande }<br>
	Tempo disponibile : ${ totDomande * 2 } minuti<br>
	<br>
<<<<<<< HEAD
	<form action="./domanda/0" method="get">
	<button type="button" class="btn btn-info">START</button>
=======
	
		<input type="submit" value="CARICA">
>>>>>>> refs/remotes/origin/Progetto_Dinal
	</form>

</body>
</html>