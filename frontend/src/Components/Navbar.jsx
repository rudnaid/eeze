import React, { useEffect, useState } from 'react';
import { RxHamburgerMenu } from "react-icons/rx";
import { Link } from "react-router-dom";

const ROUTES = [
  { name: "Home", path: "/" },
  { name: "Balance", path: "/" },
  { name: "Income", path: "/income" },
  { name: "Expenses", path: "/expenses" },
];

const Navbar = ({ navbarRef, updateNavbarHeight }) => {
const [isMobileMenuShown, setIsMobileMenuShown] = useState(false);

  useEffect(() => {
    updateNavbarHeight()
  },[isMobileMenuShown]);

  return (
    <nav ref={navbarRef} className='fixed top-0 left-0 w-full bg-[#7fa99b] shadow-md p-4 flex flex-wrap justify-between items-center z-50'>
      <a href="#" className="text-4xl text-white font-[sans-serif]">eeze</a>

      {/* Hamburger button */}
      <button
        onClick={() => setIsMobileMenuShown(!isMobileMenuShown)}
        className="rounded-lg p-2 hover:bg-gray-100 focus:ring-2 focus:ring-gray-200 lg:hidden"
      >
        <RxHamburgerMenu size={25} />
      </button>

      {/* Mobile Menu toggle */}
      <div className={`${!isMobileMenuShown && 'hidden'} w-full lg:block lg:w-auto`}>
        <ul className="flex flex-col lg:flex-row rounded-lg border border-gray-100 bg-gray-50 p-4 text-lg lg:space-x-8 lg:border-none lg:bg-transparent">
          {ROUTES.map((route, i) => (
            <li key={route} className={`cursor-pointer rounded px-3 py-2 
              ${i === 0 ? "bg-[#7fa99b] text-white lg:bg-transparent lg:text-white hover:bg-gray-500" : "hover:bg-gray-300"}`}>
              <Link to={route.path}>{route.name}</Link> 
            </li>
          ))}
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;