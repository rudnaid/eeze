import { useState } from "react";
import { useNavigate } from "react-router-dom";
import IncomeForm from "../Components/Forms/IncomeForm";

const createIncome = (income) => {
    return fetch("/api/income/", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(income),
    }).then((res) => res.json());
  };

const IncomeCreator = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
  
    const handleCreateIncome = (income) => {
      setLoading(true);
  
      createIncome(income)
        .then(() => {
          setLoading(false);
          navigate("/income");
        })
    };
  
    return (
      <IncomeForm
        onCancel={() => navigate("/")}
        disabled={loading}
        onSave={handleCreateIncome}
      />
    );
  };
  
  export default IncomeCreator;