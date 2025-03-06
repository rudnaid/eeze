import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import './index.css';
import { Expenses } from './Pages/Expenses.jsx';
import Income from './Pages/Income.jsx';
import IncomeCreator from './Pages/IncomeCreator.jsx';
import ExpenseCreator from './Pages/ExpenseCreator.jsx';
import Homepage from './Pages/Homepage.jsx';
import Login from './Pages/Login.jsx';
import Register from './Pages/Register.jsx';
import PublicLayout from './Layouts/PublicLayout.jsx';
import AuthenticatedLayout from './Layouts/AuthenticatedLayout.jsx';
import LandingPage from './Pages/LandingPage.jsx';

const router = createBrowserRouter([
	{
		path: '/',
		element: <PublicLayout />, 
		children: [
			{
				index: true,
				element: <Homepage />,
			},
			{
				path: 'login',
				element: <Login />,
			},
			{
				path: 'register',
				element: <Register />,
			},
		],
	},

	{
		path: '/home',
		element: <AuthenticatedLayout />, 
		children: [
			{
				index: true,
				element: <LandingPage />,
			},
			{
				path: 'expenses',
				element: <Expenses />,
			},
			{
				path: 'expenses/create',
				element: <ExpenseCreator />,
			},
			{
				path: 'income',
				element: <Income />,
			},
			{
				path: 'income/create',
				element: <IncomeCreator />,
			},
		],
	},
]);

createRoot(document.getElementById('root')).render(
	<StrictMode>
		<RouterProvider router={router} />
	</StrictMode>
);
