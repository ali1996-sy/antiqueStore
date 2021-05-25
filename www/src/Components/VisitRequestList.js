const API_Furniture_URL = "/api/visitrequest/";
import callAPI from "../utils/api.js";
import PrintError from "./PrintError.js";
import parseJwt from "../utils/utils.js";
import Search from "./Search.js";


import { getUserSessionData, getUserLocalData } from "../utils/session.js";
import DisplayVisitRequest from "./DisplayVisitRequest.js";
let VisitRequestList = async (visitRequest = null) =>{

   var user = getUserSessionData();
   if (!user) user = getUserLocalData();
    var tokenDecode = parseJwt(user.token);
    if(!document.getElementById('searchBar'))Search();
    var user = getUserSessionData();
    if (!user) user = getUserLocalData();
    var tokenDecode = parseJwt(user.token);
    var list;
    var url="selectAllVisitRequest";
    var method="GET";
    var data=null;
    if(!tokenDecode.administrator){
      url="selectAllVisitRequestByUserId";
      method="POST";
      data={userId:tokenDecode.user};
    }
if (visitRequest == null) {
  try {
    visitRequest = await callAPI(
      API_Furniture_URL + url,
      method,
      user.token,
      data
    );
  
   
  } catch (err) {
    console.error("get all visit requests", err);
    PrintError(err);
  }
}

//if(visitRequest==undefined)
// var list=new Array();
// else
var list = Array.from(visitRequest.allVisitRequests);
let listHtml = "";
let index = 0;
list.forEach((e) => {
 
  listHtml += ` <tr name="id-visitRequest" id="${index}">
  <td class="text-justify font-weight-bold text-primary border"> ${e.seller.username}</td>
  <td class="text-justify border"> ${e.timeSlot}</td>
  <td class="text-justify border"> ${e.requestDate}</td>`;
  if(e.furnitureAddress.buildingNumber!=0)
  listHtml += `
 <td class="text-justify border">${e.furnitureAddress.street} ${e.furnitureAddress.buildingNumber},
 ${e.furnitureAddress.postCode},
   ${e.furnitureAddress.city} ,
 ${e.furnitureAddress.country}
  </td>`;
  else
  listHtml += `<td  class="text-justify border"> </td>`;
  listHtml +=` 
  </tr>`; index++;
  })
  let html = `<div class="d-flex  justify-content-center">
  <div class="table-responsive">
  <table class="table  table-dark " id="format-list">
<thead class="thead-dark">
  <tr>
    <th scope="col" class="border" >Vendeur</th>
    <th scope="col" class="border">PlageHoraire</th>
    <th scope="col" class="border">Date de demande</th>
    <th scope="col" class="border">Adresse</th>

  </tr>
</thead>
  <tbody>
    ${listHtml}
  </tbody>
</table></div>
<div id="view"></div></div>
  `;

document.getElementById("page").innerHTML = html;


let listNames = Array.from(document.getElementsByName("id-visitRequest"));


listNames.forEach((e) => {
  e.addEventListener('click', function () {
  	e.classList.add("bg-secondary");

    DisplayVisitRequest(list[parseInt(e.id)])
  })
})
}
export default VisitRequestList;

