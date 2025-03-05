import axios from 'axios';

export const fetchMonthlyExpenseReport = async (month, year) => {
  try {
    const token = localStorage.getItem('token');

    const response = await axios.get("/api/reports/", {
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
}
