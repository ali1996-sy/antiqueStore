import {Router} from "./Components/Router.js";
import Navbar from "./Components/Navbar.js";
/* use webpack style & css loader*/
import "./stylesheets/style.css";
/* load bootstrap css (web pack asset management) */
import 'bootstrap/dist/css/bootstrap.css';
/* load bootstrap module (JS) */
import 'bootstrap';
import('bootstrap-tagsinput');
import('bootstrap-tagsinput/dist/bootstrap-tagsinput.css');
const FOOTER_TEXT = "Happy shopping : )";


Navbar();


Router();


document.querySelector("#footerText").innerText = "";
