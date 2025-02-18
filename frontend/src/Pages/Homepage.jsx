import React from 'react';
import backgroundImage from "../assets/homepage-bg-pic.jpg";


export const Homepage = () => {
	return (
		<div>
		<div className="flex justify-center items-center bg-[#394a51] bg-opacity-50 h-screen bg-cover bg-center bg-no-repeat" style={{ backgroundImage: `url(${backgroundImage})` }}>
		  {/* <h2 className="text-4xl text-white font-bold">Track Your Expenses, Effortlessly</h2> */}
		</div>
	  </div>
)};
