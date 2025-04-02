import { useState } from 'react';

const IncomeForm = ({ onSave, disabled, income, onCancel }) => {
	const [amount, setAmount] = useState(income?.amount ?? '');
	const [date, setDate] = useState(income?.date ?? '');

	const onSubmit = (e) => {
		e.preventDefault();
		const data = { amount, date };
		if (income) return onSave({ ...income, ...data });
		return onSave(data);
	};

	return (
		<div className="font-[sans-serif] relative">
			<div className="h-[240px] bg-[#7fa99b]"></div>

			<div className="bg-[#fbf2d5] w-full mx-auto relative -mt-60 m-4">
				<form
					className="bg-white w-full mx-auto p-6 sm:p-8"
					onSubmit={onSubmit}
				>
					<div className="mb-8">
						<h3 className="text-gray-800 text-2xl font-bold text-center">
							Add New Income
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
					<div className="mb-6">
						<label htmlFor="date" className="block text-sm font-medium text-gray-700 mb-1">
							Date
						</label>
						<input
							value={date}
							type="date"
							onChange={(e) => setDate(e.target.value)}
							name="date"
							id="date"
							required
							className="w-full bg-white text-sm text-gray-800 border border-[#fdc57b] rounded-lg px-4 py-3 outline-none focus:ring-2 focus:ring-[#fdc57b] transition"
						/>
					</div>

					<div className="flex flex-col gap-3 mt-8">
						<button
							type="submit"
							disabled={disabled}
							className="w-full py-2.5 px-4 text-sm font-semibold rounded-lg text-gray-700 bg-[#fff8ef] border border-[#fdc57b] hover:bg-[#fff0da] transition duration-200 disabled:opacity-50 disabled:cursor-not-allowed"
						>
							{income ? 'Update Income' : 'Add Income'}
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

export default IncomeForm;