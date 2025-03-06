import { useState } from "react";
import { redirect, useNavigate } from "react-router-dom";
import ExpenseForm from "../Components/Forms/ExpenseForm";

const createExpense = (expense) => {
    // change to your own generated database-s no1 members public id!
    const token = localStorage.getItem('jwt');
    return fetch("/api/expenses", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
      body: JSON.stringify(expense),
    }).then((res) => res.json());
  };

const ExpenseCreator = ({onCancel}) => {
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
        onCancel={(onCancel)}
        disabled={loading}
        onSave={handleCreateExpense}
      />
    );
  };
  
  export default ExpenseCreator;