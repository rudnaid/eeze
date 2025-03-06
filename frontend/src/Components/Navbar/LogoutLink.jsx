import React from "react";
import { useNavigate } from "react-router-dom";

const LogoutLink = ({ onLogout }) => {
    const navigate = useNavigate();

    const handleLogout = (e) => {
        e.preventDefault();
        localStorage.removeItem("jwt"); // Remove token
        if (onLogout) onLogout(); // Close dropdown if passed
        navigate("/"); // Redirect to home
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
