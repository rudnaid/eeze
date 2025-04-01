import React from 'react';
import { Link } from 'react-router-dom';
import { useFetchSummary } from '../Hooks/useFetchSummary';
import { useFetchExpensesByCategory } from '../Hooks/useFetchExpensesByCategory';
import Loading from "../Components/Loading/Loading";
import ErrorComponent from "../Components/Util/ErrorComponent.jsx";

export const Expenses = () => {
	const {loading: summaryLoading, error: summaryError, summary} = useFetchSummary();
	const {loading: categoriesLoading, error: categoriesError, categories} = useFetchExpensesByCategory();

	if (summaryLoading || categoriesLoading) return <Loading />;
    if (summaryError || categoriesError) return <ErrorComponent message={summaryError || categoriesError} />;

	return (
		<>
			<div className="p-6 max-w-2xl mx-auto">
				{/* Summary Table */}
				<div className="border-2 border-[#fdc57b] bg-[#fffdf6] rounded-2xl p-6 shadow-lg mb-10 max-w-3xl mx-auto">
					<div className="flex justify-between text-gray-700 font-semibold px-4 pb-2 border-b border-gray-300 text-lg">
						<p>Total Income</p>
						<p>Total Expense</p>
						<p>Total Balance</p>
					</div>
					<div className="flex justify-between text-gray-800 font-bold px-4 pt-2 text-xl">
						<p>
							{summary.totalIncome?.toLocaleString('de-DE', {
								minimumFractionDigits: 2,
								maximumFractionDigits: 2,
							})}
						</p>
						<p>
							{summary.totalExpenses?.toLocaleString('de-DE', {
								minimumFractionDigits: 2,
								maximumFractionDigits: 2,
							})}
						</p>
						<p>
							{summary.currentBalance?.toLocaleString('de-DE', {
								minimumFractionDigits: 2,
								maximumFractionDigits: 2,
							})}
						</p>
					</div>
				</div>
				<div className="mb-8"></div>

				{/* Categories Boxed Layout */}
				<div className="space-y-4">
					{categories.map((category, index) => (
						<div
							key={index}
							className="border border-[#fdc57b] rounded-xl p-4 shadow-sm bg-white flex justify-between items-center"
						>
							<p className="font-semibold">{category.categoryName}</p>
							<p className="font-bold">
								{category.totalByCategory.toLocaleString('de-DE', {
									minimumFractionDigits: 2,
									maximumFractionDigits: 2,
								})}
							</p>
						</div>
					))}
				</div>
			</div>
			<div className="mt-6 flex justify-center pb-10">
				<Link
					to="/expenses/create"
					className="text-white bg-[#7e8283] hover:bg-[#7fa99b] font-semibold py-2 px-4 rounded transition"
				>
					Add New Expense
				</Link>
			</div>
		</>
	);
};
