const API_Furniture_URL = "/api/furniture/";
import callAPI from "../utils/api.js";
import PrintError from "./PrintError.js";
import { RedirectUrl } from "./Router.js";
import { getUserSessionData, getUserLocalData } from "../utils/session.js";

let afficher  =async (visitRequest)=>{
	var user = getUserSessionData();
  if (!user) user = getUserLocalData();
  let listHtml="";
  var tabImages = visitRequest.photos;

  var tabFurniture=visitRequest.furniture;
  let index=0;
  tabImages.forEach((e)=>{
    
	if(tabFurniture[index].state=="requested"){
    listHtml+=`<tr><td><img id="${e.photoId}"  class="block image" style="width: 100px; height: 100px; " src="${e.photo}" ></td><td> Prix d'achat : <input type="text" id="prix"><br>  
    <br> Description :<textarea readonly id="description" rows="6">${tabFurniture[index].description}</textarea><br> Date de la livraison : <input type="date" id="date-transport" ></td><td>
    <div class="container">
    
    Annuler
    <input class="form-check-input" type="checkbox" id="annuler" unchecked>
   </tr><tr></td><td>
  <input  title="${e.furniture}" name="${e.photoId}" id="modifier" type="button" value="Confirmer ">
  
  </div></td></tr>`;
   

}
  else if(tabFurniture[index].state=="cancelled"){
    listHtml+=`<tr><td><img id="${e.photoId}"  class="block image" style="width: 100px; height: 100px; " src="${e.photo}" ></td>
    <td> Description :<p id="description">${tabFurniture[index].description}</p></td><td>
  <p>Annulé</p></td></tr>`;
    
  }
  else{
    var date = new Date(tabFurniture[index].withdrawalDate);
    var year=date.getFullYear();
    var month=date.getMonth()+1 //getMonth is zero based;
    var day=date.getDate();
    var formattedDate="Le " +day+"/"+month+"/"+year;
   listHtml+=`<tr><td><img id="${e.photoId}"  class="block image" style="width: 100px; height: 100px; " src="${e.photo}" ></td><td> Prix d'achat : ${tabFurniture[index].purchasePrice}<br>  
  <br> Description :<p id="description">${tabFurniture[index].description}</p><br> Date de la livraison : <p>${formattedDate}</p></td><td>
  <p>Acheté</p></td></tr>`;
 
  }
  index++;
});

  let html=`<div class="d-flex justify-content-center">
  <table class="table table-dark " id="list-user">
  <thead class="thead-dark">
    <tr>
      <th scope="col"></th>
      <th></th><th>
    </tr>
   
  </thead>
    <tbody>
      ${listHtml}
    </tbody>
  </table></div>
  <div id="view"></div>
    `;

  document.getElementById('page').innerHTML=html;
  if(document.getElementById('modifier'))
	document.getElementById('modifier').addEventListener('click',async (e)=> {
	
		try {
  
    await callAPI(
      API_Furniture_URL + "modifyFurnitureByVisitRequest",
      "POST",
      user.token,
      {
		visitRequestId: visitRequest.visitId,
		favouritePhoto:e.target.name,
		description:document.getElementById('description').value ,
		withdrawalDate:document.getElementById('date-transport').value,
		purchasePrice:document.getElementById('prix').value,
    furnitureId:e.target.title,
    sellerId:visitRequest.seller.id,
    annuler:document.getElementById('annuler').checked
      }
    );
      RedirectUrl("/visitRequestList");
  } catch (err) {
    console.error("update furniture", err);
    PrintError(err);
  }
	});
 
}
export default  afficher ;

async function getFurniture(user, e) {
  try {
    var furniture = await callAPI(
      API_Furniture_URL + "furnitureId",
      "POST",
      user.token,
      {
        furnitureId: e.furniture
      }
    );
      return furniture.furniture;
  } catch (err) {
    console.error("get furniture", err);
    PrintError(err);
  }
}
