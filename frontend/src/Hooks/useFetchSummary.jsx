import { useEffect, useState } from "react";
import { fetchSummary } from "../Services/apiService";

export const useFetchSummary = () => {
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [summary, setSummary] = useState({});

    useEffect(() => {
        const fetchData = async () => {
            try {
                setLoading(true);
                setError(null);

                const result = await fetchSummary();
                setSummary(result)
            } catch (error) {
                setError(error);
                console.error('Error getting summary:', error);
            } finally {
                setLoading(false);
            }
        }

        fetchData();
    }, []);

    return { loading, error, summary };
}
