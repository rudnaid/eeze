import { useState } from "react";
import { useNavigate } from "react-router-dom";
import IncomeForm from "../Components/Forms/IncomeForm";

const createIncome = (income) => {
  // change to your own generated database-s no1 members public id!
  
    return fetch("/api/income/?memberPublicId=7c873e71-164b-42f8-a6d9-3aa350172c2f", {
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