import { Link } from "react-router-dom";

const IncomeTable = ({ incomes, onDelete }) => {
    return (
        <div className="IncomeTable">
            <table>
                <thead>
                    <tr>
                        <th>Amount</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    {incomes.map((income, index) => (
                        <tr key={index}>
                            <td>{income.amount}</td>
                            <td>{income.date}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default IncomeTable;