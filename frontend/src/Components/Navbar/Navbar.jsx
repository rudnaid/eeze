import {useEffect, useState} from "react";
import {RxHamburgerMenu} from "react-icons/rx";
import {Link} from "react-router-dom";
import {ROUTES} from "./NavbarLinks";
import LogoutLink from "./LogoutLink";
import {FiUser} from "react-icons/fi";

const Navbar = ({ navbarRef, updateNavbarHeight }) => {
    const [isMobileMenuShown, setIsMobileMenuShown] = useState(false);
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);

    useEffect(() => {
        updateNavbarHeight();
    }, [isMobileMenuShown, updateNavbarHeight]);

    return (
        <nav
            ref={navbarRef}
            className="fixed top-0 left-0 w-full bg-[url(/green-circles.png)] shadow-md p-4 flex flex-wrap justify-between items-center z-50"
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
            <div className={`${!isMobileMenuShown && "hidden"} w-full lg:block lg:w-auto`}>
                <ul className="flex flex-col lg:flex-row rounded-lg border border-gray-100 bg-gray-50 p-4 text-lg lg:space-x-8 lg:border-none lg:bg-transparent">
                    {ROUTES.map((route, i) => (
                        <li
                            key={route}
                            className={`cursor-pointer rounded px-3 py-2 
                                ${
                                    i === 0
                                        ? "border border-[#fdc57b] text-[#fdc57b] lg:bg-transparent lg:text-[#febd68] hover:text-gray-800"
                                        : "hover:border border-[#fdc57b]"
                                }`}
                        >
                            <Link to={route.path}>{route.name}</Link>
                        </li>
                    ))}

                    {/* User Profile Dropdown */}
                    <li className="relative">
                        <button
                            onClick={() => setIsDropdownOpen(!isDropdownOpen)}
                            className="flex items-center p-2 rounded-full hover:bg-gray-200"
                        >
                            <FiUser size={24} />
                        </button>

                        {/* Dropdown Menu */}
                        {isDropdownOpen && (
                            <div className="absolute right-0 mt-2 w-40 bg-white border border-gray-300 rounded-lg shadow-lg">
                                <ul className="text-left">
                                    <li className="px-4 py-2 hover:bg-gray-100">
                                        <Link to="/profile">Your Profile</Link>
                                    </li>
                                    <li className="px-4 py-2 hover:bg-gray-100">
                                        <Link to="/settings">Settings</Link>
                                    </li>
                                    <li>
                                        <LogoutLink onLogout={() => setIsDropdownOpen(false)} />
                                    </li>
                                </ul>
                            </div>
                        )}
                    </li>
                </ul>
            </div>
        </nav>
    );
};

export default Navbar;