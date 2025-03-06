import PieChartComponent from "../Components/Charts/PieChartComponent.jsx";
import Loading from "../Components/Loading/Loading.jsx";
import ErrorComponent from "../Components/Util/ErrorComponent.jsx";
import BarChartComponent from "../Components/Charts/BarChartComponent.jsx";
import {useFetchMonthlyExpenseReport} from "../Hooks/useFetchMonthlyExpenseReport.jsx";
import useFetchYearlyIncomeExpenseReport from "../Hooks/useFetchYearlyIncomeExpenseReport.jsx";
import {useEffect} from "react";
import ExpenseButton from "../Components/Buttons/ExpenseButton.jsx";
import IncomeButton from "../Components/Buttons/IncomeButton.jsx";
import "./landingPage.css";
import ThemeToggle from "../Components/Buttons/ThemeToggle.jsx";

const LandingPage = () => {

  // useEffect needed temporarily to let DaisyUI theme affect this page
  // TODO need to remove if DaisyUI is applied to whole frontend

  useEffect(() => {
    document.documentElement.setAttribute("data-theme", "cupcake");

    return () => {
      document.documentElement.removeAttribute("data-theme");
    };
  }, []);

  const {
    loading: monthlyLoading,
    error: monthlyError,
    monthlyExpenseReportData,
    month: pieMonth,
    year: pieYear,
  } = useFetchMonthlyExpenseReport();

  const {
    loading: yearlyLoading,
    error: yearlyError,
    yearlyIncomeExpenseReportData,
    year: barYear,
  } = useFetchYearlyIncomeExpenseReport();

  const loading = monthlyLoading || yearlyLoading;
  const error = monthlyError || yearlyError;

  if (loading) return <Loading />;
  if (error) return <ErrorComponent message={error} />;


  return (
    <>

      <div className="relative min-h-screen p-6 flex flex-col items-center">
        <div className="absolute top-4 right-4">
          <ThemeToggle />
        </div>

        <div className="w-full flex justify-center mb-8">
          <BarChartComponent chartData={yearlyIncomeExpenseReportData} year={barYear} />
        </div>

        <div className="flex justify-center items-start gap-8 w-full max-w-4xl">
          <div className="w-1/2">
            <PieChartComponent chartData={monthlyExpenseReportData} month={pieMonth} year={pieYear} />
          </div>

          <div className="flex flex-col space-y-4">
            <IncomeButton />
            <ExpenseButton />
          </div>
        </div>
      </div>

    </>
  );
};

export default LandingPage;
