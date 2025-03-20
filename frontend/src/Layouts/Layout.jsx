import {useEffect, useRef, useState} from 'react';
import {Outlet} from 'react-router-dom';
import Navbar from '../Components/Navbar/Navbar';
import Footer from '../Components/Footer/Footer';
import {useAuth} from "../Context/AuthContext.jsx";

const Layout = () => {
  const navbarRef = useRef(null);
  const [navbarHeight, setNavbarHeight] = useState(0);
  const {user} = useAuth();

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
      {user ? <Navbar updateNavbarHeight={updateNavbarHeight} navbarRef={navbarRef}/> : null}

      <main style={{ paddingTop: `${navbarHeight}px` }} className="flex-grow">
        <Outlet />
      </main>

      {user ? <Footer/> : null}

    </div>
  );
};

export default Layout;
