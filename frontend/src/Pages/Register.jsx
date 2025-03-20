import { FaFacebook } from 'react-icons/fa';
import { FaApple } from 'react-icons/fa';
import { FcGoogle } from 'react-icons/fc';
import { AiFillEye } from 'react-icons/ai';
import { BiMap } from 'react-icons/bi';
import { MdAlternateEmail } from 'react-icons/md';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { FaUser } from 'react-icons/fa';
import {registerUser} from "../Services/apiService.js";

const Register = () => {
	const navigate = useNavigate();
	const [firstName, setFirstName] = useState('');
	const [lastName, setLastName] = useState('');
	const [username, setUsername] = useState('');
	const [country, setCountry] = useState('');
	const [email, setEmail] = useState('');
	const [password, setPassword] = useState('');
	const [showPassword, setShowPassword] = useState(false);

	const togglePasswordVisibility = () => {
		setShowPassword(!showPassword);
	};

	const handleRegister = async (e) => {
		e.preventDefault();

		await registerUser({ firstName, lastName, country, email, username, password });

		navigate('/login');
	};

	return (
		<>
			<div className="font-[sans-serif] relative bg-[#394a51] pb-40">
				<div className="h-[400px] font-[sans-serif] bg-[url(/green-circles.png)] bg-no-repeat bg-cover bg-center">
					<Link to="/">
						<h1 className="ml-7 text-5xl pt-8 text-[#fdc57b]">eeze</h1>
					</Link>
					{/* <img className="w-full h-full bg-[#6a9184]" /> */}
				</div>

				<div className="max-w-md w-full mx-auto relative -mt-60 m-4">
					<form
						className="bg-white max-w-xl w-full mx-auto shadow-[0_2px_10px_-3px_rgba(182,191,184,0.99)] p-6 sm:p-8 rounded-2xl"
						onSubmit={handleRegister}
					>
						<div className="mb-12">
							<h3 className="text-gray-800 text-3xl font-bold text-center">
								Register
							</h3>
						</div>

						<div>
							<label className="text-gray-800 text-xs block mb-2"></label>
							<div className="relative flex items-center">
								<input
									name="firstname"
									type="text"
									required
									value={firstName}
									onChange={(e) => setFirstName(e.target.value)}
									className="w-full bg-transparent text-sm text-gray-800 border-b border-gray-300 focus:border-gray-500 pl-2 pr-8 py-3 outline-none"
									placeholder="Enter first name"
								/>
								<FaUser className="w-5 h-5 text-gray-400 absolute right-2" />
							</div>
						</div>

						<div className="mt-8">
							<label className="text-gray-800 text-xs block mb-2"></label>
							<div className="relative flex items-center">
								<input
									name="lastname"
									type="text"
									required
									value={lastName}
									onChange={(e) => setLastName(e.target.value)}
									className="w-full bg-transparent text-sm text-gray-800 border-b border-gray-300 focus:border-gray-500 pl-2 pr-8 py-3 outline-none"
									placeholder="Enter last name"
								/>
								<FaUser className="w-5 h-5 text-gray-400 absolute right-2" />
							</div>
						</div>

						<div className="mt-8">
							<label className="text-gray-800 text-xs block mb-2"></label>
							<div className="relative flex items-center">
								<input
									name="username"
									type="text"
									required
									value={username}
									onChange={(e) => setUsername(e.target.value)}
									className="w-full bg-transparent text-sm text-gray-800 border-b border-gray-300 focus:border-gray-500 pl-2 pr-8 py-3 outline-none"
									placeholder="Enter username"
								/>
								<FaUser className="w-5 h-5 text-gray-400 absolute right-2" />
							</div>
						</div>

						<div className="mt-8">
							<label className="text-gray-800 text-xs block mb-2"></label>
							<div className="relative flex items-center">
								<input
									name="country"
									type="text"
									required
									value={country}
									onChange={(e) => setCountry(e.target.value)}
									className="w-full bg-transparent text-sm text-gray-800 border-b border-gray-300 focus:border-gray-500 pl-2 pr-8 py-3 outline-none"
									placeholder="Enter country"
								/>
								<BiMap className="w-5 h-5 text-gray-400 absolute right-2" />
							</div>
						</div>

						<div className="mt-8">
							<label className="text-gray-800 text-xs block mb-2"></label>
							<div className="relative flex items-center">
								<input
									name="email"
									type="text"
									required
									value={email}
									onChange={(e) => setEmail(e.target.value)}
									className="w-full bg-transparent text-sm text-gray-800 border-b border-gray-300 focus:border-gray-500 pl-2 pr-8 py-3 outline-none"
									placeholder="Enter email"
								/>
								<MdAlternateEmail className="w-5 h-5 text-gray-400 absolute right-2" />
							</div>
						</div>

						<div className="mt-8">
							<label className="text-gray-800 text-xs block mb-2"></label>
							<div className="relative flex items-center">
								<input
									name="password"
									type={showPassword ? 'text' : 'password'}
									required
									value={password}
									onChange={(e) => setPassword(e.target.value)}
									className="w-full bg-transparent text-sm text-gray-800 border-b border-gray-300 focus:border-gray-500 pl-2 pr-8 py-3 outline-none"
									placeholder="Enter password"
								/>
								<button
									type="button"
									onClick={togglePasswordVisibility}
									className="absolute right-2"
								>
									{showPassword ? (
										<AiFillEye className="w-5 h-5 text-gray-400 cursor-pointer" />
									) : (
										<AiFillEye className="w-5 h-5 text-gray-400 cursor-pointer" />
									)}
								</button>{' '}
							</div>
						</div>

						<div className="flex items-center mt-8">
							<input
								id="remember-me"
								name="remember-me"
								type="checkbox"
								className="h-4 w-4 shrink-0 rounded"
							/>
							<label htmlFor="remember-me" className="ml-3 block text-sm">
								I accept the
								<Link
									to="/terms-and-conditions" // TODO: oldalt megcsinalni!!!!!!!!!!!!!!!!!!!!
									className="text-[#6a9184] font-semibold hover:underline ml-1"
								>
									Terms and Conditions
								</Link>
							</label>
						</div>

						<div className="mt-8">
							<button
								type="submit"
								className="w-full shadow-xl py-2.5 px-4 text-sm font-semibold tracking-wider rounded-md text-white bg-gray-800 hover:bg-gray-500 focus:outline-none transition-all"
							>
								Register
							</button>
							<p className="text-gray-800 text-sm mt-4 text-center">
								Already have an account?
								<Link
									to="/login"
									className="text-[#6a9184] font-semibold hover:underline ml-1"
								>
									Login here
								</Link>
							</p>
						</div>
						<hr className="my-6 border-gray-400" />
						<div className="space-x-8 flex justify-center">
							<button type="button" className="border-none outline-none">
								<FcGoogle size={30} />
							</button>
							<button type="button" className="border-none outline-none">
								<FaApple size={30} className="text-black" />
							</button>
							<button type="button" className="border-none outline-none">
								<FaFacebook size={30} className="text-[#1877F2]" />
							</button>
						</div>
					</form>
				</div>
			</div>
		</>
	);
};

export default Register;
