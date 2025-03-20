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

export const loginUser = async (userData) => {
  try {
    const response = await api.post("/users/login", userData);

    return response.data;
  } catch (error) {
    console.error("Error:", error);
  }
}

export const getExpenseCategories = async (user) => {
  try {
    const response = await api.get("/categories", {
      user
    });

    return response.data;
  } catch (error) {
    console.error('Error getting categories:', error);
  }
}

export const postNewIncome = async (user, income) => {
  try {
    const response = await api.post("/incomes", income, {
      user
    });

    return response.data;
  } catch (error) {
    console.error('Error posting income:', error);
  }
}

export const postNewExpense = async (user, expense) => {
  try {
    const response = await api.post("/expenses", expense, {
      user
    });

    return response.data;
  } catch (error) {
    console.error('Error posting expense:', error);
  }
}

export const registerUser = async (user) => {
  try {
    const response = await api.post("/users/register", user);

    return response.data;
  } catch (error) {
    console.error('Error registering user:', error);
  }
}
