import { useNavigate } from "react-router-dom";
import {useAuth} from "../../Context/AuthContext.jsx";

const LogoutLink = ({ onLogout }) => {
    const {logout} = useAuth();
    const navigate = useNavigate();

    const handleLogout = (e) => {
        e.preventDefault();
        logout();
        if (onLogout) onLogout();
        navigate("/");
    };

    return (
        <button
            onClick={handleLogout}
            className="w-full text-left px-4 py-2 hover:bg-gray-100 cursor-pointer"
        >
            Sign out
        </button>
    );
};

export default LogoutLink;
