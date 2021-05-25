import {
  RedirectUrl
} from "./Router.js";
import callAPI from "../utils/api.js";
const API_OPTION_URL = "/api/option/";
import {
  getUserSessionData,
  getUserLocalData
} from "../utils/session.js";



let createOption = (furniture) => {
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
  if (!user) {
    user = getUserLocalData();
  }
  if (user) {
    let html = `
    <div class="row" style="top:200px !important; ">
    <input class="btn btn-success btn-lg btn-block " type="submit" id="submitOption" value="Introduire l'option">
  </div><div class="bg-dark">
  <input class="form-control bg-dark text-danger" type="text" value="Attention vous avez 5 jours au maximum avant que votre option soit annulée !" readonly>
  <input class="form-control bg-dark text-white" type="text" value="Prix du meuble : ${furniture.sellingPrice}€" readonly> 
  <textarea class="form-control bg-dark text-white" id="exampleFormControlTextarea1" rows="3" readonly>Description : ${furniture.description}</textarea>
  <div class=" bg-dark text-white form-control"><label for="optionTerm">Choisisez la durée de l'option</label>
  <input type="number" id="optionTerm" name="" min="1" max="5">

  </div>

  `;
    document.getElementById('furniture-viewer').innerHTML = html;
    document.getElementById('submitOption').addEventListener("click", function () {
      let update = {
        furniture: furniture.furnitureId,
        optionTerm: document.getElementById("optionTerm").value,
        state: 'pending',
        furnitureState: 'in_option',
        buyer: user.user.id
      };
      try {
        callAPI(API_OPTION_URL + "put", "POST", user.token, update);
      } catch (error) {
        console.error("Create option error :" + error);
      }
      RedirectUrl("/listFurniture");
    });
  }
};
export default createOption;