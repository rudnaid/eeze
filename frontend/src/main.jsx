import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import './index.css';
import Layout from './Pages/Layout.jsx';
import { Homepage } from './Pages/Homepage.jsx';
import { Expenses } from './Pages/Expenses.jsx';
import Income from './Pages/Income.jsx';
import Login from './Pages/Login.jsx';
import Register from './Pages/Register.jsx';
import Homepage2 from './Pages/Homepage2.jsx';
import Login2 from './Pages/Login2.jsx';
import Register2 from './Pages/Register2.jsx';

const router = createBrowserRouter([
	{
		path: '/',
		element: <Layout />,
		children: [
			// {
			// 	path: '/home',
			// 	element: <Homepage />,
			// },
			{
				path: '/expenses',
				element: <Expenses />,
			},
			{
				path: '/income',
				element: <Income />
			}
		],
	},
	{
		path: '/home1',
		element: <Homepage />

	},
	{
		path: '/home',
		element: <Homepage2 />

	},
	{
		path: '/login1',
		element: <Login />,
	},
	{
		path: '/login',
		element: <Login2 />,
	},
	{
		path: '/register1',
		element: <Register />,
	},
	{
		path: '/register',
		element: <Register2 />,
	},
]);

createRoot(document.getElementById('root')).render(
	<StrictMode>
		<RouterProvider router={router} />
	</StrictMode>
);
