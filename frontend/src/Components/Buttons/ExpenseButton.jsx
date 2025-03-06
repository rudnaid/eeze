import Modal from "../Util/Modal.jsx";
import ExpenseCreator from "../../Pages/ExpenseCreator.jsx";
import {useState} from "react";

const ExpenseButton = () => {
  const [expenseModal, setExpenseModal] = useState(false);

  return (
    <div>
      <div>
        <button onClick={() => setExpenseModal(true)} className="w-full shadow-xl py-2.5 px-4 text-sm font-semibold tracking-wider rounded-md text-white bg-gray-800 hover:bg-gray-500 focus:outline-none transition-all">
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
