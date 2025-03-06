import { useState } from "react";
import { useNavigate } from "react-router-dom";
import IncomeForm from "../Components/Forms/IncomeForm";

const createIncome = (income) => {
 
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
    
    const [loading, setLoading] = useState(false);
  
    const handleCreateIncome = (income) => {
      setLoading(true);
  
      createIncome(income)
        .then(() => {
          setLoading(false);
          onCancel();
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