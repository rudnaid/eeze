import {useEffect, useState} from "react";
import { fetchTotalIncomes } from "../Services/apiService";
import {useAuth} from "../Context/AuthContext.jsx";

export const useFetchTotalIncomes = () => {
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [incomes, setIncomes] = useState([]);
    const {user} = useAuth();

    useEffect(() => {
        const fetchData = async () => {
          try {
            setLoading(true);
            setError(null);
    
            const result = await fetchTotalIncomes(user);
            setIncomes(result)
          } catch (error) {
            setError(error);
            console.error('Error getting total incomes:', error);
          } finally {
            setLoading(false);
          }
        }
    
        fetchData();
      }, [user]);

    return { loading, error, incomes};
}