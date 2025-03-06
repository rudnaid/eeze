import { useEffect, useState } from "react";
import { fetchExpensesByCategory } from "../Services/apiService";

export const useFetchExpensesByCategory = () => {
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [categories, setCategories] = useState({});
    
        useEffect(() => {
            const fetchData = async () => {
                try {
                    setLoading(true);
                    setError(null);
    
                    const result = await fetchExpensesByCategory();
                    setCategories(result)
                } catch (error) {
                    setError(error);
                    console.error('Error getting expenses by category:', error);
                } finally {
                    setLoading(false);
                }
            }
    
            fetchData();
        }, []);
    
        return { loading, error, categories };
}