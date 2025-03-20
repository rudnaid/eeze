import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    if (config.user && config.user.jwt) {
      config.headers['Authorization'] = `Bearer ${config.user.jwt}`;
      delete config.user;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export const fetchMonthlyExpenseReport = async (user, month, year) => {
  try {
    const response = await api.get("/reports/monthly", {
      params: { month, year },
      user
    });

    return response.data;
  } catch (error) {
    console.error('Error getting monthly expense report:', error);
  }
};

export const fetchTotalIncomes = async (user) => {
  try {
    const response = await api.get("/incomes", {
      user
    });

    return response.data;
  } catch (error) {
    console.error('Error getting all incomes report:', error);
  }
};

export const fetchSummary = async (user) => {
  try {
    const response = await api.get("/reports", {
      user
    });

    return response.data;
  } catch (error) {
    console.error('Error getting summary:', error);
  }
};

export const fetchExpensesByCategory = async (user) => {
  try {
    const response = await api.get("/reports/by-category", {
      user
    });

    return response.data;
  } catch (error) {
    console.error('Error getting expenses by category:', error);
  }
};

export const fetchYearlyIncomeExpenseReport = async (user, year) => {
  try {
    const response = await api.get("/reports/yearly", {
      params: { year },
      user
    });

    return response.data;
  } catch (error) {
    console.error('Error getting yearly expense report:', error);
  }
}
