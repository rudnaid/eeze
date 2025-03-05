import PieChartComponent from "../Components/Charts/PieChartComponent.jsx";
import Loading from "../Components/Loading/Loading.jsx";
import {useFetchMonthlyExpenseReport} from "../Hooks/useFetchMonthlyExpenseReport.jsx";
import ErrorComponent from "../Components/Util/ErrorComponent.jsx";

const LandingPage = () => {
  const { loading, error, monthlyExpenseReportData } = useFetchMonthlyExpenseReport();

  if (loading) return <Loading />;
  if (error) return <ErrorComponent message={error} />;

  return (
    <>
      <PieChartComponent chartData={monthlyExpenseReportData}/>
    </>
  )
}

export default LandingPage;
