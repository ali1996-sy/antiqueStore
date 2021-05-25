import {
	RedirectUrl
} from "./Router.js";
import Navbar from "./Navbar.js";
import callAPI from "../utils/api.js";
import PrintError from "./PrintError.js";
import autocomplete from "./AutoComplete.js";
const API_BASE_URL = "/api/users/";
var countries = require("./data/countries.json");
var postCode = require("./data/postcode.json");

let registerPage = `  <div class="container " id="registerPage">
<h3 class="registerPage">Inscription</h3>
<div class="bg-dark rounded-lg" style="margin:auto; padding: 1em;">
<form action="#" method="POST" id="form-register" source="custom" name="form" class="">
  <div class="form-row">
	<div class="col">
	  <label for="email" id="label">Mail:</label>
	  <input type="email" placeholder="Entrez votre adresse mail" id="email" name="email" class="form-control" required=""
		pattern="^\\w+([.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,4})+\$">
	</div>
	

  </div>
  <div class="form-row">
  <div class="col">
  <label for="pseudo" id="label">Pseudo:</label>
  <input type="text" placeholder="Entrez votre pseudo" id="username" name="username" class="form-control"
	required="required">
</div>
  </div>
  <div class="form-row">
	<div class="col">
	  <label for="fname" id="label">Prénom:</label>
	  <input type="text" placeholder="Entrez votre prénom" id="fname" name="first name" class="form-control"
		required="required">
	</div>
	<div class="col">
	  <label for="lname" id="label">Nom:</label>
	  <input type="text" placeholder="Entrez votre nom" id="lname" name="lastname" class="form-control" required="">
	</div>
  </div>
  <div class="form-row">
	<div class="col">
	  <label for="street" id="label">Rue:</label>
	  <input type="text" placeholder="Entrez votre rue" id="street" name="street" class="form-control" required="">
	</div>
  </div>

  <div class="form-row">
	<div class="col">
	  <label for="nstreet" id="label">Numéro:</label>
	  <input type="text" placeholder="Entrez votre numero de boîte" id="nstreet" name="building-number"
		class="form-control" required="required">
	</div>
	<div class="col">
	  <label for="pcode" id="label">Code postal:</label>
	  <input type="text" placeholder="Entrez votre Code Postale" id="pcode" name="postCode" class="form-control"
		required="required" autocomplete="off">
	  <div id="auto-pcode"></div>
	</div>
  </div>
  <div class="form-row">
	<div class="col">
	  <label for="city" id="label">Ville:</label>
	  <input type="text" placeholder="Entrez votre ville" id="city" name="city" class="form-control" required="required"
		autocomplete="off">
	  <div id="auto-city"></div>
	</div>
	<div class="col">
	  <label for="country" id="label">Pays:</label>
	  <input type="text" placeholder="Entrez votre pays" id="country" name="country" class="form-control"
		required="required">
	</div>
  </div>
  <div class="form-row">
	<div class="col">
	  <label for="password" id="label">Mot de passe:</label>
	  <input type="password" placeholder="Entrer le mot de passe" id="cpassword" name="password" required="required"
		class="form-control">
	</div>

  </div>
  <div class="form-row">
  <div class="col">
  <label for="confirm-password" id="label">Confirmer mot de passe:</label>
  <input type="password" placeholder="Confirmer le mot de passe" id="cpassword" name="password"
	required="required" class="form-control">
	</div>
  </div>
  <div class="form-row">
  <div class="col">
  <br>
  <input class="btn btn-primary btn-block" type="submit" value="Enregistrer" id="btn " value="Input">
  </div>
  </div>
</form></div></div>
`;

const RegisterPage = () => {
	let listSearch = document.getElementById("searchList");
	let myOptions = document.getElementById("myOptions");
	let option = document.getElementById("options");
	let visitRequest = document.getElementById("visitRequest");
	let forSale = document.getElementById("forSale");
	let carousel = document.getElementById("carousel");
	carousel.innerHTML = "";
	listSearch.innerHTML = "";
	myOptions.innerHTML = "";
	option.innerHTML = "";
	visitRequest.innerHTML = "";
	forSale.innerHTML = "";
	let page = document.querySelector("#page");
	document.getElementById("search").innerHTML="";
	page.innerHTML = "";
	page.innerHTML = registerPage;
	let registerForm = document.getElementById("form-register");
	let input = document.getElementById('city')
	autocomplete(input, countries);
	let post = document.getElementById('pcode')
	autocomplete(post, postCode);
	registerForm.addEventListener("submit", onRegister);
};

const onRegister = async (e) => {
	e.preventDefault();
	let user = {
		username: document.getElementById("username").value,
		password: document.getElementById("cpassword").value,
		lastName: document.getElementById("lname").value,
		firstName: document.getElementById("fname").value,
		street: document.getElementById("street").value,
		buildingNumber: document.getElementById("nstreet").value,
		country: document.getElementById("country").value,
		city: document.getElementById("city").value,
		postCode: document.getElementById("pcode").value,
		confirmPassword: document.getElementById("cpassword").value,
		photo: "PhotoTest",
		email: document.getElementById("email").value
	};

	try {
		let message = await callAPI(
			API_BASE_URL + "register",
			"POST",
			undefined,
			user
		);
			
		onUserRegistration("vous êtes bien inscrit");
	} catch (err) {
		console.error(err)
		PrintError({
			message: err
		});
	}
};

const onUserRegistration = (message) => {

	RedirectUrl("/");
	PrintError({
		message: message
	}, "green");
	// re-render the navbar for the authenticated user
	Navbar();
};

export default RegisterPage;