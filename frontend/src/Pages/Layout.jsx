import React from 'react'
import Navbar from '../Components/Navbar'
import { Outlet } from "react-router-dom";



const Layout = () => {
  return (
    <div>
        <Navbar />
        <main className=''>
            <Outlet />
        </main>
    </div>
  )
}

export default Layout