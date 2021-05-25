import {
	RedirectUrl
} from "./Router.js";
import callAPI from "../utils/api.js";
const API_OPTION_URL = "/api/option/";
import parseJwt from "../utils/utils.js";
import {
	getUserSessionData
} from "../utils/session.js";
import Search from "./Search.js";

function dateDiff(date1, date2) {
	var diff = {}
	var tmp = date2 - date1;
	tmp = Math.floor(tmp / 1000);
	diff.sec = tmp % 60;
	tmp = Math.floor((tmp - diff.sec) / 60);
	diff.min = tmp % 60;
	tmp = Math.floor((tmp - diff.min) / 60);
	diff.hour = tmp % 24;
	tmp = Math.floor((tmp - diff.hour) / 24);
	diff.day = tmp;
	return diff;

}
let Options = async () => {
	let listSearch = document.getElementById("searchList");
	let myOptions = document.getElementById("myOptions");
	let visitRequest = document.getElementById("visitRequest");
	let forSale = document.getElementById("forSale");
	let carousel = document.getElementById("carousel");
	carousel.innerHTML = "";
	listSearch.innerHTML = "";
	myOptions.innerHTML = "";
	visitRequest.innerHTML = "";
	forSale.innerHTML = "";
	 if (!document.getElementById('searchBar'))
    Search();
	let options = [];
	var user = getUserSessionData();
	if (!user) user = getUserLocalData();
	var tokenDecode = parseJwt(user.token);
	if (tokenDecode.administrator) {
		var option = document.getElementById("options");
		let optionHtml = "";
		option.innerHTML = "";

		if (user) {
			try {
				options = await callAPI(API_OPTION_URL + "all", "GET", user.token, null);
			} catch (error) {
				console.error("Get all options error : " + error);
			}

		}
		options = Array.from(options.allOptions);

	 		options.sort(function(a,b){
      		return (b.state- a.state) ;
 		});
		var today = new Date();
		var diffDay;
		options.forEach((e) => {
		
			optionHtml += ` 
			<tr">
			<td class="text-primary font-weight-bold align-middle border border-5">${e.buyerName}</td>
			<td class="align-middle text-justify border border-5">${e.description}</td>`;
			diffDay = dateDiff(today, new Date(e.termDate));

			if (e.state == "pending" && (e.termDate - today) <= 0) {
				let update = {
					optionId: e.optionId,
					furniture: e.furniture,
					furnitureState: 'for_sale',
					state: 'finished'
				}
				try {
					callAPI(API_OPTION_URL + "update", "POST", user.token, update);
				} catch (error) {
					console.error("Update option error : " + error);
				}
			}
			if (e.state == "pending") {
				optionHtml += `<td class="bg-success font-weight-bold align-middle text-center border border-5" style="width:25%">En attente</td>`;
			} else if (e.state == "cancelled") {
				optionHtml += `<td class="bg-danger font-weight-bold  align-middle text-center border border-5" style="width:25%">Annulé</td>`;
			} else {
				optionHtml += `<td class="bg-primary font-weight-bold align-middle text-center border border-5" style="width:25%">Fini</td>`;
			}
			if (e.state == "pending") {
				optionHtml += `
			<td class="align-middle  text-center border border-5">${diffDay.day} jour(s) et ${diffDay.hour}h</td>`;
			}
			if (e.state == "cancelled" || e.state == "finished") {
				optionHtml += `
			<td class="align-middle  text-center border border-5">0 jour(s)</td>`;
			}

			if (e.state != 'cancelled' && e.state != 'finished') {
				optionHtml += `<td class = "bg-dark font-weight-bold align-middle border border-danger border-5" id="customButton"><button type="button" name="canceledButton" id="${e.furniture}" class="customButton btn align-middle btn-primary btn-sm bg-danger" style="width:25%"  value="${e.optionId}">Annuler </button></td>`;
			} else {
				optionHtml += `<td class="bg-dark border border-danger border-5"></td>`;
			}
			if (e.state == 'pending' && !e.antiqueDealer) {
				optionHtml += `<td class = "bg-dark  align-middle border border-success border-5" ></div><button type="button" name="validateButton" title="${e.buyer}" id="${e.furniture}" class="customButton btn btn-sm float-left btn-primary  bg-success"   value="${e.optionId}">Confirmer vente </button></td>`;
			} else if (e.antiqueDealer && e.state == 'pending') {
				optionHtml += `<td class = "bg-dark  align-middle border border-success border-5" >
				<span class="font-weight-bold align-middle ">Prix spéciale antiquaire : </span>
				<div class="input-group mb-3">
				<input type="text" id="antiquePrice" name="antiquePrice" class="form-control" >
				<div class="input-group-append">
				<span class="input-group-text">€</span>
				</div>
				</div><button type="button" name="validateButton" id="${e.furniture}" class="customButton btn btn-sm float-left btn-primary  bg-success" title="${e.buyer}" value="${e.optionId}">Confirmer vente </button></td>`;
			} else {
				optionHtml += `<td class="bg-dark border border-success border-5"></td>`;
			}
			optionHtml += `</tr>`;

		});
		

		let html = `
		<table class="table table-dark w-75" id="format-list">
		<thead class="thead-dark text-center ">
		<tr>
		<th class="align-middle border border-5" scope="col">Acheteur</th>
		<th class="align-middle border border-5" scope="col">Description</th>
		<th class="align-middle border border-5" scope="col">Etat</th>
		<th class="align-middle border border-5" scope="col">Durée de l'option (actuel)</th>
		<th class="align-middle bg-dark align-middle text-danger border border-danger border-5" scope="col">Annuler l'option</th>
		<th class="align-middle bg-dark align-middle text-success border border-danger border-5" scope="col">Confirmer la vente</th>
		</tr>
		</thead>
		<tbody>
		${optionHtml}
		</tbody>
		</table>
		`;
		option.innerHTML = html;

		var listInput = document.getElementsByName('antiquePrice');
		var listValidateAntiquaire = document.getElementsByName('validateButton');

		var price = "";

		listInput.forEach((e) => {
			e.addEventListener('input', function () {
				price = e.value;
			});
		});

		listValidateAntiquaire.forEach((a) => {

			a.addEventListener('click', function () {

				let update = {
					optionId: a.value,
					furniture: a.id,
					furnitureState: 'saled',
					optionState: 'finished',
					antiquePrice: price,
					buyerId:a.title
				}
				try {
					callAPI(API_OPTION_URL + "saledWithSpecialPrice", "POST", user.token, update);
					RedirectUrl("/options");
				} catch (error) {
					console.error("Update option error : " + error);
				}

			});
		});



		var listCanceled = document.getElementsByName('canceledButton');

		listCanceled.forEach((e) => {

			e.addEventListener('click', function () {
				
				let update = {
					optionId: e.value,
					furniture: e.id,
					furnitureState: 'for_sale',
					state: 'cancelled'
				}
				try {
					callAPI(API_OPTION_URL + "update", "POST", user.token, update);
					RedirectUrl("/options");
				} catch (error) {
					console.error("Update option error : " + error);
				}

			});
		});



	}
}
export default Options;