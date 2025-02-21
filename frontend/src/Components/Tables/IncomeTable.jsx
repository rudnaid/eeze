import { Link } from 'react-router-dom';

const IncomeTable = ({ incomes }) => {
    return (
        <div className="IncomeTable flex flex-col justify-start min-h-screen pt-30">
            <table className="mx-auto border-collapse border border-gray-300 bg-white w-2xl">
                <thead>
                    <tr>
                        <th className="border border-spacing-2 p-2 border-gray-300">Amount</th>
                        <th className="border border-spacing-2 p-2 border-gray-300">Date</th>
                    </tr>
                </thead>
                <tbody>
                    {incomes.map((income, index) => (
                        <tr key={index}>
                            <td className="border p-2 border-gray-300">{income.amount}</td>
                            <td className="border p-2 border-gray-300">{income.date}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div className="mt-6 flex justify-center">
                <Link
                    to="/income/create"
                    className="text-white bg-[#7e8283] hover:bg-[#7fa99b] font-semibold py-2 px-4 rounded transition"
                >
                    Add New Income
                </Link>
            </div>
        </div>
    );
}

export default IncomeTable;