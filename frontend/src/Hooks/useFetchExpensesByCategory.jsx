import { useEffect, useState } from "react";
import { fetchExpensesByCategory } from "../Services/apiService";
import {useAuth} from "../Context/AuthContext.jsx";

export const useFetchExpensesByCategory = () => {
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [categories, setCategories] = useState({});
    const {user} = useAuth();
    
        useEffect(() => {
            const fetchData = async () => {
                try {
                    setLoading(true);
                    setError(null);
    
                    const result = await fetchExpensesByCategory(user);
                    setCategories(result)
                } catch (error) {
                    setError(error);
                    console.error('Error getting expenses by category:', error);
                } finally {
                    setLoading(false);
                }
            }
    
            fetchData();
        }, [user]);
    
        return { loading, error, categories };
}