import { useState } from "react";
import { useNavigate } from "react-router-dom";
import IncomeForm from "../Components/Forms/IncomeForm";

const createIncome = (income) => {
  // change to your own generated database-s no1 members public id!
  
    return fetch("/api/income/?memberPublicId=03204898-c76f-45c1-8d50-6a022bdb0355", {
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