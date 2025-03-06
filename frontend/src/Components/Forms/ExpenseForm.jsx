import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const ExpenseForm = ({ onSave, disabled, expense, onCancel }) => {
    const navigate = useNavigate();
    const [amount, setAmount] = useState(expense?.amount ?? "");
    const [transactionDate, setTransactionDate] = useState(expense?.transactionDate ?? "");
    const [category, setCategory] = useState(expense?.category ?? "");
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem("jwt");
        const headers = {
            "Content-Type": "application/json"
        };

        if (token) {
            headers["Authorization"] = `Bearer ${token}`;
        } else {
            navigate("/login");
            return;
        }
        const fetchData = async () => {
            try {
                const response = await fetch("/api/categories", {headers});
                const categories = await response.json();
                setCategories(categories);
            } catch (error) {
                console.error('Error occured while fetching categories: ', error)
            }
        };
        fetchData();
    }, [])

    const onSubmit = (e) => {
        e.preventDefault();

        if (expense) {
            return onSave({
                ...expense,
                amount: amount,
                transactionDate: transactionDate,
                category: category
            });
        }

        return onSave({
            amount: amount,
            transactionDate: transactionDate,
            category: category
        });
    };

    return (
        <>
            <div className="font-[sans-serif] relative">
                <div className="h-[240px] font-[sans-serif] bg-[#6a9184]">
                    {/* <img className="w-full h-full bg-[#6a9184]" /> */}
                </div>
                <div className="bg-[#fbf2d5] max-w-md w-full mx-auto relative -mt-60 m-4">
                    <form className="bg-white max-w-xl w-full mx-auto shadow-[0_2px_10px_-3px_rgba(182,191,184,0.99)] p-6 sm:p-8 rounded-2xl" onSubmit={onSubmit}>
                        <div className="mb-12">
                            <h3 className="text-gray-800 text-3xl font-bold text-center">
                                Create new expense
                            </h3>
                        </div>

                        <div>
                            <label className="text-gray-800 text-xs block mb-2"></label>
                            <div className="relative flex items-center">
                                <input
                                    value={amount}
                                    onChange={(e) => setAmount(e.target.value)}
                                    name="amount"
                                    id="amount"
                                    className="w-full bg-transparent text-sm text-gray-800 border-b border-gray-300 focus:border-gray-500 pl-2 pr-8 py-3 outline-none"
                                    placeholder="Enter amount"
                                />
                            </div>
                        </div>

                        <div>
                            <label className="text-gray-800 text-xs block mb-2"></label>
                            <div className="relative flex items-center">
                                <input
                                    value={transactionDate}
                                    type="date"
                                    onChange={(e) => setTransactionDate(e.target.value)}
                                    name="date"
                                    id="date"
                                    className="w-full bg-transparent text-sm text-gray-800 border-b border-gray-300 focus:border-gray-500 pl-2 pr-8 py-3 outline-none"
                                />
                            </div>
                        </div>

                        <div>
                            <label className="text-gray-800 text-xs block mb-2"></label>
                            <div className="relative flex items-center">
                                <select
                                    value={category}
                                    onChange={(e) => setCategory(e.target.value)}
                                    name="category"
                                    id="category"
                                >
                                    <option value="">Select a category</option>
                                    {categories.map((category, idx) => (<option key={idx} value={category.name}>{category.name}</option>))}
                                </select>
                            </div>
                        </div>

                        <div className="mt-8">
                            <button
                                type="submit"
                                disabled={disabled}
                                className="w-full shadow-xl py-2.5 px-4 text-sm font-semibold tracking-wider rounded-md text-white bg-gray-800 hover:bg-gray-500 focus:outline-none transition-all">
                                {expense ? "Update Expense" : "Create Expense"}
                            </button>

                            <br />
                            <br />

                            <button
                                type="button"
                                onClick={onCancel}
                                className="w-full shadow-xl py-2.5 px-4 text-sm font-semibold tracking-wider rounded-md text-white bg-gray-800 hover:bg-gray-500 focus:outline-none transition-all">
                                Cancel
                            </button>
                        </div>
                    </form>
                </div>
            </div >
        </>
    );
};

export default ExpenseForm;
