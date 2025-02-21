import React, { useEffect, useState } from 'react';

export const Expenses = () => {
	const [summary, setSummary] = useState({});
	const [categories, setCategories] = useState([]);

	useEffect(() => {
		fetch(
			`http://localhost:8080/api/reports/7c873e71-164b-42f8-a6d9-3aa350172c2f`
		)
			.then((res) => res.json())
			.then((summaryResult) => setSummary(summaryResult));

		fetch(
			`http://localhost:8080/api/reports/by-category/7c873e71-164b-42f8-a6d9-3aa350172c2f`
		)
			.then((res) => res.json())
			.then((categoryList) => setCategories(categoryList));
	}, []);

	return (
		<>
			<div className="p-6 md:p-10 lg:p-12">
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
					<div className="table-row-group text-gray-800">
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
		</>
	);
};
