import {useState} from "react";
import IncomeForm from "../Components/Forms/IncomeForm";
import {useAuth} from "../Context/AuthContext.jsx";
import {postNewIncome} from "../Services/apiService.js";
import Loading from "../Components/Loading/Loading.jsx";

const IncomeCreator = ({onCancel}) => {
  const {user} = useAuth();
  const [loading, setLoading] = useState(false);

  const handleCreateIncome = async (income) => {
    setLoading(true);

    await postNewIncome(user, income);

    setLoading(false);
  };

  if (loading) return <Loading/>;

  return (
    <IncomeForm
      onCancel={onCancel}
      disabled={loading}
      onSave={handleCreateIncome}
    />
  );
};

export default IncomeCreator;
