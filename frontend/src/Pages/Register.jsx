import { FaFacebook } from 'react-icons/fa';
import { FaApple } from 'react-icons/fa';
import { FcGoogle } from 'react-icons/fc';
import {AiFillEye, AiFillEyeInvisible} from 'react-icons/ai';
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
					<form onSubmit={handleRegister} className="bg-white bg-opacity-90 w-full p-6 sm:p-8 rounded-xl border border-[#fdc57b] shadow-md">
						<h3 className="text-center text-2xl font-bold text-gray-800 mb-6">Register</h3>

						{/* First Name */}
						<div className="mb-4">
							<label htmlFor="firstname" className="block text-sm font-medium text-gray-700 mb-1">First Name</label>
							<input
								id="firstname"
								name="firstname"
								type="text"
								value={firstName}
								onChange={(e) => setFirstName(e.target.value)}
								required
								className="w-full bg-white text-sm text-gray-800 border border-[#fdc57b] rounded-lg px-4 py-3 outline-none focus:ring-2 focus:ring-[#fdc57b] placeholder:text-gray-400"
								placeholder="Enter first name"
							/>
						</div>

						{/* Last Name */}
						<div className="mb-4">
							<label htmlFor="lastname" className="block text-sm font-medium text-gray-700 mb-1">Last Name</label>
							<input
								id="lastname"
								name="lastname"
								type="text"
								value={lastName}
								onChange={(e) => setLastName(e.target.value)}
								required
								className="w-full bg-white text-sm text-gray-800 border border-[#fdc57b] rounded-lg px-4 py-3 outline-none focus:ring-2 focus:ring-[#fdc57b] placeholder:text-gray-400"
								placeholder="Enter last name"
							/>
						</div>

						{/* Username */}
						<div className="mb-4">
							<label htmlFor="username" className="block text-sm font-medium text-gray-700 mb-1">Username</label>
							<input
								id="username"
								name="username"
								type="text"
								value={username}
								onChange={(e) => setUsername(e.target.value)}
								required
								className="w-full bg-white text-sm text-gray-800 border border-[#fdc57b] rounded-lg px-4 py-3 outline-none focus:ring-2 focus:ring-[#fdc57b] placeholder:text-gray-400"
								placeholder="Choose a username"
							/>
						</div>

						{/* Country */}
						<div className="mb-4">
							<label htmlFor="country" className="block text-sm font-medium text-gray-700 mb-1">Country</label>
							<input
								id="country"
								name="country"
								type="text"
								value={country}
								onChange={(e) => setCountry(e.target.value)}
								required
								className="w-full bg-white text-sm text-gray-800 border border-[#fdc57b] rounded-lg px-4 py-3 outline-none focus:ring-2 focus:ring-[#fdc57b] placeholder:text-gray-400"
								placeholder="Enter your country"
							/>
						</div>

						{/* Email */}
						<div className="mb-4">
							<label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">Email</label>
							<input
								id="email"
								name="email"
								type="email"
								value={email}
								onChange={(e) => setEmail(e.target.value)}
								required
								className="w-full bg-white text-sm text-gray-800 border border-[#fdc57b] rounded-lg px-4 py-3 outline-none focus:ring-2 focus:ring-[#fdc57b] placeholder:text-gray-400"
								placeholder="Enter your email"
							/>
						</div>

						{/* Password */}
						<div className="mb-4">
							<label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">Password</label>
							<div className="relative">
								<input
									id="password"
									name="password"
									type={showPassword ? 'text' : 'password'}
									value={password}
									onChange={(e) => setPassword(e.target.value)}
									required
									className="w-full bg-white text-sm text-gray-800 border border-[#fdc57b] rounded-lg px-4 py-3 pr-10 outline-none focus:ring-2 focus:ring-[#fdc57b] placeholder:text-gray-400"
									placeholder="Enter password"
								/>
								<button
									type="button"
									onClick={togglePasswordVisibility}
									className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400"
								>
									{showPassword ? <AiFillEyeInvisible className="w-5 h-5" /> : <AiFillEye className="w-5 h-5" />}
								</button>
							</div>
						</div>

						{/* Submit button */}
						<div className="mt-6">
							<button
								type="submit"
								className="w-full py-2.5 px-4 text-sm font-semibold rounded-lg text-gray-700 bg-[#fff8ef] border border-[#fdc57b] hover:bg-[#fff0da] transition"
							>
								Register
							</button>
							<p className="text-sm text-gray-700 text-center mt-4">
								Already have an account?
								<Link to="/login" className="text-[#394a51] font-semibold hover:underline ml-1">Login here</Link>
							</p>
						</div>
						{/* Social icons */}
						<hr className="my-6 border-[#fdc57b]" />
						<div className="space-x-8 flex justify-center">
							<FcGoogle size={28} className="hover:opacity-70 transition" />
							<FaApple size={26} className="text-gray-800 hover:opacity-70 transition" />
							<FaFacebook size={26} className="text-[#394a51] hover:opacity-70 transition" />
						</div>
					</form>
				</div>
			</div>
		</>
	);
};

export default Register;
