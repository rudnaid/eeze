import PieChartComponent from "../Components/Charts/PieChartComponent.jsx";
import Loading from "../Components/Loading/Loading.jsx";
import ErrorComponent from "../Components/Util/ErrorComponent.jsx";
import BarChartComponent from "../Components/Charts/BarCharComponent.jsx";
import {useFetchMonthlyExpenseReport} from "../Hooks/useFetchMonthlyExpenseReport.jsx";
import useFetchYearlyIncomeExpenseReport from "../Hooks/useFetchYearlyIncomeExpenseReport.jsx";

const LandingPage = () => {

  const {
    loading: monthlyLoading,
    error: monthlyError,
    monthlyExpenseReportData,
  } = useFetchMonthlyExpenseReport();

  const {
    loading: yearlyLoading,
    error: yearlyError,
    yearlyIncomeExpenseReportData,
  } = useFetchYearlyIncomeExpenseReport();

  const loading = monthlyLoading || yearlyLoading;
  const error = monthlyError || yearlyError;

  if (loading) return <Loading />;
  if (error) return <ErrorComponent message={error} />;

  return (
    <>
      <div>
        <BarChartComponent chartData={yearlyIncomeExpenseReportData} />
      </div>
      <div>
      <PieChartComponent chartData={monthlyExpenseReportData} />
      </div>
    </>
  );
};

export default LandingPage;
