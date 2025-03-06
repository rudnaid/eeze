import {useEffect, useState} from "react";
import { fetchTotalIncomes } from "../Services/apiService";

export const useFetchTotalIncomes = () => {
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [incomes, setIncomes] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
          try {
            setLoading(true);
            setError(null);
    
            const result = await fetchTotalIncomes();
            setIncomes(result)
          } catch (error) {
            setError(error);
            console.error('Error getting total incomes:', error);
          } finally {
            setLoading(false);
          }
        }
    
        fetchData();
      }, []);

    return { loading, error, incomes};
}