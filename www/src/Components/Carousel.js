const API_Photo_URL = "/api/photos/";
import callAPI from "../utils/api.js";
import PrintError from "./PrintError.js";
var slideIndex = 0;

let carousel = async (photos=null) => {
   var allPhotos;
  if(!photos){
  try {
     allPhotos = await callAPI(
      API_Photo_URL + "allPhotos",
      "GET",
      undefined,
      null
      );
     
    allPhotos = allPhotos.allPhotos;
;
  } catch (err) {

    PrintError({ message: "Serveur Backend non lancé " });
  }
  }
  else allPhotos=photos;
  let element = document.getElementById("carousel");
  
  let slide = `<div id="carousel-color" class="d-inline d-block justify-content-center" >`;
  let photo ="";
  let photoIcon = "";
//<div class="d-flex justify-content-center ">...</div>
allPhotos.forEach((e) => {
    if(e.furnitureState=="deposited"||e.furnitureState=="for_sale"||e.furnitureState=="saled"||e.furnitureState=="in_option"){
    photo += ` <div class="mySlides" >
    <img src="${e.photo}" class="photo-slide-preview" id="${e.furnitureId}">
    </div>`;
    
  
  
  photoIcon += `<div class="column">
  <img class="demo cursor slide-photo rounded  border-3" src="${e.photo}" id ="${slideIndex}">
  </div>`; slideIndex++;
    }
});
if(slideIndex != 0){
  slide+=photo;
}

let html = `

<!-- Next and previous buttons -->

<div class="container-cl" style="background-color: rgba(0,0,0 ,0.7)">
${slide}




<!-- Thumbnail images -->
<div class="row caption-container">
<button id="left" class="prev bg-dark text-danger" >&#10094;</button>
<button id="right" class="next bg-dark text-danger" >&#10095;</button>
${photoIcon}
</div>

</div>

`;

if(document.getElementsByClassName("mySlides").length==0&&document.getElementById("logo"))
  element.innerHTML += html;

changerPhoto();

};

let changerPhoto = () => {

  document.getElementsByClassName('next')[0].addEventListener('click', function () { showSlides(slideIndex += 1); });
  document.getElementsByClassName('prev')[0].addEventListener('click', function () { showSlides(slideIndex -= 1); });
  let photos = document.getElementsByClassName('slide-photo');
  let slides = document.getElementsByClassName('photo-slide-preview');
  for (let index = 0; index < photos.length; index++) {
    photos[index].addEventListener('click', function () { currentSlide(parseInt(photos[index].id)) });

  }
}
var nIntervId = setInterval(function () { showSlides(slideIndex += 1); }, 3000);
function currentSlide(n) {

  showSlides(slideIndex = n);
}

function showSlides(n) {
  var i;
  var slides = document.getElementsByClassName("mySlides");
  if (n >= slides.length) { slideIndex = 0 }
    if (n < 0) { slideIndex = slides.length - 1 }
      for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
      }
      if (slides[0])
        slides[slideIndex].style.display = "block";
  //function (){clearInterval(nIntervId);
  }





  export default carousel;
