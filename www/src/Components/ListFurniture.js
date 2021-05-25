const API_Furniture_URL = "/api/furniture/";
const API_Photo_URL = "/api/photos/";
import Furniture from "./Furniture.js";
import callAPI from "../utils/api.js";
import PrintError from "./PrintError.js";
import parseJwt from "../utils/utils.js";
import Search from "./Search.js";
import { getUserSessionData, getUserLocalData } from "../utils/session.js";
import { map } from "jquery";


let MyFurniture = () => {
  var user = getUserSessionData();
  if (!user) user = getUserLocalData();
  var tokenDecode = parseJwt(user.token);
  ListFurniture(null, tokenDecode.user);

};
let ListFurniture = async (furniture = null, userId = null) => {
  let listSearch = document.getElementById("searchList");
  let myOptions = document.getElementById("myOptions");
  let option = document.getElementById("options");
  let page = document.getElementById("visitRequest");
  let forSale = document.getElementById("forSale");
  let carousel = document.getElementById("carousel");

  carousel.innerHTML = ""
  listSearch.innerHTML = "";
  myOptions.innerHTML = "";
  option.innerHTML = "";
  page.innerHTML = "";
  forSale.innerHTML = "";
  var furnitureTypes = require("./data/furnitureTypes.json")[0];

  if (!document.getElementById("searchBar")) Search();
  var user = getUserSessionData();
  if (!user) user = getUserLocalData();
  var tokenDecode = parseJwt(user.token);
  carousel.innerHTML = ""
  listSearch.innerHTML = "";
  myOptions.innerHTML = "";
  option.innerHTML = "";
  page.innerHTML = "";
  forSale.innerHTML = "";
  var furnitureTypes = require("./data/furnitureTypes.json")[0];

  if (!document.getElementById("searchBar")) Search();
  
  var list;
  var url = "allFurnitures";
  var method = "GET";
  var data = null;
  if (userId != null) {
    url = "userId";
    method = "POST";
    data = { userId: tokenDecode.user };
  } else if (!tokenDecode.administrator) {
    url = "allFurnitureByState";
    method = "POST";
    data = { state: "for_sale" };
  }
  if (furniture == null) {
    try {
      furniture = await callAPI(
        API_Furniture_URL + url,
        method,
        user.token,
        data
      );
    } catch (err) {
      furniture.allFurnitures = [];
      console.error("get all list furniture", err);
      PrintError(err);
    }
  }
  let photosList=[];
  let getPhoto = async (thePhoto) => {
    try {
      var photo = await callAPI(
        API_Photo_URL + "photoById",
        "POST",
        undefined,
        thePhoto
      );
      return photo.photo.photo;
     
    } catch (err) {
      console.error("get a photo", err);
      PrintError(err.eval);
    }
  };

  list = Array.from(furniture.allFurnitures);
  let listHtml = `<table><tbody><thead>
        <tr>
            <th colspan="2">Liste des meubles</th>
        </tr>
    </thead>`;
  let index = 0;
  var photos = [];
  
  list.forEach((e) => {
    let thePhoto = {
      photoId: e.favouritePhoto,
      src: null,
    };
    photosList.push(thePhoto);
    //var image = null;
    //async()=>{
    // image =  getPhoto(thePhoto);

    e.furnitureType = furnitureTypes[e.furnitureType];
    if (e.description != null) {
      if (e.description != "") {
        //listHtml += `<li class="list-group-item item-color col-sm" id= "${index}" name="id-furniture">${e.description}</li>`;
        listHtml += `
            <tr">
            <td class="align-middle text-justify " id= "${index}" name="id-furniture">
            <div name="maPhoto" class="media" id="${e.favouritePhoto}">

            </div>
            </td>
            <td class="align-middle text-justify border border-5 bg-dark" id= "${index}" name="id-furniture">${e.description}</td>
        </tr>
    `;
      }
      index++;
    }
  });
  
  

  listHtml += `</tbody></table>`;
  let html = ` <div class="container1 text-center" id="furnituresListe"> <div class="row">
  <ul class=" furniture-list list-group ">
  
  ${listHtml}
  
  </ul>
  </div></div>`;
  photosList[0].src=await getPhoto(photosList[0]);
  document.getElementById("page").innerHTML = html;
   photosList.forEach ( async(e) => {
    e.src=await getPhoto(e);
   let  img=document.createElement("img")
    img.src=e.src;
    img.style.width="100px";
    img.style.height= "100px";
   document.getElementById(e.photoId).appendChild(img);
  });
  
  var listPhoto = document.getElementsByName("maPhoto");


  let listNames = Array.from(document.getElementsByName("id-furniture"));
  listNames.forEach((e) => {
    e.addEventListener("click", function () {
      e.classList.add("bg-success");
      Furniture(list[parseInt(e.id)]);
    });
  });
};
export { ListFurniture, MyFurniture };
