import React from "react";

const JwtTokenExpiredModal = ({ onClose }) => {
    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
            <div className="bg-white p-6 rounded-lg shadow-lg max-w-sm text-center">
                <h2 className="text-lg font-bold mb-2">Session Expired</h2>
                <p>Your session has expired. Please log in again.</p>
                <button
                    onClick={onClose}
                    className="mt-4 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                >
                    OK
                </button>
            </div>
        </div>
    );
};

export default JwtTokenExpiredModal;