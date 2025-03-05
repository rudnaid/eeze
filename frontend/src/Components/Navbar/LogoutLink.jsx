import React from 'react';
import { Link } from 'react-router-dom';

const LogoutLink = () => {
	const handleLogout = (e) => {
		e.preventDefault();
		localStorage.removeItem('jwt');
	};
	return (
		<Link
			to="/"
			onClick={handleLogout}
	
		>
			Log out
		</Link>
	);
};

export default LogoutLink;
