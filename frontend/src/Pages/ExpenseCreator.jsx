import { useState } from "react";
import { useNavigate } from "react-router-dom";
import ExpenseForm from "../Components/Forms/ExpenseForm";

const createExpense = (expense) => {
    // change to your own generated database-s no1 members public id!

    return fetch("/api/expenses?memberPublicId=03204898-c76f-45c1-8d50-6a022bdb0355", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(expense),
    }).then((res) => res.json());
  };

const ExpenseCreator = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
  
    const handleCreateExpense = (expense) => {
      setLoading(true);
  
      createExpense(expense)
        .then(() => {
          setLoading(false);
          navigate("/expenses");
        })
    };
  
    return (
      <ExpenseForm
        onCancel={() => navigate("/")}
        disabled={loading}
        onSave={handleCreateExpense}
      />
    );
  };
  
  export default ExpenseCreator;