import React, { useEffect, useRef, useState } from 'react';
import Navbar from '../Components/Navbar';
import { Outlet } from "react-router-dom";

const Layout = () => {
  const navbarRef = useRef(null);
  const [navbarHeight, setNavbarHeight] = useState(0);

  const updateNavbarHeight = () => {
    if (navbarRef.current) {
      setNavbarHeight(navbarRef.current.offsetHeight);
    }
  };
  useEffect(() => {
    
    updateNavbarHeight();

    window.addEventListener("resize", updateNavbarHeight);
    return () => window.removeEventListener("resize", updateNavbarHeight);
  }, []);

  return (
    <div>
      <Navbar updateNavbarHeight={updateNavbarHeight} navbarRef={navbarRef}  />
      
      <main style={{ paddingTop: `${navbarHeight + 0}px`}} className='bg-[fbf2d5]'>
        <Outlet />
      </main>
    </div>
  );
};

export default Layout;