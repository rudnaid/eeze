import React from 'react'
import Navbar from '../Components/Navbar'
import { Outlet } from "react-router-dom";



const Layout = () => {
  return (
    <div>
        <Navbar />
        <main className='pt-16 p-4'> 
        {/* pt-16 (padding-top: 4rem) → Pushes content below the fixed navbar, because Navbar has fix position, without this it would displayed behind*/}
            <Outlet />
        </main>
    </div>
  )
}

export default Layout