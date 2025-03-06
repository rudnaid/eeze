import axios from 'axios';

export const fetchMonthlyExpenseReport = async (month, year) => {
  try {
    const token = localStorage.getItem('jwt');

    const response = await axios.get("/api/reports/monthly", {
      params: {
        month: month,
        year: year,
      },
      headers: {
        Authorization: `Bearer ${token}`,
      }
    });

    return response.data;
  } catch (error) {
    console.error('Error getting monthly expense report:', error);
  }
};

export const fetchTotalIncomes = async () => {
  try {
    const token = localStorage.getItem("jwt");
    const response = await axios.get("/api/incomes", {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    });

    return response.data;
  } catch (error) {
    console.error('Error getting all incomes report:', error);
  }
};

export const fetchSummary = async () => {
  try {
    const token = localStorage.getItem("jwt");
    const response = await axios.get("/api/reports", {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    });

    return response.data;
  } catch (error) {
    console.error('Error getting summary:', error);
  }
};

export const fetchExpensesByCategory = async () => {
  try {
    const token = localStorage.getItem("jwt");
    const response = await axios.get("/api/reports/by-category", {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    });

    return response.data;
  } catch (error) {
    console.error('Error getting expenses by category:', error);
  }
};

export const fetchYearlyIncomeExpenseReport = async (year) => {
  try {
    const token = localStorage.getItem('jwt');

    const response = await axios.get("/api/reports/yearly", {
      params: {
        year: year,
      },
      headers: {
        Authorization: `Bearer ${token}`,
      }
    });
    return response.data;
  } catch (error) {
    console.error('Error getting yearly expense report:', error);
  }
}
