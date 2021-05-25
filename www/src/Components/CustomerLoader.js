const API_User_URL = "/api/users/";
import callAPI from "../utils/api.js";
import {
	getUserSessionData
} from "../utils/session.js";
var customers = [];
async function CustomerLoader(){
	var user = getUserSessionData();
	try{
		let customersList = await callAPI(API_User_URL + "all", "GET", user.token,null);
		
		customersList = customersList.allUsers;
		customersList.forEach(e =>{customers.push(e.firstName)});
		return customers;
	}catch (err){
		console.error("Get all customers : ", err);
	}
	return customers;
};
export default CustomerLoader;