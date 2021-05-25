import {
	RedirectUrl
} from "./Router.js";
import callAPI from "../utils/api.js";
const API_OPTION_URL = "/api/option/";
import parseJwt from "../utils/utils.js";
import {
	getUserSessionData,
	getUserLocalData
} from "../utils/session.js";
import Search from "./Search.js";
var options = [];

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

let MyOptions = async () => {
	 if (!document.getElementById('searchBar'))
    Search();
	let optionHtml = "";
	let html = "";
	let listSearch = document.getElementById("searchList");
	let option = document.getElementById("options");
	let visitRequest = document.getElementById("visitRequest");
	let forSale = document.getElementById("forSale");
	let carousel = document.getElementById("carousel");
	carousel.innerHTML = "";
	listSearch.innerHTML = "";
	option.innerHTML = "";
	visitRequest.innerHTML = "";
	forSale.innerHTML = "";

	var local = getUserSessionData();
	if (!local) local = getUserLocalData();
	var tokenDecode = parseJwt(local.token);


	if (local) {
		try {
			options = await callAPI(API_OPTION_URL + "all", "GET", local.token, null);
		} catch (error) {
			console.error("Get all options error " + error);
		}
		options = Array.from(options.allOptions);
		var today = new Date();
		var diffDay;

	 	options.sort(function(a,b){
      		return (a.state- b.state) ;
 		});
 		
		options.forEach((e) => {

			if (e.buyer == tokenDecode.user) {

				optionHtml += ` 
		<tr">
		<td class="align-middle border text-justify border-5">${e.description}</td>`;
				diffDay = dateDiff(today, new Date(e.termDate));
				if (e.state == "pending" && (e.termDate - today) <= 0) {
					let update = {
						optionId: e.optionId,
						furniture: e.furniture,
						furnitureState: 'for_sale',
						state: 'cancelled'
					}
					try {
						callAPI(API_OPTION_URL + "update", "POST", user.token, update);
					} catch (error) {
						console.error("Update option error : " + error);
					}
				}
				if (e.state == "pending") {
					optionHtml += `<td class="bg-success align-middle font-weight-bold text-center border border-5" style="width:25%">En attente</td>`;
				} else if (e.state == "cancelled") {
					optionHtml += `<td class="bg-danger align-middle  font-weight-bold text-center border border-5" style="width:25%">Annulé</td>`;
				} else {
					optionHtml += `<td class="bg-primary align-middle font-weight-bold text-center border border-5" style="width:25%">Fini</td>`;
				}
		if (e.state == "pending" ) {
				optionHtml += `
			<td class="align-middle  text-center border border-5">${diffDay.day} jour(s) et ${diffDay.hour}h</td>`;
			}
			if (e.state == "cancelled"|| e.state=="finished") {
				optionHtml += `
			<td class="align-middle  text-center border border-5">0 jour(s)</td>`;
			}
				if (e.state != 'cancelled' && e.state != 'finished') {
					optionHtml += `<td class = "bg-dark align-middle border border-danger border-5"><button type="button" name="canceledButtonMyOption" id="${e.furniture}" class="btn align-middle btn-primary btn-sm bg-danger" style="width:25%" id="validateButton" value="${e.optionId}">Annuler </button></td></tr>`;
				} else {
					optionHtml += `<td class="bg-dark border border-danger border-5"></td></tr>`;

				}
			}

		});

		html = `

	<table class="table table-dark " id="format-list">
	<thead class="thead-dark text-center ">
	<tr>
	<th class="align-middle border border-5" scope="col">Description</th>
	<th class="align-middle border border-5" scope="col">Etat</th>
	<th class="align-middle border border-5" scope="col">Durée de l'option (actuel)</th>
	<th class="align-middle bg-dark align-middle text-danger border border-danger border-5" scope="col">Annuler l'option</th>`;



		html += `</tr>
	</thead>
	<tbody>
	${optionHtml}
	</tbody>
	</table>
	`;


		var listCanceled = document.getElementsByName('canceledButtonMyOption');
		option.innerHTML = html;
		listCanceled.forEach((e) => {

			e.addEventListener('click', function () {
				let update = {
					optionId: e.value,
					furniture: e.id,
					furnitureState: 'for_sale',
					state: 'cancelled'
				}
				try {
					callAPI(API_OPTION_URL + "update", "POST", local.token, update);
					RedirectUrl("/myOptions");
				} catch (error) {
					console.error("Update option error :" + error);
				}

			});
		});
	}
}
export default MyOptions;