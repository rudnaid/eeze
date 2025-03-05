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
}
