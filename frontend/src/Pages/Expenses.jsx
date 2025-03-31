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
				<div className="table w-full border border-gray-300 rounded-lg overflow-hidden mb-6">
					<div className="table-header-group bg-gray-100 text-gray-700 font-semibold">
						<div className="table-row">
							<div className="table-cell px-6 py-3 text-center">
								Total Income
							</div>
							<div className="table-cell px-6 py-3 text-center">
								Total Expense
							</div>
							<div className="table-cell px-6 py-3 text-center">
								Total Balance
							</div>
						</div>
					</div>
					<div className="table-row-group text-gray-800">
						<div className="table-row">
							<div className="table-cell px-6 py-3 text-center">
								{summary.totalIncome?.toLocaleString('de-DE', {
									minimumFractionDigits: 2,
									maximumFractionDigits: 2,
								})}
							</div>
							<div className="table-cell px-6 py-3 text-center">
								{summary.totalExpenses?.toLocaleString('de-DE', {
									minimumFractionDigits: 2,
									maximumFractionDigits: 2,
								})}
							</div>
							<div className="table-cell px-6 py-3 text-center">
								{summary.currentBalance?.toLocaleString('de-DE', {
									minimumFractionDigits: 2,
									maximumFractionDigits: 2,
								})}
							</div>
						</div>
					</div>
				</div>

				{/* Categories Table */}
				<div className="table w-full border border-gray-300 rounded-lg overflow-hidden">
					<div className="table-header-group bg-gray-100 text-gray-700 font-semibold">
						<div className="table-row">
							<div className="table-cell px-6 py-3 text-left">
								Category Name
							</div>
							<div className="table-cell px-6 py-3 text-right">Amount</div>
						</div>
					</div>
					<div className="table-row-group text-gray-800 ">
						{categories.map((category, index) => (
							<div className="table-row even:bg-gray-50" key={index}>
								<div className="table-cell px-6 py-3">
									{category.categoryName}
								</div>
								<div className="table-cell px-6 py-3 text-right">
									{category.totalByCategory.toLocaleString('de-DE', {
										minimumFractionDigits: 2,
										maximumFractionDigits: 2,
									})}
								</div>
							</div>
						))}
					</div>
				</div>
			</div>
			<div className="mt-6 flex justify-center">
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
