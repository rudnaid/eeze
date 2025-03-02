import React from 'react';
import { Link } from 'react-router-dom';

const Homepage2 = () => {
	return (
		<>
			<div>
				<section
					id="hero"
					className="bg-[url(/green-circles.png)] bg-no-repeat bg-cover bg-center h-200"
				>
					{/* Hero container */}
					<div className="container max-w-6xl mx-auto px-6 py-12">
						{/* Menu/Logo container */}
						<nav className="flex items-center justify-between font-bold text-white">
							<Link to="/home">
								<h1 className="ml-7 text-5xl text-[#fdc57b]">eeze</h1>
							</Link>
							<div className="hidden h-10 text-2xl md:flex md:space-x-8">
								<div className="border border-transparent text-[#fdc57b] hover:border-[#fdc57b] hover:text-[#fdc57b] px-3 py-1 rounded transition-all duration-300">
									<a href="#about">About</a>
								</div>
								<div className="border border-transparent text-[#fdc57b] hover:border-[#fdc57b] hover:text-[#fdc57b] px-3 py-1 rounded transition-all duration-300 ">
									<Link to="/login">
										<h3 className="">Login</h3>
									</Link>
								</div>
								<div className="border border-transparent text-[#fdc57b] hover:border-[#fdc57b] hover:text-[#fdc57b] px-3 py-1 rounded transition-all duration-300">
									<Link to="/register">
										<h3>Register</h3>
									</Link>
								</div>
							</div>
							<div className="md:hidden">
								<button
									id="menu-tbn"
									type="button"
									className="z-40 block hamburger md:hidden focus:outline-none cursor:pointer w-24 h-24 transition-all duration-250 relative"
								>
									<span className="hamburger-top"></span>
									<span className="hamburger-middle translate-y-[7px]"></span>
									<span className="hamburger-bottom absolute w-6 h-0.5 top-0 left-0 translate-y-[14px] bg-white transform rotate-0 transition-all duration-500"></span>
								</button>
							</div>
						</nav>
					</div>
					<div className="max-w-sm mt-32 mb-32 ml-10 p-10 text-5xl tracking-wide text-[#fdc57b] uppercase border-2 md:max-w-lg md:ml-16 md:p-10 md:m-32 md:mx-0 md:text-6xl break-words">
						Finances <br /> Made EEZE
						<p className="text-xl lowercase mt-4">
							Stay on top of your expenses and make your money work for you
						</p>
					</div>
				</section>

				{/* Why Eeze Section */}
				<div
					id="why-eeze"
					className="bg-[#fdc57b] py-0 flex flex-col md:flex-row items-stretch w-full h-auto overflow-hidden"
				>
					<div className="w-full md:w-1/2 h-auto">
						<img
							src="/why_eeze.jpg"
							alt="Why Eeze"
							className="w-full h-auto object-cover opacity-70"
						/>
					</div>

					<div className="w-full text-justify md:w-1/2 flex flex-col justify-center p-8 pl-20 pr-20">
						<h2 className="text-4xl text-white mb-6">Why Eeze</h2>
						<p className="text-lg text-gray-700 leading-relaxed">
							Manage your money smarter with{' '}
							<span className="text-white"> Eeze </span>.
							<p>
								Track your expenses and incomes, organize them into categories,
								and gain insights through dynamic charts.
							</p>
							<p>
								Stay in control, make better financial decisions, and reach your
								financial goals effortlessly.
							</p>
						</p>
					</div>
				</div>

				{/* About Eeze Section */}
				<div
					id="about"
					className="bg-[#7fa99b] py-0 flex flex-col md:flex-row-reverse items-stretch w-full h-auto overflow-hidden"
				>
					<div className="w-full md:w-1/2 h-auto">
						<img
							src="/about_eeze.jpg"
							alt="About Eeze"
							className="w-full h-auto object-cover opacity-70"
						/>
					</div>

					<div className="w-full text-justify md:w-1/2 flex flex-col justify-center p-8 pl-20 pr-20">
						<h2 className="text-4xl text-white mb-6">About Eeze</h2>
						<p className="text-lg text-gray-700 leading-relaxed">
							We believe that financial tracking should be simple, stress-free,
							and even a little fun!
							<p>
								<span className="text-white"> Eeze </span> is designed to help
								you manage your money effortlessly. Whether you're tracking
								expenses, organizing your income, or analyzing your financial
								habits with interactive charts, we’ve got you covered.
							</p>
						</p>
					</div>
				</div>
			</div>
		</>
	);
};

export default Homepage2;
