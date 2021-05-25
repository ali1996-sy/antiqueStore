const API_Furniture_URL = "/api/furniture/";
import {
  RedirectUrl
} from "./Router.js";
import callAPI from "../utils/api.js";
const API_BASE_URL = "/api/users/";
import autocomplete from "./AutoComplete.js";
import Search from "./Search.js";
import {
  getUserLocalData,
  getUserSessionData
} from "../utils/session.js";
  var furnitureTypes = require("./data/furnitureTypes.json")[0];
var furnitures = [];
let furnitureHtml = "";

let forSaleFurnitures = async () => {
	let listSearch = document.getElementById("searchList");
	let myOptions = document.getElementById("myOptions");
	let option = document.getElementById("options");
	let visitRequest = document.getElementById("visitRequest");
	let carousel = document.getElementById("carousel");
	carousel.innerHTML = "";
	listSearch.innerHTML = "";
	myOptions.innerHTML = "";
	option.innerHTML = "";
	visitRequest.innerHTML = "";
	 if (!document.getElementById('searchBar'))
    Search();
	var price = "";
	var currentUserId = "";

	var user = getUserSessionData();
	  if (!user) user = getUserLocalData();

	var forSale = document.querySelector("#forSale");
	
let users = [];
 	if(user){
		
	 try {
      users = await callAPI(API_BASE_URL + "all", "GET", user.token, null);
    } catch (err) {
      console.error("Get all users : " + err);
    }

	if (user) {
		try {
			furnitures = await callAPI(API_Furniture_URL + "allForSaleFurniture", "GET", user.token, null);
		} catch (error) {
			console.error("Get all forSale furniture error : " + error);
		}

	}
	furnitures = Array.from(furnitures.allFurnituresForSale);
	 furnitures.sort(function(a,b){
      return (a.description - b.description) ;
 	});
	users = Array.from(users.allUsers);
	var userHtml = "";
	users.sort(function(a,b){
    	return (a.id - b.id);
	});
	users.forEach((e)=>{	
		userHtml +=`
		    <a class="dropdown-item bg-light text-danger text-justify" name='userId' id="${e.id}" value='${e.id} ' ><span id="sortTable" class="text-primary font-weight-bold">${e.id}</span> | <span class="text-primary font-weight-bold"> ${e.username} </span>| <span class="text-primary font-weight-bold">${e.firstName} ${e.lastName}</span></a>
		  `;});



	furnitures.forEach((e) => {
		
		furnitureHtml += ` 
		<tr">
		<td class="">${e.description}</td>
		<td class="">${furnitureTypes[e.furnitureType]}</td>
		<td class="">
		<div class="dropdown">

		  <button class="btn btn-sm btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		   	Client
		  </button>

		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		  <a class="dropdown-item bg-light text-danger text-justify" name='userId' id="8" value='8 ' >  <span class="text-primary font-weight-bold">-|en magasin</span></a>

		${userHtml} 
		</div>
		</div>
		</td>
		<td class="">
				<div class="input-group input-group-sm mb-3">
				<span class="input-group-text">€</span>
				<input type="text" id="price" name="price" class="" value ="${e.sellingPrice}" class="form-control" >
				<div class="input-group-append">
				
				</div></td>
		<td ><button type="button" name="forSaleButton" class="btn btn-success btn-sm" id="validateButton" value="${e.furnitureId}">Confirmer </button></td>
		</tr>`;

	});

	let html = `
	
	<table class="table table-dark table-responsive w-50" id="format-list">
	<thead class="thead-dark">
	<tr>
	<th scope="col">Description</th>
	<th scope="col">Type</th>
	<th scope="col">Acheteur</th>
	<th scope="col">Prix de vente</th>
	<th scope="col">Confirmé la vente</th>
	
	</tr>
	</thead>
	<tbody>
	${furnitureHtml}
	</tbody>
	</table>
	`;
	forSale.innerHTML = html;

	var listInput = document.getElementsByName('price');
	listInput.forEach((e) => {
			e.addEventListener('click', function () {
				price = e.value;
			});
	});
	listInput.forEach((e) => {
			e.addEventListener('input', function () {
				price = e.value;
			});
	});

	var listUsers = document.getElementsByName('userId');

	listUsers.forEach((e) => {
			e.addEventListener('click', (event) => {
				e.parentElement.parentElement.children[0].innerText = e.textContent.split('|')[1];
				
			  currentUserId = e.id;
			 
			});

	}); 

	var listForSale = document.getElementsByName('forSaleButton');

	listForSale.forEach((e) => {
		
		e.addEventListener('click', function () {
			
			let update = {
				furnitureId: e.value,
				state: 'saled',
				userId : currentUserId,
				price : e.parentElement.parentElement.children[3].children[0].children[1].value
			}
			try {
				
				callAPI(API_Furniture_URL + "saleFurniture", "POST", user.token, update);
			} catch (error) {
				console.error("Update furniture error : " + error);
			}
			location.reload();
			
		});
	});
		
}

};

export default forSaleFurnitures;
