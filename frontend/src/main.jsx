import {StrictMode} from 'react';
import {createRoot} from 'react-dom/client';
import {createBrowserRouter, RouterProvider} from 'react-router-dom';

import './index.css';
import {Expenses} from './Pages/Expenses.jsx';
import Income from './Pages/Income.jsx';
import IncomeCreator from './Pages/IncomeCreator.jsx';
import ExpenseCreator from './Pages/ExpenseCreator.jsx';
import Homepage from './Pages/Homepage.jsx';
import Login from './Pages/Login.jsx';
import Register from './Pages/Register.jsx';
import LandingPage from './Pages/LandingPage.jsx';
import {AuthProvider} from "./Context/AuthContext.jsx";
import Layout from "./Layouts/Layout.jsx";
import ProtectedRoute from "./Components/Util/ProtectedRoute.jsx";

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout/>,
    children: [
      {
        index: true,
        element: <Homepage/>,
      },
      {
        path: 'login',
        element: <Login/>,
      },
      {
        path: 'register',
        element: <Register/>,
      },
      {
        index: true,
        path: 'home',
        element: (
          <ProtectedRoute>
            <LandingPage/>
          </ProtectedRoute>
        ),
      },
      {
        path: '/expenses',
        element: (
          <ProtectedRoute>
            <Expenses/>
          </ProtectedRoute>
        ),
      },
      {
        path: '/expenses/create',
        element: (
          <ProtectedRoute>
            <ExpenseCreator/>
          </ProtectedRoute>
        ),
      },
      {
        path: 'incomes',
        element: (
          <ProtectedRoute>
            <Income/>
          </ProtectedRoute>
        ),
      },
      {
        path: 'incomes/create',
        element: (
          <ProtectedRoute>
            <IncomeCreator/>
          </ProtectedRoute>
        ),
      },
    ],
  },
]);

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AuthProvider>
      <RouterProvider router={router}/>
    </AuthProvider>
  </StrictMode>
);
