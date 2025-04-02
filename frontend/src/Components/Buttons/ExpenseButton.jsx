import Modal from "../Util/Modal.jsx";
import ExpenseCreator from "../../Pages/ExpenseCreator.jsx";
import {useState} from "react";

const ExpenseButton = () => {
  const [expenseModal, setExpenseModal] = useState(false);

  return (
    <div>
      <div>
        <button onClick={() => setExpenseModal(true)} className="text-white bg-[#7e8283] hover:bg-[#7fa99b] font-semibold py-2 px-4 rounded transition">
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
