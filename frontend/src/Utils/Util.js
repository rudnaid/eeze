import { useNavigate } from "react-router-dom";

export const isUserLoggedIn = () => {

    const token = localStorage.getItem('jwt');

    return token == null ? false : true;
}


