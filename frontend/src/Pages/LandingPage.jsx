import PieChartComponent from "../Components/Charts/PieChartComponent.jsx";
import Loading from "../Components/Loading/Loading.jsx";
import { useFetchMonthlyExpenseReport } from "../Hooks/useFetchMonthlyExpenseReport.jsx";
import ErrorComponent from "../Components/Util/ErrorComponent.jsx";
import { useState } from "react";
import Modal from "../Components/Util/Modal.jsx";
import IncomeCreator from "./IncomeCreator.jsx";
import ExpenseCreator from "./ExpenseCreator.jsx";

const LandingPage = () => {
  const { loading, error, monthlyExpenseReportData } = useFetchMonthlyExpenseReport();
  const [incomeModal, setIncomeModal] = useState(false);
  const [expenseModal, setExpenseModal] = useState(false);

  if (loading) return <Loading />;
  if (error) return <ErrorComponent message={error} />;

  return (
    <>
      <div>
        {monthlyExpenseReportData && <PieChartComponent chartData={monthlyExpenseReportData} />}
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
  )
}

export default LandingPage;
