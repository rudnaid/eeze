import React, { useEffect, useRef, useState } from 'react';
import { useNavigate, Outlet } from 'react-router-dom';
import { isUserLoggedIn } from '../Utils/Util';
import Navbar from '../Components/Navbar/Navbar';
import Footer from "../Components/Footer/Footer";

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
		<div className="flex flex-col min-h-screen">
			<Navbar updateNavbarHeight={updateNavbarHeight} navbarRef={navbarRef} />
			
			<main style={{ paddingTop: `${navbarHeight}px` }} className="flex-grow">
				<Outlet />
			</main>

			<Footer />
		</div>
	);
};

export default AuthenticatedLayout;