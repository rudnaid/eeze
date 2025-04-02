import { useEffect, useState } from "react";
import { useAuth } from "../../Context/AuthContext.jsx";
import { getExpenseCategories } from "../../Services/apiService.js";

const ExpenseForm = ({ onSave, disabled, expense, onCancel }) => {
    const { user } = useAuth();
    const [amount, setAmount] = useState(expense?.amount ?? "");
    const [transactionDate, setTransactionDate] = useState(expense?.transactionDate ?? "");
    const [category, setCategory] = useState(expense?.category ?? "");
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const result = await getExpenseCategories(user);
            setCategories(result);
        };
        fetchData();
    }, [user]);

    const onSubmit = (e) => {
        e.preventDefault();
        const data = {
            amount,
            transactionDate,
            category,
        };
        if (expense) return onSave({ ...expense, ...data });
        return onSave(data);
    };

    return (
        <div className="font-[sans-serif] relative">
            <div className="h-[240px] bg-[#6a9184]"></div>

            <div className="bg-[#fbf2d5] w-full mx-auto relative -mt-60 m-4">
                <form
                    className="bg-white w-full mx-auto p-6 sm:p-8"
                    onSubmit={onSubmit}
                >
                    <div className="mb-8">
                        <h3 className="text-gray-800 text-2xl font-bold text-center">
                            Add New Expense
                        </h3>
                    </div>

                    {/* Amount */}
                    <div className="mb-4">
                        <label htmlFor="amount" className="block text-sm font-medium text-gray-700 mb-1">
                            Amount
                        </label>
                        <input
                            value={amount}
                            onChange={(e) => setAmount(e.target.value)}
                            name="amount"
                            id="amount"
                            required
                            className="w-full bg-white text-sm text-gray-800 border border-[#fdc57b] rounded-lg px-4 py-3 outline-none focus:ring-2 focus:ring-[#fdc57b] transition"
                            placeholder="Enter amount"
                        />
                    </div>

                    {/* Date */}
                    <div className="mb-4">
                        <label htmlFor="date" className="block text-sm font-medium text-gray-700 mb-1">
                            Date
                        </label>
                        <input
                            value={transactionDate}
                            type="date"
                            onChange={(e) => setTransactionDate(e.target.value)}
                            name="date"
                            id="date"
                            required
                            className="w-full bg-white text-sm text-gray-800 border border-[#fdc57b] rounded-lg px-4 py-3 outline-none focus:ring-2 focus:ring-[#fdc57b] transition"
                        />
                    </div>

                    {/* Category */}
                    <div className="mb-6">
                        <label htmlFor="category" className="block text-sm font-medium text-gray-700 mb-1">
                            Category
                        </label>
                        <select
                            value={category}
                            onChange={(e) => setCategory(e.target.value)}
                            name="category"
                            id="category"
                            required
                            className="w-full bg-white text-sm text-gray-800 border border-[#fdc57b] rounded-lg px-4 py-3 outline-none focus:ring-2 focus:ring-[#fdc57b] transition"
                        >
                            <option value="">Select a category</option>
                            {categories.map((category, idx) => (
                                <option key={idx} value={category.name}>
                                    {category.name}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="flex flex-col gap-3 mt-8">
                        <button
                            type="submit"
                            disabled={disabled}
                            className="w-full py-2.5 px-4 text-sm font-semibold rounded-lg text-gray-700 bg-[#fff8ef] border border-[#fdc57b] hover:bg-[#fff0da] transition duration-200 disabled:opacity-50 disabled:cursor-not-allowed"
                        >
                            {expense ? "Update Expense" : "Add Expense"}
                        </button>

                        <button
                            type="button"
                            onClick={onCancel}
                            className="w-full py-2.5 px-4 text-sm font-semibold rounded-lg text-gray-600 bg-white border border-[#fdc57b] hover:bg-[#fff0da] transition duration-200"
                        >
                            Cancel
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default ExpenseForm;