import {
  RedirectUrl
} from "./Router.js";
import {
  getUserLocalData,
  getUserSessionData
} from "../utils/session.js";
import callAPI from "../utils/api.js";
import PrintError from "./PrintError.js";
import Search from "./Search.js";

const API_BASE_URL = "/api/users/";



var user = getUserSessionData();
const UserListPage = async (Searchedusers = null) => {
  let usersHtml = "";

let oldElement;
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
  document.querySelector("#page").innerHTML = "";
  if (!document.getElementById('searchBar'))
    Search();
  if (!user) user = getUserLocalData();
  var users = Searchedusers;
  if (!user) {
    RedirectUrl("/");
    PrintError({
      message: "Veuillez vous connecter."
    }, "red");
    return;
  }



  if (!Searchedusers) {
    try {
      users = await callAPI(API_BASE_URL + "all", "GET", user.token, null);
    } catch (err) {
      console.error("Get all users : " + err);
    }
  }
 


  users = Array.from(users.allUsers);
  users.sort(function(a,b){
      return (b.validateRegistration - a.validateRegistration) ;
  });
  var indice = 0;
  users.forEach((e) => {
    
    usersHtml += ` <tr name="userline" class="userline" id="${indice}">`;
    if (e.validateRegistration == false) {
      usersHtml += `

  <td class="userline bg-danger border border-3 border-light">${e.username}</td>
  <td class="userline bg-danger border border-3 border-light">${e.firstName}</td>
  <td class="userline bg-danger border border-3 border-light">${e.lastName}</td>
  <td class="userline bg-danger border border-3 border-light">${e.email}</td>
<td class="userline bg-danger border border-3 border-light">${e.buyFurnitureNumber}</td>
<td class="userline bg-danger border border-3 border-light">${e.sellFurnitureNumber}</td>

  `;
    } else {
      usersHtml += ` 

  <td class="userline border border-3 border-light bg-dark">${e.username}</td>
  <td class="userline border border-3 border-light bg-dark">${e.firstName}</td>
  <td class="userline border border-3 border-light bg-dark ">${e.lastName}</td>
  <td class="userline border border-3 border-light bg-dark">${e.email}</td>
  <td class="userline border border-3 border-light bg-dark">${e.buyFurnitureNumber}</td>
  <td class="userline border border-3 border-light bg-dark">${e.sellFurnitureNumber}</td>

  `;
    }
    usersHtml += ` </tr>`;
    indice++;
  })
  let html = `



<table class=" table table-dark bg-transparent " id="format-list" >
<thead class="thead-dark font-weight-bold ">

<tr>


<th scope="col">Pseudo</th>
<th scope="col">Prénom</th>
<th scope="col">Nom</th>
<th scope="col">Email</th>
<th scope="col" style="max-width: 100px;">meubles achetés</th>
<th scope="col" style="max-width: 100px;">meubles vendues</th>


</tr>
</thead>
<tbody>
${usersHtml}
</tbody>
</table>
<div id="view"></div>
`;
  let page = document.querySelector("#page");
  // clear the page
  page.innerHTML = html;
  var listUser = document.getElementsByName('userline');

  listUser.forEach((e) => {
    e.addEventListener('click', function () {
      let id = e.getAttribute("id");
      let element = document.getElementById(id);

      if (oldElement != null) {

        var top = document.getElementById(oldElement);
        var nested = document.getElementById("spinner");
        top.removeChild(nested);


      }
      oldElement = e.getAttribute("id");

      element.innerHTML += `<button class="btn btn-info " id="spinner" type="button" disabled>
  <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>

</button>`;


      createDetails(users[parseInt(e.id)])
    })
  })

};

