import IncomeTable from "../Components/Tables/IncomeTable";
import Loading from "../Components/Loading/Loading";
import { useFetchTotalIncomes } from "../Hooks/useFetchTotalIncomes";
import ErrorComponent from "../Components/Util/ErrorComponent.jsx";

const Income = () => {
    const {loading, error, incomes} = useFetchTotalIncomes();

    if (loading) return <Loading />;
    if (error) return <ErrorComponent message={error} />;

    return <IncomeTable incomes={incomes} />;
}

export default Income;