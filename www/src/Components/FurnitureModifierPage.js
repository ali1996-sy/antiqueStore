import { getUserLocalData, getUserSessionData } from "../utils/session.js";

import callAPI from "../utils/api.js";
const API_BASE_URL = "/api/users/";
const API_Furniture_URL = "/api/furniture/";
import PrintError from "./PrintError.js";

import { RedirectUrl } from "./Router.js";
var user = getUserSessionData();
var furnitureStates = require("./data/furnitureStates.json")[0];
var arr = [];
if (!user) user = getUserLocalData();
let FurnitureModifier =async (furniture) => {
  try {
   var users = await callAPI(API_BASE_URL + "all", "GET", user.token, null);
  } catch (err) {
    console.error("Get all users : " + err);
  }
  users = Array.from(users.allUsers);
  var userHtml = "";
  users.forEach((e)=>{	
    if(e.antiqueDealer)
		 userHtml +=`
		    <a class="dropdown-item bg-light text-danger text-justify" name='userId' id="${e.id}" value='${e.id} ' ><span id="sortTable" class="text-primary font-weight-bold">${e.id}</span> | <span class="text-primary font-weight-bold"> ${e.username} </span>| <span class="text-primary font-weight-bold">${e.firstName} ${e.lastName}</span></a>
		  `;});
  let photoHtml = "";
  let tabImages = furniture.images;
  var photo_genrale = furniture.favoritePhoto;
  tabImages.forEach((e) => { if (e.photoId != furniture.favouritePhoto) photoHtml += ` <img id="${e.photoId}" class="photo-modify image photo" src="${e.photo}" >`; });
  let html = `
  <div class=""  id ="div-furniture">
  <div id="photo-generale" style="
    width: 800px;
    height: 369px; margin-bottom:100px;">
  <img id="${photo_genrale.photoId}" class="image-principal image" src="${photo_genrale.photo}" >
  </div>
  

  <div id="furniture-modifier" class="bg-dark text-white border">              

  <div class="">
  <form action="#" method="POST" class="" style="padding: 10px" source="custom" name="form">
  <div class="">
  <div class="" style="text-decoration: line-through !important;"></div><h5>Prix d'achat </h5>
  <h4 id="price">${furniture.purchasePrice} €</h4>
  </div>
  <div class="">
  <h5>Description</h5><br>
  <textarea placeholder="" rows="4" cols="50"  id="description" name="description" class="u-border-1 u-border-grey-30 u-input u-input-rectangle u-white" required="">${furniture.description}</textarea>
  </div>
  <div class="u-form-group u-form-select u-form-group-2" >
  <h5>Etat</h5>
  <div class="u-form-select-wrapper">
  <select id="state" name="select" class="u-border-1 u-border-grey-30 u-input u-input-rectangle u-white">
  <option value="${furnitureStates[furniture.state]}">${furnitureStates[furniture.state]}</option>
  <!--option value="Acheté">Acheté</option-->
  <!--option value="En restauration">En restauration</option -->
  <!--option value="Déposé en magasin">Déposé en magasin</option -->
  <!--option value="Proposé à la vente">Disponible à la vente</option -->
  <!--option value="Retiré">Retiré</option -->
  <!--option value="Vendu">Vendu</option -->
  </select>
  <div id="sellingPrice-div" style="display:none;">
  <div class="" style="text-decoration: line-through !important;"></div><h5>Prix de vente</h5>
  <input id="sellingPrice" Value ="${furniture.sellingPrice}" />
  </div>
  </div>
  <div id="sellingSpecialPrice-div" style="display:none;">
  <div class="" style="text-decoration: line-through !important;"></div><h5>Prix spécial</h5>
  <input id="sellingSpecialPrice" Value ="${furniture.specialSalePrice}" />
  </div>
  </div>
  <div class="dropdown" id="dropdown" style="display:none;">

		  <button class="btn btn-sm btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		   	Client
		  </button>

		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		${userHtml} 
		</div>
		</div>
  </div>
  <p >Type : ${furniture.furnitureType}</p>
  </form>
  
  </div>
  
  <div class="photos" id="photos1">
  <form action="#" method="POST" class="u-clearfix u-form-spacing-37 u-form-vertical u-inner-form" style="padding: 0px;" source="custom" name="form">
  <div  >
  <div id="add">
  <label for="upload" id="upload">
  <svg id="svg">
  <path d="M37.059,16H26V4.941C26,2.224,23.718,0,21,0s-5,2.224-5,4.941V16H4.941C2.224,16,0,18.282,0,21s2.224,5,4.941,5H16v11.059
  C16,39.776,18.282,42,21,42s5-2.224,5-4.941V26h11.059C39.776,26,42,23.718,42,21S39.776,16,37.059,16z"></path>
  </svg>

  </label>
  <input type="file" id="upload-file" name="upload" " multiple>
  </div>
  <div class="u-carousel-thumbnails " id="row"><!--product_gallery_thumbnail-->
  ${photoHtml}
  </div>
  
  </div> 
  
  </form>
  
  </div>
  
  </div>
  
  <div id="btn-favorite">
  <input id="choix" type="submit" value="Choisir comme photo préféré" class="u-form-control-hidden">
  </div>
  </div>  

  </div>
  <div class="u-align-right u-form-group u-form-submit">
        <input class=" btn btn-danger btn-sm " type="submit" value="Confirmer" id="submit-modify">
  </div>  

  `;
  
  document.getElementById('page').innerHTML = "";
  document.getElementById('page').innerHTML = html;
  if (document.getElementById('state').value == "Proposé à la vente") {
    document.getElementById('sellingPrice-div').style.display = 'block';
  }
 
  switch (furnitureStates[furniture.state]) {
    case "Acheté": arr = new Array("En restauration", "Déposé en magasin", "Vente spéciale"); break;
    case "En restauration": arr = new Array("Déposé en magasin", "Vente spéciale"); break;
    case "Déposé en magasin": arr = new Array("Proposé à la vente", "Vente spéciale"); break;
    case "Proposé à la vente": arr = new Array("Vendu", "Retiré", "Vente spéciale"); break;
    case "Vendu": arr = new Array(); break;
    case "Retiré": arr = new Array(); break;
    case "En attente d'achat":arr = new Array("Déposé en magasin","Vendu");break;
  }
  for (let i = 0; i < arr.length; i++) {
    var option = document.createElement('option');
    option.text = option.value = arr[i];

    state.add(option, 0);
  }
  var listUsers = document.getElementsByName('userId');
  var currentUserId=0;
	listUsers.forEach((e) => {
			e.addEventListener('click', (event) => {
				e.parentElement.parentElement.children[0].innerText = e.textContent.split('|')[1];
			  currentUserId = e.id;
			});

	}); 
  //choisir photo principal
  document.getElementById('choix').addEventListener('click', () => {

    let ph = document.getElementsByClassName('image-principal')[0];
    furniture['favoritePhoto'] = { photo: (ph.src), photoId: ph.id };

  });
  // choisir le prix de vente 
  let activities = document.getElementById('state');
  activities.addEventListener("change", function () {
    if (activities.value == "Proposé à la vente") {
      document.getElementById('sellingPrice-div').style.display = 'block';
    }else if (activities.value == "Vente spéciale") {
      document.getElementById('sellingSpecialPrice-div').style.display = 'block';
      document.getElementById('dropdown').style.display='block';

    }
    else {
      document.getElementById('sellingPrice-div').style.display = 'none';
    }
  });
  // upload photo  
  document.getElementById('upload-file').addEventListener('change', encodeImagetoBase64);
  function encodeImagetoBase64(element) {

    var reader = new FileReader();
    reader.onloadend = function () {
      document.getElementById('row').innerHTML += `<img id =-1 class="photo-modify image photo" src="${reader.result}" >`;
      furniture = updatePhotos(furniture);
    }
    let files = Array.from(element.target.files)
    files.forEach((e) => {

      reader.readAsDataURL(e);

    });
  }
  
  document.getElementById('submit-modify').addEventListener('click', function () { confirm(); });
  let confirm = async () => {
    furniture.description = document.getElementById('description').value;

    furniture.state =Object.keys(furnitureStates).find(key => furnitureStates[key] === document.getElementById('state').value) ;
    if(document.getElementById('state').value == "Vente spéciale"){
      
     if(currentUserId!=0) furniture.buyer=currentUserId;
      furniture.state = "saled";
    }
    furniture.sellingPrice = document.getElementById('sellingPrice').value;
    furniture.specialSalePrice = document.getElementById('sellingSpecialPrice').value;
    furniture.images = furniture.images.filter(e => e.photo != furniture.favoritePhoto.photo);
    try {

      furniture = await callAPI(
        API_Furniture_URL + "modifyFurniture",
        "POST",
        user.token,
        furniture
        );
      
      RedirectUrl('/listFurniture')
      //PrintError({message:"Meuble est bien modifié "},"green");
      
    } catch (err) {

      PrintError(err);


    }
  }
  furniture = updatePhotos(furniture);

  //Search();

};




export default FurnitureModifier;

function updatePhotos(furniture) {

  let elePr = document.getElementsByClassName('image-principal');
  let images = document.querySelectorAll('img');
  furniture.images = [];
  
  images.forEach(element => {
    furniture.images.push({ photo: element.src, photoId: element.id });
    element.addEventListener('click', function (e) {
      
      let tmp = elePr[0].src;
      let tmpId = elePr[0].id;
      elePr[0].src = e.target.src;
      elePr[0].id = e.target.id;
      e.target.src = tmp;
      e.target.id = tmpId;


    });
  });
  return furniture;
}

