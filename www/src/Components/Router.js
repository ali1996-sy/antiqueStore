import HomePage from "./HomePage.js";
import UserListPage from "./UserListPage.js";
import RegisterPage from "./RegisterPage.js";
import LogoutComponent from "./LogoutComponent.js";
import ErrorPage from "./ErrorPage.js";
import {ListFurniture,MyFurniture} from "./listFurniture.js";
import VisitRequestList from "./VisitRequestList.js";
import VisitRequest from "./CreateVisitRequest";
import DisplayVisitRequest from"./DisplayVisitRequest";
import forSaleFurnitures from "./ForSaleFurnitures.js";
import options from "./Options.js";
import MyOptions from "./MyOptions.js";



const routes = {

 
	"/": HomePage,
	"/users": UserListPage,
	"/register": RegisterPage,
	"/logout": LogoutComponent,
	"/error": ErrorPage,
	"/myFurniture": MyFurniture,
	"/listFurniture": ListFurniture,
	"/forSaleFurnitures": forSaleFurnitures,
	"/options": options,
  "/myOptions": MyOptions,
   "/visitRequestList": VisitRequestList,
  "/visitRequest": VisitRequest,
  "/displayVisitRequest":DisplayVisitRequest
  
};

let navBar = document.querySelector("#navBar");
let componentToRender;

// dictionnary of routes
const Router = () => {
	/* manage to route the right component when the page is loaded */
	
	window.addEventListener("load", (e) => {
		
		componentToRender = routes[window.location.pathname];
		if (!componentToRender)
			return ErrorPage(
				new Error("The " + window.location.pathname + " ressource does not exist.")
				);
		componentToRender();
	});

	/* manage click on the navBar*/
	const onNavigate = (e) => {
		let uri;
		if (e.target.tagName === "A") {
			e.preventDefault();
      // To get a data attribute through the dataset object, get the property by the part of the attribute name after data- (note that dashes are converted to camelCase).
      uri = e.target.dataset.uri;
  }
  if (uri) {

      // use Web History API to add current page URL to the user's navigation history & set right URL in the browser (instead of "#")
      window.history.pushState({}, uri, window.location.origin + uri);
      // render the requested component
      // for the components that include JS, we want to assure that the JS included is not runned when the JS file is charged by the browser
      // therefore, those components have to be either a function or a class
      componentToRender = routes[uri];
      if (routes[uri]) {
		 
      	componentToRender();
      } else {
      	ErrorPage(new Error("The " + uri + " ressource does not exist"));
      }
  }
};

navBar.addEventListener("click", onNavigate);

  // Display the right component when the user use the browsing history
  window.addEventListener("popstate", () => {
  	componentToRender = routes[window.location.pathname];
  	componentToRender();
  });
};

const RedirectUrl = (uri, data) => {
	
  // use Web History API to add current page URL to the user's navigation history & set right URL in the browser (instead of "#")
  window.history.pushState({}, uri, window.location.origin + uri);
  // render the requested component
  // for the components that include JS, we want to assure that the JS included is not runned when the JS file is charged by the browser
  // therefore, those components have to be either a function or a class
  componentToRender = routes[uri];
  if (routes[uri]) {
  	if (!data)
  		componentToRender();
  	else
  		componentToRender(data);

  } else {
  	ErrorPage(new Error("The " + uri + " ressource does not exist"));
  }
};

export { Router, RedirectUrl };
