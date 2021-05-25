import { RedirectUrl } from "./Router.js";
import Navbar from "./Navbar.js";
import { removeSessionData, removeLocalData } from "../utils/session.js";


const Logout = () => {
  let listSearch = document.getElementById("searchList");
  let myOptions = document.getElementById("myOptions");
  let option = document.getElementById("options");
  let visitRequest = document.getElementById("visitRequest");
  let forSale = document.getElementById("forSale");
  let search = document.getElementById("search");
  search.innerHTML = "";
  listSearch.innerHTML = "";
  myOptions.innerHTML = "";
  option.innerHTML = "";
  visitRequest.innerHTML = "";
  forSale.innerHTML = "";
  if (removeSessionData());
  else removeLocalData();
  // re-render the navbar for a non-authenticated user
  Navbar();
  RedirectUrl("/");


};


export default Logout;
