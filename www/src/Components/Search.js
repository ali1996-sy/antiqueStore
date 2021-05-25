const API_User_URL = "/api/users/";
const API_Furniture_URL = "/api/furniture/";
import callAPI from "../utils/api.js";
import PrintError from "./PrintError.js";

import {
	getUserSessionData,
	getUserLocalData
} from "../utils/session.js";
import autocomplete from "./AutoComplete.js";
import CustomerLoader from "./CustomerLoader.js";
import {ListFurniture }from "./listFurniture.js";
import UserListPage from "./UserListPage.js";
import parseJwt from "../utils/utils.js";
var countries = require("./data/countries.json");
var postCode = require("./data/postcode.json");
var customers = [];
var furnitureTypes = require("./data/furnitureTypes.json")[0];
var furnitureTypesFr = require("./data/furnitureTypesFr.json")[0];


let Search = async () => {
	var user = getUserSessionData();
	if (!user) user = getUserLocalData();

	var search = "";
	let client = "";
	let element = document.getElementById("search");

	
	if (user) {

		var tokenDecode = parseJwt(user.token);

		customers = await CustomerLoader();

		try {
			var types = await callAPI(
				API_Furniture_URL + "types",
				"GET",
				user.token, null
			);
		} catch (err) {
			console.error("Search-furniture", err);
			PrintError(err);
		}

		types = Array.from(types.get);
		let typesHtml = "";
		types.forEach(element => {
			typesHtml += `<option value="${element}">${furnitureTypes[element]}</option>`;
		});


		if (tokenDecode.administrator) {
			client = `Client<input type="text" class="form-control" placeholder="Nom de client" id="customer-ft"></input>`;
		}
		let html = `

		<div id="searchBar" class="text-white">
		<div class="card bg-secondary" style="width: 250px;">
		<h5 class="p-3 mb-2 bg-dark">Recherche meuble</h5>
		<form id="" autocomplete="off">
		Prix
		<input type="text" class="form-control" placeholder="Prix min" id="min"> à
		<input type="text" class="form-control" placeholder="Prix max" id="max">
		
		${client}
		Choisir Type
		<select   id ="select-type" data-role="tagsinput">
		<option>${typesHtml}</option>
		
		</select>
		<div id="types" data-role="tagsinput" ></div>
		<input type="button"  id ="submit-furniture" class="form-control" value="Rechercher" >
		</form>

		`;
		if (tokenDecode.administrator) {
			html += `<h5 class="p-3 mb-2 bg-dark">Recherche client</h5>
			<form id="submit-customer" autocomplete="off">
			Ville
			<input type="text" class="form-control" placeholder="Ville" id="ville-ct"> 
			<div id="auto-ville-ct"></div>
			Code postal
			<input type="text" class="form-control" placeholder="Code postal" id="postCode-ct">
			<div id="auto-postCode-ct"></div>
			Client
			<input type="text" class="form-control" placeholder="Nom de client" id="customer-ct">
			<div id="auto-customer-ct"></div>
			<input type="submit" class="form-control" value="Rechercher">
			</form>
			</div>
			</div>`;
		}
		element.innerHTML = html;

		$('div[data-role=tagsinput]').tagsinput();
		document.getElementById('select-type').addEventListener('change', function (e) {
			$('div[data-role=tagsinput]').tagsinput('add', furnitureTypes[e.target.value]);
		})

		if(document.getElementById('submit-customer'))
		document.getElementById('submit-customer').addEventListener('submit', async (event) => {
			event.preventDefault();


			let postCode = document.getElementById("postCode-ct").value;
			let ville = document.getElementById("ville-ct").value;
			let customerName = document.getElementById("customer-ct").value;

			try {
				search = await callAPI(API_User_URL + "customerSearch", "POST", user.token, {
					postCode: postCode,
					ville: ville,
					customerName: customerName,
				});

				UserListPage({
					"allUsers": search.usersToSearch
				});
			} catch (err) {
				console.error("Search-customer", err);
				PrintError(err);
			}


		});

		document.getElementById('submit-furniture').addEventListener('click', async (event) => {
			event.preventDefault();

			let min = document.getElementById("min").value;
			let max = document.getElementById("max").value;
			let customer="";
			if (tokenDecode.administrator) {
			 customer = document.getElementById("customer-ft").value;
			}
			let selectedTypes = $("#types").tagsinput('items');
			var englishSelectedTypes = [];
			let count = 0;
			selectedTypes.forEach((e) => {
				englishSelectedTypes[count] = furnitureTypesFr[e];
				count++;
			});




			try {

				const search = await callAPI(
					API_Furniture_URL + "furnitureSearch",
					"POST",
					user.token, {
						max: max,
						min: min,
						customer: customer,
						types: englishSelectedTypes
					}
				);
				ListFurniture(search);
			} catch (err) {
				console.error("Search-furniture", err);
				PrintError(err);
			}
		});
	} else {
		let element = document.getElementById("search");
		if (element) element.innerHTML = "";
		return;
	}

	let ville = document.getElementById('ville-ct');
	if(ville)
	autocomplete(ville, countries);
	let post = document.getElementById('postCode-ct');
	if(post)
	autocomplete(post, postCode);
	let customerCt = document.getElementById('customer-ct');
	if(customerCt)
	autocomplete(customerCt, customers);
	let customerFt = document.getElementById('customer-ft');
	if(customerFt)
	autocomplete(customerFt, customers);

};

export default Search;