import PieChartComponent from "../Components/Charts/PieChartComponent.jsx";
import Loading from "../Components/Loading/Loading.jsx";
import ErrorComponent from "../Components/Util/ErrorComponent.jsx";
import Modal from "../Components/Util/Modal.jsx";
import IncomeCreator from "./IncomeCreator.jsx";
import ExpenseCreator from "./ExpenseCreator.jsx";
import BarChartComponent from "../Components/Charts/BarChartComponent.jsx";
import {useFetchMonthlyExpenseReport} from "../Hooks/useFetchMonthlyExpenseReport.jsx";
import useFetchYearlyIncomeExpenseReport from "../Hooks/useFetchYearlyIncomeExpenseReport.jsx";
import {useState} from "react";

const LandingPage = () => {
  const [incomeModal, setIncomeModal] = useState(false);
  const [expenseModal, setExpenseModal] = useState(false);
  
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

      <div>
        <div>
          <button onClick={() => setIncomeModal(true)} className="w-full shadow-xl py-2.5 px-4 text-sm font-semibold tracking-wider rounded-md text-white bg-gray-800 hover:bg-gray-500 focus:outline-none transition-all">
            Add new income
          </button>

          <Modal openModal={incomeModal} closeModal={() => setIncomeModal(false)} redirectTo={"/home"}>
          {(handleClose) => <IncomeCreator onCancel={handleClose} />}
          </Modal>
        </div>
        <div>
          <button onClick={() => setExpenseModal(true)} className="w-full shadow-xl py-2.5 px-4 text-sm font-semibold tracking-wider rounded-md text-white bg-gray-800 hover:bg-gray-500 focus:outline-none transition-all">
            Add new expense
          </button>

          <Modal openModal={expenseModal} closeModal={() => setExpenseModal(false)} redirectTo={"/home"}>
          {(handleClose) => <ExpenseCreator onCancel={handleClose} />}
          </Modal>
        </div>
      </div>
    </>
  );
};

export default LandingPage;
