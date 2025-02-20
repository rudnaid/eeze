import React from 'react';
import { Link } from 'react-router-dom';



export const Homepage = () => {
	return (
		<>
		<div>
		<section id='hero' className="bg-[url(/hero-bg-image.png)] bg-no-repeat bg-cover bg-center h-200">
			{/* Hero container */}
			<div className="container max-w-6xl mx-auto px-6 py-12">
				{/* Menu/Logo container */}
				<nav className="flex igems-center justify-between font-bold text-white">
				<Link to="/home">
					<h1 className="ml-7 text-4xl text-[#fdc57b]">eeze</h1>
				</Link>
				<div className="hidden h-10 md:flex md:space-x-8">
					<div className="group text-[#fdc57b]">
						<Link>
							<h3>About</h3>
						</Link>
						<div className='mx-2 group-hover:border-b group-hover:border-blue-50'></div>
					</div>
					<div className="group text-[#fdc57b]">
						<Link to="/login">
							<h3>Login</h3>
						</Link>
						<div className='mx-2 group-hover:border-b group-hover:border-blue-50'></div>
					</div>
					<div className="group text-[#fdc57b]">
						<Link to="/register">
							<h3>Register</h3>
						</Link>
						<div className='mx-2 group-hover:border-b group-hover:border-blue-50'></div>
					</div>

				</div>
				<div className="md:hidden">
					<button id='menu-tbn' type='button' className="z-40 block hamburger md:hidden focus:outline-none cursor:pointer w-24 h-24 transition-all duration-250 relative">
						<span className='hamburger-top'></span>
						<span className='hamburger-middle translate-y-[7px]'></span>
						<span className='hamburger-bottom absolute w-6 h-0.5 top-0 left-0 translate-y-[14px] bg-white transform rotate-0 transition-all duration-500'></span>
					</button>
				</div>
				
				</nav>
				
			</div>
			<div className="max-w-sm mt-32 mb-32 ml-10 p-10 text-5xl tracking-wide text-[#fdc57b] uppercase border-2 md:max-w-lg md:ml-16 md:p-10 md:m-32 md:mx-0 md:text-6xl break-words">
					Finances <br /> Made EEZE
			
				</div>
				
	  	</section>
		</div>
		
		

		</>
		
)};
