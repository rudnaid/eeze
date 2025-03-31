import {useEffect, useState} from "react";
import {useAuth} from "../Context/AuthContext.jsx";
import {fetchCurrentMonthExpenses} from "../Services/apiService.js";

export const useFetchCurrentMonthExpenses = () => {

    const [expenses, setExpenses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { user } = useAuth();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const result = await fetchCurrentMonthExpenses(user);
                setExpenses(result);
            } catch (error) {
                setError(error);
            } finally {
                setLoading(false);
            }
        }
        if (user) fetchData();
    }, [user]);

    return { expenses, loading, error };
}