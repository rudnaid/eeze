import Modal from "../Util/Modal.jsx";
import IncomeCreator from "../../Pages/IncomeCreator.jsx";
import {useState} from "react";

const IncomeButton = () => {
  const [incomeModal, setIncomeModal] = useState(false);

  return (
    <>
      <div>
        <button onClick={() => setIncomeModal(true)}
                className="w-full py-2.5 px-4 text-sm font-semibold rounded-lg text-gray-700 bg-[#fff8ef] border border-[#fdc57b] hover:bg-[#fff0da] transition duration-200 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          Add new income
        </button>

        <Modal openModal={incomeModal} closeModal={() => setIncomeModal(false)} redirectTo={"/home"}>
          {(handleClose) => <IncomeCreator onCancel={handleClose} />}
        </Modal>
      </div></>
  )
}

export default IncomeButton;
