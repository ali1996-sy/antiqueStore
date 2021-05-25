const API_OPTION_URL = "/api/option/";
import callAPI from "../utils/api.js";
const API_Photo_URL = "/api/photos/";
import FurnitureModifier from "./FurnitureModifierPage.js";
import createOption from "./CreateOption";
import parseJwt from "../utils/utils.js";
import PrintError from "./PrintError.js";
import {
  getUserSessionData,
  getUserLocalData
} from "../utils/session.js";
var furnitureStates = require("./data/furnitureStates.json")[0];
var options = [];
var optionsId = [];
let Furniture = async (furniture) => {

  var user = getUserSessionData();
  if (!user) user = getUserLocalData();
  if (user) {
    options = await callAPI(API_OPTION_URL + "all", "GET", user.token, null);
    options = Array.from(options.allOptions);

    options.map(({
      furniture,
      state
    }) => {
      if (furniture && state == "pending") optionsId.push(furniture)
    });

  }

  let furnitureId = furniture.furnitureId;
  try {
    var photos = await callAPI(
      API_Photo_URL + "photosByFurnitureId",
      "POST",
      undefined, {
        furnitureId
      }
    );
    var tabImages = Array.from(photos.allPhotos);
    furniture['images'] = tabImages;

  } catch (err) {
    console.error("get all list furniture", err.eval);
    PrintError(err.eval);
  }
  let photoHtml = "";
  var photo_genrale = tabImages[0];
  tabImages.forEach((e) => {
    if (e.photoId == furniture.favouritePhoto) photo_genrale = e;
    else
      photoHtml += ` <img id="${e.photoId}" class="block image" style="width: 100px; height: 100px; "src="${e.photo}" >`;
  });

  let html = `
<div class=" view"  id ="format-list" >
<div class=" container">
  <div class="row">
    <div class="col " style="width: 400px; height: 400px; margin-bottom:100px margin-left:auto;margin-right:auto; vertical-align:middle;" >
      <img id="${photo_genrale.photoId}" class="image-principal image"  src="${photo_genrale.photo}" >
    </div>
    <div class ="col bg-dark border rounded " id="furniture-viewer">
      <h2 class=" "id ="title">            
      </h2>
      <div  class="bg-dark  ">    
      <h5>Prix De vente : </h5>
        <div class="" style="font-size: 1.5rem; font-weight: 700;"><p>${furniture.sellingPrice}€</p></div>
      </div>
      <div  class="bg-dark  ">    
      <h5>Prix Spécial: </h5>
        <div class="" style="font-size: 1.5rem; font-weight: 700;"><p>${furniture.specialSalePrice}€</p></div>
      </div>
      <h5  class="description" >Description :</h5>
      <textarea  class="description bg-secondary text-white" readonly id="description">${furniture.description}</textarea>
      
`;

  html += `  
      <span> <p id="state">Etat : ${furnitureStates[furniture.state]}</p></span>
      <p >Type : ${furniture.furnitureType}</p>`;
  if (furniture.state == "for_sale") {

    html += `<input type="submit" value="Introduire une option " id="submit">`;
  }

  html += `</div>
  </div>
  <div class="row">
  ${photoHtml}
  </div>
  </div>`;
  furniture['images'] = tabImages;
  furniture['favoritePhoto'] = photo_genrale;
  document.getElementById('page').innerHTML = "";
  document.getElementById('page').innerHTML = html;

  if (furniture.state == "for_sale") {

  document.getElementById('submit').addEventListener('click', function () {
    createOption(furniture)
  });
}
  let elePr = document.getElementsByClassName('image-principal');



  if (user) {
    var tokenDecode = parseJwt(user.token);
    if (tokenDecode.administrator) {
      document.getElementById('title').innerHTML += '<button id="edit" class="btn btn-danger btn-sm">Modifier</button>';
      document.getElementById('edit').addEventListener('click', function () {
        FurnitureModifier(furniture)
      });
    }
  }

  let images = document.querySelectorAll('img');
  images.forEach(element => {
    element.addEventListener('click', function (e) {
      let tmp = elePr[0].src;
      elePr[0].src = e.target.src;
      e.target.src = tmp;
    });
  });
  //Search();

};

export default Furniture;