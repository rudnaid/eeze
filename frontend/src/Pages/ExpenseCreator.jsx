import {useState} from "react";
import ExpenseForm from "../Components/Forms/ExpenseForm";
import {postNewExpense} from "../Services/apiService.js";
import {useAuth} from "../Context/AuthContext.jsx";
import Loading from "../Components/Loading/Loading.jsx";

const ExpenseCreator = ({onCancel}) => {
  const {user} = useAuth();
  const [loading, setLoading] = useState(false);

  const handleCreateExpense = async (expense) => {
    setLoading(true);

    await postNewExpense(user, expense);

    setLoading(false);
  };

  if (loading) return <Loading/>;

  return (
    <ExpenseForm
      onCancel={(onCancel)}
      disabled={loading}
      onSave={handleCreateExpense}
    />
  );
};

export default ExpenseCreator;
