import React from 'react';
import { Link } from 'react-router-dom';
import { FaFacebook, FaLinkedin, FaArrowUp } from 'react-icons/fa';
import { FaYoutube } from 'react-icons/fa';

const Footer = () => {
	const scrollToTop = () => {
		window.scrollTo({ top: 0, behavior: 'smooth' });
	};

	return (
		<footer className="bg-[#394a51] text-[#fbf2d5] p-10 pb-10 flex flex-col items-center">
			{/* Footer Links */}
			<div className="w-full max-w-4xl flex justify-center space-x-30 pb-6">
				<Link className="link link-hover" to="/about">
					About us
				</Link>
				<Link className="link link-hover" to="/contact">
					Contact
				</Link>
				<Link className="link link-hover" to="/privacy-policy">
					Privacy Policy
				</Link>
				<Link className="link link-hover" to="/sitemap">
					Sitemap
				</Link>
				<Link className="link link-hover" to="/help">
					Help
				</Link>
			</div>

			{/* Social Media Icons */}
			<div className="flex space-x-6 pb-4">
				<a
					href="https://www.youtube.com"
					target="_blank"
					rel="noopener noreferrer"
				>
					<FaYoutube className="w-6 h-6 hover:text-red-400 transition duration-300" />
				</a>
				<a
					href="https://facebook.com"
					target="_blank"
					rel="noopener noreferrer"
				>
					<FaFacebook className="text-xl hover:text-blue-500 transition duration-300" />
				</a>
				<a
					href="https://linkedin.com"
					target="_blank"
					rel="noopener noreferrer"
				>
					<FaLinkedin className="text-xl hover:text-blue-600 transition duration-300" />
				</a>
			</div>

			{/* Copyright Section */}
			<div className="w-full max-w-4xl text-center pt-4">
				<p>
					Copyright © {new Date().getFullYear()} - All rights reserved by EEZE
				</p>
			</div>

			{/* Back to Top Button */}
			<button
				onClick={scrollToTop}
				className="mt-4 flex items-center space-x-2 text-sm text-[#fbf2d5] hover:text-white transition duration-300 pt-5"
			>
				<FaArrowUp /> <span>Back to Top</span>
			</button>
		</footer>
	);
};

export default Footer;
