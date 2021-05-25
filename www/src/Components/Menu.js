import {
  getUserSessionData,
  getUserLocalData
} from "../utils/session.js";
import parseJwt from "../utils/utils.js";
import PrintError from "./PrintError.js";
import callAPI from "../utils/api.js";
const API_BASE_URL = "/api/users/";





let Menu = async () => {
  var local = getUserSessionData();
  if (!local) local = getUserLocalData();
  var tokenDecode = parseJwt(local.token);


  let element = document.getElementById("Menu");
  if (element.innerHTML != "") return;

  var html;
  try {
    var users = await callAPI(API_BASE_URL + "all", "GET", local.token);

  } catch (err) {

    if (err.message == "Unauthorized") {
      RedirectUrl("/logout");
      PrintError({
        message: "Veuillez vous connecter."
      }, "red");
    } else {
      PrintError(err);
    }

  }
  var number = 0;
  var display = "none"

  users = new Array(users.allUsers);

  users[0].forEach(element => {

    if (!element.validateRegistration) {
      number++;
      display = "block";
    }
  });

  if (tokenDecode.administrator) {
    html = `
    
    

          <div class=" Menu list-group bg-secondary" style="width: 250px; "  id="admin-menu">

            <li class="list-group-item list-group-item-dark list-group-item-action"><a href="/" >Accueil</a></li>
            <li class="list-group-item list-group-item-dark list-group-item-action"><a href="/visitRequestList" ">Liste Visites</a></li>
            <li class="list-group-item list-group-item-dark list-group-item-action"><a href="/visitRequest">Introduire une demande de visite</a></li>
            <li class="list-group-item list-group-item-dark list-group-item-action"><a href="/options">Options</a></li>
            <li class="list-group-item list-group-item-dark list-group-item-action"><a href="/myOptions">Mes Options</a></li>
            <li class="list-group-item list-group-item-dark list-group-item-action"><a href="/forSaleFurnitures">Confirmation Ventes</a></li>
            <li class="list-group-item list-group-item-dark list-group-item-action"><a  href="/listFurniture" >Liste Meubles</a></li>
            <a href="/users" data-uri="/accueil" class="notification"><li class="list-group-item list-group-item-dark list-group-item-action">Liste Utilisateurs</li><span class="badge" style="display:${display};">${number}</span></a>
          </div>
       


    
    </div>`;
  } else {
    html = `
    <div class="Menu">

    <div class=" list-group" style="width: 250px;" id="user-menu">
    <li class="list-group-item list-group-item-dark list-group-item-action"><a href="/" >Accueil</a></li>
    <li class="list-group-item list-group-item-dark list-group-item-action"><a href="/visitRequestList">Mes Visites</a></li>
    <li class="list-group-item list-group-item-dark list-group-item-action"><a href="/myOptions">Mes Options</a></li>
    <li class="list-group-item list-group-item-dark list-group-item-action"><a href="/myFurniture">Mes Meubles</a></li>
    <li class="list-group-item list-group-item-dark list-group-item-action"><a href="/listFurniture">Liste Meubles</a></li>
    <li class="list-group-item list-group-item-dark list-group-item-action"><a href="/visitRequest" >Introduire une demande de visite</a></li>


    
    </div></div>`;
  }
  if (local)
    element.innerHTML = html;
  else
    element.innerHTML = "";


  $('#admin-menu li').on('click', function (e) {
    var $this = $(this);
    $('.active').removeClass('active bg-danger');
    $this.toggleClass('active bg-danger');
  });

  $('#user-menu li').on('click', function (e) {
    var $this = $(this);
    $('.active').removeClass('active bg-danger');
    $this.toggleClass('active bg-danger');
  });

};
export default Menu;