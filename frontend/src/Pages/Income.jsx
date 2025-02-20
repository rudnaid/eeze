import { useEffect, useState } from "react";
import IncomeTable from "../Components/Tables/IncomeTable";
import Loading from "../Components/Loading/Loading";

const Income = () => {
    const userId = 1;
    const [loading, setLoading] = useState(true);
    const [incomes, setIncomes] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const incomesResponse = await fetch(`/api/income/?userId=${userId}`)
            const incomes = await incomesResponse.json();
            setIncomes(incomes)
            setLoading(false)
        };
        fetchData();
    }, [userId]);

    if (loading) {
        return <Loading/>;
      }
    
    return <IncomeTable incomes={incomes} />;
}
 
export default Income;