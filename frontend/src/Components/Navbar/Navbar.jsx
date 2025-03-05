import React, { useEffect, useState } from 'react';
import { RxHamburgerMenu } from 'react-icons/rx';
import { Link, useNavigate } from 'react-router-dom';
import { ROUTES } from './NavbarLinks';
import LogoutLink from './LogoutLink';

const Navbar = ({ navbarRef, updateNavbarHeight }) => {
	const navigate = useNavigate();
	const [isMobileMenuShown, setIsMobileMenuShown] = useState(false);

	const handleLogout = () => {
		localStorage.removeItem('jwt');
		console.log('Ez a jwt: ', localStorage.getItem('jwt'));
		navigate('/');
	};

	useEffect(() => {
		updateNavbarHeight();
	}, [isMobileMenuShown]);

	return (
		<nav
			ref={navbarRef}
			className="fixed top-0 left-0 w-full bg-[#7fa99b] shadow-md p-4 flex flex-wrap justify-between items-center z-50"
		>
			<Link to="/home" className="text-4xl text-[#fdc57b] font-[sans-serif]">
				eeze
			</Link>

			{/* Hamburger button */}
			<button
				onClick={() => setIsMobileMenuShown(!isMobileMenuShown)}
				className="rounded-lg p-2 hover:bg-gray-100 focus:ring-2 focus:ring-gray-200 lg:hidden"
			>
				<RxHamburgerMenu size={25} />
			</button>

			{/* Mobile Menu toggle */}
			<div
				className={`${!isMobileMenuShown && 'hidden'} w-full lg:block lg:w-auto`}
			>
				<ul className="flex flex-col lg:flex-row rounded-lg border border-gray-100 bg-gray-50 p-4 text-lg lg:space-x-8 lg:border-none lg:bg-transparent">
					{ROUTES.map((route, i) => (
						<li
							key={route}
							className={`cursor-pointer rounded px-3 py-2 
              ${i === 0 ? 'border border-[#fdc57b] text-[#fdc57b] lg:bg-transparent lg:text-[#febd68] hover:text-gray-800' : 'hover:border border-[#fdc57b]'}`}
						>
							<Link to={route.path}>{route.name}</Link>
						</li>
					))}
					<li className="cursor-pointer rounded px-3 py-2 hover:border border-[#fdc57b]">
						<LogoutLink />
					</li>
				</ul>
			</div>
		</nav>
	);
};

export default Navbar;
