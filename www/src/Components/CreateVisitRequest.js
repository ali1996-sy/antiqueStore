const API_VISITREQUEST_URL ="/api/visitrequest/";
import callAPI from "../utils/api.js";
const API_USERS_URL = "/api/users/";
import { getUserSessionData, getUserLocalData } from "../utils/session.js";
import parseJwt from "../utils/utils.js";
import { RedirectUrl } from "./Router.js";
import PrintError from "./PrintError.js";

var furnitureTypes = require("./data/furnitureTypes.json")[0];
import Search from "./Search.js";
let VisitRequest = async (visitRequest = null) => {
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
  Search();
  let user = "";
  if (!user) user = getUserLocalData();
  user = getUserSessionData();
  if (user) {

    let html = "";
    var tokenDecode = parseJwt(user.token);
    let users ="";
    try {
       users = await callAPI(API_USERS_URL + "all", "GET", user.token, null);
    } catch (err) {
      console.error("Get all users : " + err);
    }
   
    users=Array.from(users.allUsers);
    var userHtml = "";
    users.forEach((e)=>{	
		userHtml +=`
		    <a class="dropdown-item bg-light text-danger text-justify" name='user' id="${e.id}" value='${e.id} ' ><span class="text-primary font-weight-bold"> ${e.username} </span>| <span class="text-primary font-weight-bold">${e.firstName} ${e.lastName}</span></a>
		  `;});
    html += `
  <div id="format-list" class="col rounded-lg bg-dark" style="margin:auto; padding: 1em;">
  <div class="row">
      <div class="input-group input-group-sm mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text" id="">Plage horaire</span>
        </div>
        <input type="text" class="form-control" aria-label="Small" id="timeSlot" aria-describedby="inputGroup-sizing-sm" required="true">
              </div>
      </div>
      <div class="container">
        <input class="form-check-input" type="checkbox" id="addressCheckbox">
          <label class="form-check-label text-white" for="flexCheckDefault">
            Ajouter une adresse
            </label>
          <div id="addressAvaible">

          </div>`;
          if(tokenDecode.administrator){
            html+= ` 	<div class="dropdown">
            <button class="btn btn-sm btn-primary dropdown-toggle" type="button" id="userButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Client
         </button><div class="dropdown-menu" aria-labelledby="dropdownMenuButton">${userHtml} 
            </div>`;
          }
          
		
          html+= `<div>
            <div class="row">
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <span class="input-group-text">Upload</span>
                </div>
                <div class="custom-file">
                  <input type="file" class="custom-file-input" id="uploadPhotos" accept=".jpg, .jpeg, .png" required>
                    <label class="custom-file-label" for="inputGroupFile01">Choisir une photo</label>
                  </div>
                </div>
              </div>
            </div>
            <div class="container">
              <div id="furnitureList">
                <table class="table bg-secondary border  w-75" >
                  <thead class="thead text-center text-white ">
                    <tr>
                      <th class="align-middle border border-5" scope="col">Photo</th>
                      <th class="align-middle border border-5" scope="col">Description</th>
                      <th class="align-middle border border-5" scope="col">Type</th>
                    </tr>
                  </thead>
                  <tbody id="ligne">
                  </tbody>
                </table>
              </div>
            </div>
            <div>
              <button type="submit" id="submitRequest" class="btn btn-primary">Demander une visite</button>
            </div>
          </div></div>
          </div>
          `;
    let htmlAddress = `
      <div class="row">
        <div class="col">
          <input type="text" class="form-control" id="visitStreet" name="street" placeholder="Rue" required>
        </div>
        <div class="col">
            <input type="text"  class="form-control" id="visitBuildingNumber" name="building-number" placeholder="Numéro" required>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <input type="text" class="form-control" id="visitCity" name="city" placeholder="Ville" required>
        </div>
        <div class="col">
            <input type="number" class="form-control"  id="visitPostCode" name="postCode" placeholder="Code postal" required>
        </div>    
      </div>
      <div class="row">
        <div class="col">
          <input type="text" class="form-control" id="visitCountry" name="country" placeholder="Pays" required>
        </div>
        <div class="col">
        <button type="button" id="validateAddress"class="btn btn-outline-primary">Confirmer l'adresse</button>
        </div>
      </div>
                    
                    <br>`;
    let typesHtml = "";
    Object.keys(furnitureTypes).forEach((e) => {
      typesHtml += `
                          <a class="dropdown-item bg-light text-danger text-justify" name="type" id="${e}" value='${e} ' >${furnitureTypes[e]}</a>
                        `;
    });


    let furnitures = ``;
    
    let addressToSend = "";
    let changed = "";
    page.innerHTML = html;
    var listUser = document.getElementsByName("user");
    listUser.forEach((e) => {
          e.addEventListener('click', (event) => {
            e.parentElement.parentElement.children[0].innerText = e.textContent;
            e.parentElement.parentElement.children[0].title = e.id;
          });
        });
    let address=document.getElementById('addressAvaible');
    let addressCheckbox = document.getElementById("addressCheckbox");
    addressCheckbox.addEventListener("change", function () {
      changed = addressCheckbox.checked;
      if (addressCheckbox.checked) {
        address.innerHTML = htmlAddress;
        let validateAddress = document.getElementById("validateAddress");
        if (validateAddress != null) {
          validateAddress.addEventListener("click", function () {
            if (!document.getElementById("visitStreet").readOnly) {
              validateAddress.innerText = "Modifier l'addresse";
              validateAddress.className = "btn btn-outline-danger";
            } else {
              validateAddress.innerText = "Confirmer l'adresse";
              validateAddress.className = "btn btn-outline-primary";
            }
            document.getElementById("visitStreet").readOnly = !document.getElementById("visitStreet").readOnly;
            document.getElementById("visitBuildingNumber").readOnly = !document.getElementById("visitBuildingNumber").readOnly;
            document.getElementById("visitCountry").readOnly = !document.getElementById("visitCountry").readOnly;
            document.getElementById("visitCity").readOnly = !document.getElementById("visitCity").readOnly;
            document.getElementById("visitPostCode").readOnly = !document.getElementById("visitPostCode").readOnly;
            if (changed) {
              addressToSend = {
                street: document.getElementById("visitStreet").value,
                buildingNumber: document.getElementById("visitBuildingNumber").value,
                country: document.getElementById("visitCountry").value,
                city: document.getElementById("visitCity").value,
                postCode: document.getElementById("visitPostCode").value
              }
            }
          });
        };
      } else {
        address.innerHTML = "";
      }
    });

    // upload photo
    let furniture = "";



    document.getElementById('uploadPhotos').addEventListener('change', encodeImagetoBase64);
    let indice = 0;
    function encodeImagetoBase64(element) {


      var reader = new FileReader();

      reader.onloadend = function () {
        document.getElementById('ligne').innerHTML += `<tr name="furniture">
      <td class="text-primary font-weight-bold align-middle border border-5" id="image"><img id =-1 class="" style="max-width:75%;" src="${reader.result}" > </td>
      <td class="align-middle text-justify border border-5"><textarea rows="5"> </textarea></td>
      <td class="align-middle text-justify border border-5">    <div class="dropdown">
        
      <button class="btn btn-sm btn-primary dropdown-toggle" type="button" id="${indice}" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          types
      </button>
      
      <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
      ${typesHtml} 
      </div></td>
      </tr>`;
        indice++;
        var usersList = document.getElementsByName("type");
        usersList.forEach((e) => {
          e.addEventListener('click', (event) => {
            e.parentElement.parentElement.children[0].innerText = e.textContent;
            e.parentElement.parentElement.children[0].id=e.id;
          });
        });
        
      }
      let files = Array.from(element.target.files)
      files.forEach((e) => {
        reader.readAsDataURL(e);


      });
    };
 
    let toSend = "";
    let submit = document.getElementById("submitRequest");
    let furnituresArray = [];
    let listToInsert = document.getElementsByName("furniture");

    submit.addEventListener("click", async () => {
      let timeSlot = document.getElementById("timeSlot").value;
      let seller = "";
      if(tokenDecode.administrator){
         seller=document.getElementById('userButton').title;
      }
      else{
        seller=tokenDecode.user;
      }
      listToInsert.forEach((e) => {
        let td = Array.from(e.children);
        let imageSource = td[0].children[0].src;
        let description = td[1].children[0].value;
        let type = td[2].children[0].children[0].id;
        let furniture = {
          photo: imageSource,
          furnitureType: type,
          description: description
        }
        furnituresArray.push(furniture);
      });

      if (changed) {
        toSend = {
          timeSlot: timeSlot,
          seller:seller,
          address: addressToSend,
          furnitures: furnituresArray
        }
      }
      else {
        toSend = {
          timeSlot: timeSlot,
          seller: seller,
          furnitures: furnituresArray
        }
      }
      await onVisitRequest(user, toSend);
    });
  }
}
async function onVisitRequest(user, toSend) {
  try {
    await callAPI(API_VISITREQUEST_URL + "createVisitRequest","POST", user.token, toSend);
      document.getElementById('visitRequest').innerHTML="";
      RedirectUrl("/visitRequestList");
      PrintError({ message: "Votre demande est bien envoye !" }, "green");
      // re-render the navbar for the authenticated user
      Navbar();

  } catch (error) {
    console.error(error);
  }
}
export default VisitRequest;


