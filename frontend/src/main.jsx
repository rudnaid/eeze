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
import IncomeCreator from './Pages/IncomeCreator.jsx';
import ExpenseCreator from './Pages/ExpenseCreator.jsx';

const router = createBrowserRouter([
	{
		path: '/',
		element: <Layout />,
		children: [
			{
				path: '/',
				element: <Homepage />,
			},
			{
				path: '/expenses',
				element: <Expenses />,
			},
			{
				path: '/expenses/create',
				element: <ExpenseCreator />,
			},
			{
				path: '/income',
				element: <Income />
			},
			{
				path: '/income/create',
				element: <IncomeCreator />
			},
		],
	},
	{
		path: '/login',
		element: <Login />,
	},
	{
		path: '/register',
		element: <Register />,
	},
]);

createRoot(document.getElementById('root')).render(
	<StrictMode>
		<RouterProvider router={router} />
	</StrictMode>
);
