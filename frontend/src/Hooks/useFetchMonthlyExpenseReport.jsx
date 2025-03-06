import {useEffect, useState} from "react";
import {fetchMonthlyExpenseReport} from "../Services/apiService.js";

export const useFetchMonthlyExpenseReport = (selectedMonth = null, selectedYear = null) => {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [monthlyExpenseReportData, setMonthlyExpenseReportData] = useState(null);

  const currentDate = new Date();
  const currentMonth = currentDate.getMonth() + 1;
  const currentYear = currentDate.getFullYear();

  const month = selectedMonth ?? currentMonth;
  const year = selectedYear ?? currentYear;

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        setError(null);

        const result = await fetchMonthlyExpenseReport(month, year);
        setMonthlyExpenseReportData(result);
      } catch (error) {
        setError(error);
        console.error('Error getting monthly report:', error);
      } finally {
        setLoading(false);
      }
    }

    fetchData();
  }, [month, year]);

  return { loading, error, monthlyExpenseReportData, month, year };
}
