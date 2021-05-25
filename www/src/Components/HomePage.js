import { getUserSessionData, setUserSessionData, getUserLocalData, setUserLocalData } from "../utils/session.js";
const API_Photo_URL = "/api/photos/";
import Navbar from "./Navbar.js";
import callAPI from "../utils/api.js";
import PrintError from "./PrintError.js";
const API_BASE_URL = "/api/users/";
import MyImage from "../images/logoAE_v2.png";
import carousel from "./Carousel.js";
//import Furniture from "./Furniture.js";
import Search from "./Search.js";
let listSearch = document.getElementById("searchList");
let myOptions = document.querySelector("#myOptions");
let option = document.querySelector("#options");
var furnitureTypesFr = require("./data/furnitureTypesFr.json")[0];

let homePage = `<div class="d-flex justify-content-center"><div class="container text-center" id="logo">
<div class="text-center">
<p class="font-weight-bold text-light h2">
Bienvenue Li Vi Satcho antique store
</p><p class=" text-light font-italic"> Adresse : 1bis sente des artistes – 4800, Verviers, Belgique</p><br>
<div id=img>
<img src=${MyImage} style=" width: 250px;height: 250px;" id="logo-photo">
</div>
<div>
</div></div>
`;

const HomePage = async () => {
  let listSearch = document.getElementById("searchList");
  let myOptions = document.getElementById("myOptions");
  let option = document.getElementById("options");
  let visitRequest = document.getElementById("visitRequest");
  let forSale = document.getElementById("forSale");
  listSearch.innerHTML = "";
  myOptions.innerHTML = "";
  option.innerHTML = "";
  visitRequest.innerHTML = "";
  forSale.innerHTML = "";

  var user = getUserSessionData();
  if (!user) user = getUserLocalData();
  let page = document.getElementById("page");

  page.innerHTML = "";
  page.innerHTML = homePage;
  carousel();

  if (user) {
    Search();
    try {
      await callAPI(API_BASE_URL + "me", "GET", user.token, null);

    } catch (err) {

      console.error(err);


    }
    // re-render the navbar for the authenticated user


    //RedirectUrl("/");
  } else {
    document.getElementById("Menu").innerHTML = "";
  
    let types = Array.from(Object.keys(furnitureTypesFr));
    let typesHtml = "";
    types.forEach(element => {
      typesHtml += `<option value="${furnitureTypesFr[element]}">${element}</option>`;
    });
    types = `<div id="type-search" class="text-white">
		<div class="card bg-secondary" style="width: 250px;position: absolute;left: 0px;">Choisir Type
		<select   id ="select-type" data-role="tagsinput">
		<option>${typesHtml}</option>
		
		</select>
		<div id="types" data-role="tagsinput" ></div>
    <input type="button"  id ="submit-type" class="form-control" value="Rechercher" >
    </div>
    </div>
    </div>
    `;
    document.getElementById("search").innerHTML += types;
    document.getElementById('submit-type').addEventListener('click', async () => {
    
      let type = document.getElementById('select-type').value;
      try {
        var allPhotos = await callAPI(API_Photo_URL + "photosByType", "POST", null, {
          type: type
        });
      

        allPhotos = Array.from(allPhotos.allPhotos);

      } catch (err) {
        console.error("Search-customer", err);
        PrintError(err);
      }
      document.getElementById('carousel').innerHTML = "";
      if (allPhotos.length > 0)
        carousel(allPhotos);


    });

  };
  Navbar();

};





export default HomePage;