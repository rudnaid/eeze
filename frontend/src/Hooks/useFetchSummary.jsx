import { useEffect, useState } from "react";
import { fetchSummary } from "../Services/apiService";
import {useAuth} from "../Context/AuthContext.jsx";

export const useFetchSummary = () => {
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [summary, setSummary] = useState({});
    const {user} = useAuth();

    useEffect(() => {
        const fetchData = async () => {
            try {
                setLoading(true);
                setError(null);

                const result = await fetchSummary(user);
                setSummary(result)
            } catch (error) {
                setError(error);
                console.error('Error getting summary:', error);
            } finally {
                setLoading(false);
            }
        }

        fetchData();
    }, [user]);

    return { loading, error, summary };
}