let createDetails = (userView) => {

  var user = getUserSessionData();
  if (!user) user = getUserLocalData();
  var date = new Date(userView.registrationDate);

var year=date.getFullYear();
var month=date.getMonth()+1 //getMonth is zero based;
var day=date.getDate();
var formattedDate="Le " +day+"/"+month+"/"+year;


  let html = ` 
  <div class="container" id ="userListDetails">
    <div class="row" >
      <form>

        <table class="user table-dark table-responsive-xl float-left font-weight-bold " > 
          <tbody>
          <input type="hidden" name="user_id" value="${userView.id}"></th></th>
          
          <tr>
            <td>Prenom :</td><td> ${userView.firstName}</td>
          </tr>
          <tr>
            <td>Nom :</td>
            <td>${userView.lastName}</td>
          </tr>
          <tr>
            <td>Pseudo :</td> 
            <td>${userView.username}</td>
          </tr>
          <tr>
            <td>Rue :</td>
            <td>${userView.addressDTO.street}</td>
          </tr>
          <tr>
            <td>Numéro :</td> 
            <td>${userView.addressDTO.buildingNumber}</td>
          </tr>
          <tr>
            <td>Code postale : </td>
            <td>${userView.addressDTO.postCode}</td>
          </tr>
          <tr>
            <td>Commune :</td>
            <td>${userView.addressDTO.city}</td>
          </tr>
          <tr>
            <td>Pays :</td>
            <td>${userView.addressDTO.country}</td>
          </tr>
          <tr>
            <td>Date d'entregistrement :</td>
            <td>${formattedDate}</td>
          </tr>`;
  if (userView.validateRegistration) {
    html += `  <tr class ="font-weight-bold ">
            <td class="bg-success">Est confirmé :</td>
            <td class="bg-success">Oui</td>
          </tr>`;
  } else {
    html += `  <tr class ="font-weight-bold ">
            <td class="bg-danger ">Est confirmé :</td>
            <td class="bg-danger">Non</td>
          </tr>`;
  }

  if (userView.administrator) {
    html += ` <tr class ="font-weight-bold ">
            <td class="bg-success">Est Administrateur:</td>
            <td class="bg-success">Oui</td>
          </tr>`;
  } else {
    html += ` <tr class ="font-weight-bold ">
            <td class="bg-danger">Est Administrateur:</td>
            <td class="bg-danger">Non</td>
          </tr>`;
  }
  if (userView.antiqueDealer) {
    html += `
          <tr class ="font-weight-bold ">
            <td class="bg-success">Est Antiquaire :</td>
            <td class="bg-success">Oui</td>
          </tr>`;
  } else {
    html += `
          <tr class ="font-weight-bold ">
            <td class="bg-danger">Est Antiquaire :</td>
            <td class="bg-danger">Non</td>
          </tr>`;
  }



  if (userView.validateRegistration == false) {
    html += `<tr>
              <td colspan="2"><input class="btn btn-success btn-lg btn-block" type="submit" id ="confirm-user" value="Confirmer inscription"></td>
              </tr>
        `;
  }
  else{
  html += `<div class="row">
  <div class="dropdown float-left" style="position:relative; right:-200px;" id="dropdown">
  <button class="btn btn-danger btn-sm btn-block dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
    Changer le status
  </button>
  <div class="dropdown-menu dropdown-menu-xl bg-dark" aria-labelledby="dropdownMenuButton">
    <a class="dropdown-item bg-dark text-white" name='status' id="statusId" value='customer' >Client</a>
    <a class="dropdown-item bg-dark text-white" name='status' id="statusId" value='is_antique_dealer' >Client Antiquaire</a>
    <a class="dropdown-item bg-dark text-white" name='status' id="statusId" value='is_administrator' >Administrateur</a>
  
  </div>
</div>
</div>
</div>
    </tbody> 
 
   </table>
 </form>
 </div>
 </div>
 `;
  }




  document.getElementById("view").innerHTML = html;

  var listStatus = document.getElementsByName('status');
  listStatus.forEach((e) => {

    e.addEventListener('click', function () {

      let update = {
        userId: userView.id,
        status: e.getAttribute("value")
      }
      callAPI(API_BASE_URL + "updateStatus", "POST", user.token, update);
      location.reload();
    });
  });


  if (!userView.validateRegistration) {
    const confirm = document.getElementById("confirm-user");
    //const annuler = document.getElementById("confirm");

    confirm.style.display = `block`;
    //  annuler.innerHTML+=`<input type="submit" id ="annuler-user" value="Annuler inscription" />`;
    document.getElementById("confirm-user").addEventListener('click', async () => {
      let updated = {
        validateRegistration: true,
        id: userView.id
      }

      try {
        await callAPI(
          API_BASE_URL + "confirm_user",
          "POST",
          user.token,
          updated
        );

        PrintError({
          message: "inscription confirmé"
        }, "green");
      } catch (err) {

        console.error("Post validate users : " + err);
      }
    });
    //document.getElementById("annuler-user").addEventListener('click',function(){  userView.validateRegistration=false;changeEtatUser(userView, confirm);} );

  }
}


export default UserListPage;