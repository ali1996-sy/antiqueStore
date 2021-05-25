import jwt_decode from "jwt-decode";
let  parseJwt=  (token)=> {

    return jwt_decode(token);
};
export default parseJwt;