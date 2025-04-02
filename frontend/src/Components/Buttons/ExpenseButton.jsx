import Modal from "../Util/Modal.jsx";
import ExpenseCreator from "../../Pages/ExpenseCreator.jsx";
import {useState} from "react";

const ExpenseButton = () => {
  const [expenseModal, setExpenseModal] = useState(false);

  return (
    <div>
      <div>
        <button onClick={() => setExpenseModal(true)} className="w-full py-2.5 px-4 text-sm font-semibold rounded-lg text-gray-700 bg-[#fff8ef] border border-[#fdc57b] hover:bg-[#fff0da] transition duration-200 disabled:opacity-50 disabled:cursor-not-allowed">
          Add new expense
        </button>

        <Modal openModal={expenseModal} closeModal={() => setExpenseModal(false)} redirectTo={"/home"}>
          {(handleClose) => <ExpenseCreator onCancel={handleClose} />}
        </Modal>
      </div>
    </div>
  )
}

export default ExpenseButton;
