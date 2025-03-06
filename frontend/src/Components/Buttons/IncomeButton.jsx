import Modal from "../Util/Modal.jsx";
import IncomeCreator from "../../Pages/IncomeCreator.jsx";
import {useState} from "react";

const IncomeButton = () => {
  const [incomeModal, setIncomeModal] = useState(false);

  return (
    <>
      <div>
        <button onClick={() => setIncomeModal(true)} className="w-full shadow-xl py-2.5 px-4 text-sm font-semibold tracking-wider rounded-md text-white bg-gray-800 hover:bg-gray-500 focus:outline-none transition-all">
          Add new income
        </button>

        <Modal openModal={incomeModal} closeModal={() => setIncomeModal(false)} redirectTo={"/home"}>
          {(handleClose) => <IncomeCreator onCancel={handleClose} />}
        </Modal>
      </div></>
  )
}

export default IncomeButton;
