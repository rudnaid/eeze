import Loading from "../Components/Loading/Loading.jsx";
import {useFetchCurrentMonthExpenses} from "../Hooks/useFetchCurrentMonthExpenses.jsx";

const CurrentExpenses = () => {
    const { expenses, loading } = useFetchCurrentMonthExpenses();

    const total =  expenses.reduce((acc, item) => acc + item.amount, 0);

    if (loading) return <Loading />;

    return (
        <div className="p-6 max-w-3xl mx-auto">
            <h1 className="text-2xl font-bold mb-4 text-right">
                Monthly Total: {total.toLocaleString("de-DE", { minimumFractionDigits: 2})}
            </h1>

            <div className="space-y-4">
                {expenses.map((expense) => (
                    <div
                        key={expense.publicId}
                        className="border rounded-lg p-4 shadow-sm bg-white flex justify-between items-center"
                    >
                        <div className="flex flex-col">
                            <p className="font-semibold">{expense.expenseCategory}</p>
                            <p className="text-gray-500 text-xs mt-1">{expense.transactionDate}</p>
                        </div>

                        <div className="font-bold">
                            {expense.amount.toLocaleString("de-DE", {
                                minimumFractionDigits: 2,
                            })}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default CurrentExpenses;