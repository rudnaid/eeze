import { useState } from 'react';

const IncomeForm = ({ onSave, disabled, income, onCancel }) => {
	const [amount, setAmount] = useState(income?.amount ?? '');
	const [date, setDate] = useState(income?.date ?? '');

	const onSubmit = (e) => {
		e.preventDefault();

		if (income) {
			return onSave({
				...income,
				amount: amount,
				date: date,
			});
		}

		return onSave({
			amount: amount,
			date: date,
		});
	};

	return (
		<>
			<div className="font-[sans-serif] relative">
				<div className="h-[240px] font-[sans-serif] bg-[#7fa99b]">
					{/* <img className="w-full h-full bg-[#6a9184]" /> */}
				</div>
				<div className="bg-[#fbf2d5] max-w-md w-full mx-auto relative -mt-60 m-4">
					<form
						className="bg-white max-w-xl w-full mx-auto p-6 sm:p-8"
						onSubmit={onSubmit}
					>
						<div className="mb-12">
							<h3 className="text-gray-800 text-3xl font-bold text-center">
								Create new income
							</h3>
						</div>

						<div>
							<label className="text-gray-800 text-xs block mb-2"></label>
							<div className="relative flex items-center">
								<input
									value={amount}
									onChange={(e) => setAmount(e.target.value)}
									name="amount"
									id="amount"
									required={true}
									className="w-full bg-transparent text-sm text-gray-800 border-b border-gray-300 focus:border-gray-500 pl-2 pr-8 py-3 outline-none"
									placeholder="Enter amount"
								/>
							</div>
						</div>

						<div>
							<label className="text-gray-800 text-xs block mb-2"></label>
							<div className="relative flex items-center">
								<input
									value={date}
									type="date"
									onChange={(e) => setDate(e.target.value)}
									name="date"
									id="date"
									required={true}
									className="w-full bg-transparent text-sm text-gray-800 border-b border-gray-300 focus:border-gray-500 pl-2 pr-8 py-3 outline-none"
								/>
							</div>
						</div>

						<div className="mt-8">
							<button
								type="submit"
								className="w-full shadow-xl py-2.5 px-4 text-sm font-semibold tracking-wider rounded-md text-white bg-gray-800 hover:bg-gray-500 focus:outline-none transition-all"
								disabled={disabled}
							>
								{income ? 'Update Income' : 'Create Income'}
							</button>

              <br />
              <br />
              
							<button
								type="button"
								onClick={onCancel}
								className="w-full shadow-xl py-2.5 px-4 text-sm font-semibold tracking-wider rounded-md text-white bg-gray-800 hover:bg-gray-500 focus:outline-none transition-all"
							>
								Cancel
							</button>
						</div>
					</form>
				</div>
			</div>
		</>
	);
};

export default IncomeForm;
