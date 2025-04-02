import Modal from "../Util/Modal.jsx";
import IncomeCreator from "../../Pages/IncomeCreator.jsx";
import {useState} from "react";

const IncomeButton = () => {
  const [incomeModal, setIncomeModal] = useState(false);

  return (
    <>
      <div>
        <button onClick={() => setIncomeModal(true)}
                className="text-white bg-[#7e8283] hover:bg-[#7fa99b] font-semibold py-2 px-4 rounded transition"
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
