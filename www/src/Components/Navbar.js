let navBar = document.querySelector("#navBar");

import Menu from "./Menu.js";
import { RedirectUrl } from "./Router.js";
import PrintError from "./PrintError.js";
import callAPI from "../utils/api.js";
const API_BASE_URL = "/api/users/";
import { getUserSessionData, setUserSessionData, getUserLocalData, setUserLocalData } from "../utils/session.js";


let navbar;
// destructuring assignment
const Navbar = async () => {

  var user = getUserSessionData();
  if (!user) user = getUserLocalData();
  if (user) {
  
    let username = user.user.username.charAt(0).toUpperCase() + user.user.username.substring(1).toLowerCase();
    Menu();
    navbar = `
    <nav class="navbar navbar-expand-lg rounded-lg navbar-light" style="background-color: rgba(0,0,0 ,0.7)">
    
    <a class="navbar-brand text-white"  href="/" data-uri="/"><h4>Li Vi Satcho </h4></a>
    <button
    class="navbar-toggler"
    type="button"
    data-toggle="collapse"
    data-target="#navbarNavAltMarkup"
    aria-controls="navbarNavAltMarkup"
    aria-expanded="false"
    aria-label="Toggle navigation"
    >
    <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    <div class="navbar-nav" >   
    <a class="nav-item nav-link disabled text-primary font-weight-bold" href="#">${username}</a>
    </div>
    </div>
    <a class="text-white" href="/logout" data-uri="/logout" >Se déconnecter</a>
    </nav>`;
 document.getElementById("search").innerHTML="";
  } else {

    navbar = `<nav class="navbar navbar-expand-lg rounded-lg navbar-light" style="background-color: rgba(0,0,0 ,0.7);">
    
    <a class="navbar-brand text-white" href="/" data-uri="/"><h4>Li Vi Satcho</h4></a
    >
    <div>


    </div><button
    class="navbar-toggler"
    type="button"
    data-toggle="collapse"
    data-target="#navbarNavAltMarkup"
    aria-controls="navbarNavAltMarkup"
    aria-expanded="false"
    aria-label="Toggle navigation"
    >
    <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    <div class="navbar-nav" >
    <li class="nav-item">
    <a class="nav-item nav-link text-primary" href="/register" data-uri="/register"><i class="fa fa-fw fa-user-o "></i>S'inscrire</a></li>
    <li class="nav-item">
    <form id= "formLog" class="log text-white">
    <span class="text-light font-weight-bold">Pseudo</span> /<span class="text-light font-weight-bold"> Email </span>:
    <input id="login" type="text" placeholder="Enter your login" required="Please put your  login" oninvalid="this.setCustomValidity('Please Enter your username')"/>
    <span class="text-light font-weight-bold">Mot de passe:</span>
    <input id="loginPassword" type="password" name="password" placeholder="Enter your password" required="" oninvalid="this.setCustomValidity('Please Enter your password')" />
    <button class="btn btn-primary" id="btn" type="submit">Se connecter</button>
    <input class="" type="checkbox" value="rememberMe" id="rememberMe"> <label for="rememberMe" class="text-primary font-italic">Se souvenir de moi</label>
    </li>
    <!-- Create an alert component with bootstrap that is not displayed by default-->
    
    </form> 
    <div class="alert alert-danger mt-2 d-none" id="messageBoard"></div>
    </div>
    </nav>`;

  }
 
  navBar.innerHTML = navbar;
  if (document.getElementById("formLog"))
    document.getElementById("formLog").addEventListener("submit", onLogin);
  return;
};
const onLogin = async (e) => {
  e.preventDefault();
  let login = document.getElementById("login");
  let password = document.getElementById("loginPassword");

  let user = {
    login: login.value,
    password: password.value,
  };

  try {
    const userLogged = await callAPI(
      API_BASE_URL + "login",
      "POST",
      undefined,
      user
      );
    let rememberMe = document.getElementById("rememberMe").checked;

    onUserLogin(userLogged, rememberMe);
  } catch (err) {
    
    console.error("LoginPage::onLogin", err.message);
    PrintError(err);
    
    
  }
};

const onUserLogin = (userData, rememberMe) => {
  if (rememberMe)
    setUserLocalData(userData);
  else
    setUserSessionData(userData);
  // re-render the navbar for the authenticated user
  Navbar();
  RedirectUrl("/");
};

export default Navbar;
