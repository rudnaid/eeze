import { Link } from 'react-router-dom';
import React from "react";
import IncomeButton from "../Buttons/IncomeButton.jsx";

const IncomeTable = ({ incomes }) => {
    const total = incomes.reduce((acc, income) => acc + income.amount, 0);

    return (
        <div className="p-6 max-w-2xl mx-auto">
            {/* Total Income Box */}
            <div className="border-2 border-[#fdc57b] rounded-xl p-4 shadow-md bg-[#fefaf4] mb-6">
                <p className="font-bold text-right text-lg text-gray-700">
                    Total Income
                </p>
                <hr className="my-2 border-t border-gray-200" />
                <p className="text-xl font-bold text-right">
                    {total.toLocaleString("de-DE", {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2,
                    })}
                </p>
            </div>
            <div className="mb-8"></div>


            {/* Income Items Boxed Layout */}
            <div className="space-y-4">
                {incomes.map((income, index) => (
                    <div
                        key={index}
                        className="border border-[#fdc57b] rounded-xl p-4 shadow-sm bg-white flex justify-between items-center"
                    >
                        <div className="text-gray-500 text-sm mt-1">{income.date}</div>
                        <div className="font-bold">
                            {income.amount.toLocaleString('de-DE', {
                                minimumFractionDigits: 2,
                                maximumFractionDigits: 2,
                            })}
                        </div>
                    </div>
                ))}
            </div>

            {/* Add Button */}
            <div className="mt-6 flex justify-center">
                <IncomeButton />
            </div>
        </div>
    );
};

export default IncomeTable;
