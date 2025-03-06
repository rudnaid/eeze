import React, { useEffect, useRef, useState } from 'react';
import { useNavigate, Outlet } from 'react-router-dom';
import { isUserAuthenticated } from '../Utils/Util';
import Navbar from '../Components/Navbar/Navbar';
import Footer from '../Components/Footer/Footer';
import JwtTokenExpiredModal from '../Components/Util/JwtTokenExpiredModal';

const AuthenticatedLayout = () => {
	const navigate = useNavigate();
	const [showModal, setShowModal] = useState(false);
	const navbarRef = useRef(null);
	const [navbarHeight, setNavbarHeight] = useState(0);

	useEffect(() => {
		if (!isUserAuthenticated()) {
			setShowModal(true);
		}
	}, []);

	const handleClose = () => {
		localStorage.removeItem('jwt');
		setShowModal(false);
		navigate('/');
	};

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
			{showModal && <JwtTokenExpiredModal onClose={handleClose} />}
			<Navbar updateNavbarHeight={updateNavbarHeight} navbarRef={navbarRef} />

			<main style={{ paddingTop: `${navbarHeight}px` }} className="flex-grow">
				<Outlet />
			</main>

			<Footer />
		</div>
	);
};

export default AuthenticatedLayout;
