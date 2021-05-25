const API_Furniture_URL = "/api/visitrequest/";
import callAPI from "../utils/api.js";
import PrintError from "./PrintError.js";
import { RedirectUrl } from "./Router.js";
const API_Photo_URL = "/api/photos/";
import { getUserSessionData, getUserLocalData } from "../utils/session.js";
import afficher from "./FixerPrixDAchat.js";
let DisplayVisitRequest = async (visitRequest) => {
 
  var user = getUserSessionData();
  if (!user) user = getUserLocalData();

  try {
    
    var photos = await callAPI(
      API_Photo_URL + "photosByVisitRequestId",
      "POST",
      user.token,
      {
        visitRequestId: visitRequest.visitId
      }
    );
      
  } catch (err) {
    console.error("get all visit requests", err);
    PrintError(err);
  }

  
  
  var list = Array.from(photos.allPhotos);
  var furniture = Array.from(photos.furniture);
  visitRequest.photos=list;
  visitRequest.furniture=furniture;
  var photoHtml=``;
  list.forEach((e)=>{
    photoHtml+=`<img id="${e.photoId}" class="block image" style="width: 100px; height: 100px; " src="${e.photo}" >`;
  })
  let listHtml = ``;
 
  listHtml += `
    <input id="visitId" type="hidden" name="visit_id" value="${visitRequest.visitId}"><tr>
    <td>Date de demande :</td><td> ${visitRequest.requestDate}</td></tr>
    <tr><td>Plage horaire:</td><td> ${visitRequest.timeSlot}</td></tr>
    <tr><td>Photo(s) :</td><td>${photoHtml}</td></tr>
    `;
  switch (visitRequest.state) {
    case "cancelled": listHtml += `<tr><td> Etat :</td><td>Annulé</td></tr><td>Raison d'annulation :</td><td>${visitRequest.cancellationsDue}</td></tr>`;  break;
    case "finished" : listHtml += `<tr><td>Date choisie : </td><td>${visitRequest.chosenDate}</td></tr><tr><td> Etat :</td><td>Confimée</td><td><input id="fixerPrix" type="button" value="Fixer prix d'achat " class="u-form-control-hidden"></td></tr>`; break;
    default:
      listHtml += `<tr><td> Etat :</td><td>En cours</td></tr><td>Raison d'annulation :</td><td><input type="text" placeholder="Entrez la raison" id="raison" required=""><input id="annuler" type="submit" value="Annuler" class="u-form-control-hidden bg-danger"></td></tr>
    <tr><td>
    <label for="meeting-time">Choisir la date et l'heure</label>

<input type="datetime-local" id="meeting-time"
       name="meeting-time" value=""
       min="${new Date()}" max="2025-06-14T00:00">
    </td> 
    <td>
    <input id="confirm" type="button" value="Confirmer la demande" class="u-form-control-hidden">
    </td></tr>`;
  }

  let html = `<div class="d-flex justify-content-center">
    <table class="table table-dark " id="list-user">
  <thead class="thead-dark">
    <tr>
      <th scope="col">Informations</th>
      <th></th><th></th>
    </tr>
   
  </thead>
    <tbody>
      ${listHtml}
    </tbody>
  </table></div>
  <div id="view"></div>
    `;


  document.getElementById("page").innerHTML = html;
  if(document.getElementById('fixerPrix'))document.getElementById('fixerPrix').addEventListener('click',()=>fixerPrix());
let fixerPrix= ()=>{
  afficher(visitRequest)
}
  let listNames = Array.from(document.getElementsByName("id-furniture"));
  listNames.forEach((e) => {
    e.addEventListener('click', function () {
      Furniture(list[parseInt(e.id)])
    })
  })

  if(document.getElementById("annuler"))document.getElementById("annuler").addEventListener("click", async () => {
    document.getElementById("raison").value
    try {
      await callAPI(
        API_Furniture_URL + "cancelVisitRequest",
        "POST",
        undefined,
        {
          visitId: document.getElementById("visitId").value,
          cancellationsDue: document.getElementById("raison").value,
          furniture:furniture
        }

      );
      RedirectUrl("/visitRequestList")

    } catch (err) {
      console.error(err);
    }
  });
  if(document.getElementById("annuler"))document.getElementById("confirm").addEventListener("click", () => {

    onVisitRerquest();
  });
  async function onVisitRerquest() {
   

    try {
      await callAPI(
        API_Furniture_URL + "confirmVisitRequest",
        "POST",
        undefined,
        {
          visitId: document.getElementById("visitId").value,
          chosenDate:document.getElementById('meeting-time').value

        }

      );
      onDemandEnvoye();
    } catch (err) {
      console.error(err);
      PrintError({ message: "Demande n'est pas envoye " });
    }
  };


  const onDemandEnvoye = () => {
    RedirectUrl("/visitRequestList");
    PrintError({ message: "Votre demande est bien envoye !" }, "green");
    
  };

}

export default DisplayVisitRequest;