import { useState } from "react";
import { useNavigate } from "react-router-dom";
import IncomeForm from "../Components/Forms/IncomeForm";

const createIncome = (income) => {
  // change to your own generated database-s no1 members public id!
    const token = localStorage.getItem('jwt');
    return fetch("/api/incomes", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
      body: JSON.stringify(income),
    }).then((res) => res.json());
  };

const IncomeCreator = ({onCancel}) => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
  
    const handleCreateIncome = (income) => {
      setLoading(true);
  
      createIncome(income)
        .then(() => {
          setLoading(false);
          navigate("/incomes");
        })
    };
  
    return (
      <IncomeForm
        onCancel={onCancel}
        disabled={loading}
        onSave={handleCreateIncome}
      />
    );
  };
  
  export default IncomeCreator;