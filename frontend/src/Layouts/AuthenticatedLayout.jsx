import React, { useEffect, useRef, useState } from 'react';
import { useNavigate, Outlet } from 'react-router-dom';
import { isUserLoggedIn } from '../Utils/Util';
import Navbar from '../Components/Navbar';


const AuthenticatedLayout = () => {
	const navigate = useNavigate();

	useEffect(() => {
		if (!isUserLoggedIn()) {
			navigate('/login');
		}
	}, [navigate]);



	const navbarRef = useRef(null);
	const [navbarHeight, setNavbarHeight] = useState(0);

	const updateNavbarHeight = () => {
		if (navbarRef.current) {
			setNavbarHeight(navbarRef.current.offsetHeight);
		}
	};
	useEffect(() => {
		updateNavbarHeight();

		window.addEventListener('resize', updateNavbarHeight);
		return () => window.removeEventListener('resize', updateNavbarHeight);
	}, []);

	return (
		<div>
			<Navbar updateNavbarHeight={updateNavbarHeight} navbarRef={navbarRef} />

			<div
				style={{ paddingTop: `${navbarHeight + 0}px` }}
				className="bg-[fbf2d5]"
			>
				<Outlet />
			</div>
		</div>
	);
};

export default AuthenticatedLayout;
