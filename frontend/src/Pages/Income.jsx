import { useEffect, useState } from "react";
import IncomeTable from "../Components/Tables/IncomeTable";
import Loading from "../Components/Loading/Loading";

const Income = () => {
    const userId = "7c873e71-164b-42f8-a6d9-3aa350172c2f";
    const [loading, setLoading] = useState(true);
    const [incomes, setIncomes] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const incomesResponse = await fetch(`/api/income/?memberPublicId=${userId}`)
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