import { Link } from "react-router-dom";

const IncomeTable = ({ incomes, onDelete }) => {
    return (
        <div className="IncomeTable flex flex-col justify-start min-h-screen">
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
        </div>
    );
}

export default IncomeTable;