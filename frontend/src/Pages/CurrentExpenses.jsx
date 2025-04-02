import Loading from "../Components/Loading/Loading.jsx";
import {useFetchCurrentMonthExpenses} from "../Hooks/useFetchCurrentMonthExpenses.jsx";

const CurrentExpenses = () => {
    const { expenses, loading } = useFetchCurrentMonthExpenses();

    const total =  expenses.reduce((acc, item) => acc + item.amount, 0);

    if (loading) return <Loading />;

    return (
        <div className="p-6 max-w-3xl mx-auto">
            <div className="border-2 border-[#fdc57b] rounded-xl p-4 shadow-md bg-[#fefaf4] mb-6">
                <p className="font-bold text-right text-lg text-gray-700">
                    Monthly Total
                </p>
                <hr className="my-2 border-t border-gray-200" />
                <p className="text-xl font-bold text-right">
                    {total.toLocaleString("de-DE", {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2,
                    })}
                </p>
            </div>

            <div className="space-y-4">
                {expenses.map((expense) => (
                    <div
                        key={expense.publicId}
                        className="border border-[#fdc57b] rounded-xl p-4 shadow-sm bg-white flex justify-between items-center"
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