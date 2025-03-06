import {useEffect, useState} from "react";
import {fetchYearlyIncomeExpenseReport} from "../Services/apiService.js";

const useFetchYearlyIncomeExpenseReport = (selectedYear = null) => {
  const [yearlyIncomeExpenseReportData, setYearlyIncomeExpenseReportData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);

  const currentDate = new Date();
  const currentYear = currentDate.getFullYear();

  const year = selectedYear ?? currentYear;

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const result = await fetchYearlyIncomeExpenseReport(year);

        setYearlyIncomeExpenseReportData(result);
      } catch (error) {
        setError(error);
      } finally {
        setLoading(false);
      }
    }
    fetchData();
  }, [year]);

  return {loading, error, yearlyIncomeExpenseReportData, year};
}

export default useFetchYearlyIncomeExpenseReport;
