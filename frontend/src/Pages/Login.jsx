import { useState } from "react";
import { AiFillEye, AiFillEyeInvisible } from "react-icons/ai";
import { FaApple, FaFacebook, FaUser } from "react-icons/fa";
import { FcGoogle } from "react-icons/fc";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../Context/AuthContext.jsx";
import { loginUser } from "../Services/apiService.js";

const Login = () => {
	const navigate = useNavigate();
	const { login } = useAuth();
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [showPassword, setShowPassword] = useState(false);

	const togglePasswordVisibility = () => setShowPassword(!showPassword);

	const onSubmit = async (e) => {
		e.preventDefault();
		try {
			const user = await loginUser({ username, password });
			login(user);
			navigate("/home");
		} catch (error) {
			console.error(error);
			navigate("/login");
		}
	};

	return (
		<div className="relative min-h-screen bg-[url(/green-circles.png)] bg-no-repeat bg-cover bg-center flex items-center justify-center font-[sans-serif]">
			<Link to="/" className="absolute top-0 left-0 p-7 text-5xl text-[#fdc57b]">eeze</Link>

			<div className="bg-[#fbf2d5] w-full max-w-md mx-4 relative shadow-xl rounded-xl">
				<form onSubmit={onSubmit} className="bg-white w-full p-6 sm:p-8 rounded-xl border border-[#fdc57b]">
					<h3 className="text-center text-2xl font-bold text-gray-800 mb-6">Sign in</h3>

					{/* Username */}
					<div className="mb-4">
						<label htmlFor="username" className="block text-sm font-medium text-gray-700 mb-1">Username</label>
						<div className="relative">
							<input
								id="username"
								name="username"
								type="text"
								value={username}
								onChange={(e) => setUsername(e.target.value)}
								required
								className="w-full bg-white text-sm text-gray-800 border border-[#fdc57b] rounded-lg px-4 py-3 pr-10 outline-none focus:ring-2 focus:ring-[#fdc57b] placeholder:text-gray-400"
								placeholder="Enter username"
							/>
							<FaUser className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
						</div>
					</div>

					{/* Password */}
					<div className="mb-4">
						<label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">Password</label>
						<div className="relative">
							<input
								id="password"
								name="password"
								type={showPassword ? "text" : "password"}
								value={password}
								onChange={(e) => setPassword(e.target.value)}
								required
								className="w-full bg-white text-sm text-gray-800 border border-[#fdc57b] rounded-lg px-4 py-3 pr-10 outline-none focus:ring-2 focus:ring-[#fdc57b] placeholder:text-gray-400"
								placeholder="Enter password"
							/>
							<button type="button" onClick={togglePasswordVisibility} className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400">
								{showPassword ? <AiFillEyeInvisible className="w-5 h-5" /> : <AiFillEye className="w-5 h-5" />}
							</button>
						</div>
					</div>

					{/* Remember + Forgot */}
					<div className="flex items-center justify-between mb-6">
						<label className="flex items-center text-sm text-gray-700">
							<input type="checkbox" className="mr-2 border-gray-300 rounded" />
							Remember me
						</label>
						<Link to="/forgot-password" className="text-sm font-medium text-gray-600 hover:underline">
							Forgot Password?
						</Link>
					</div>

					{/* Submit Button */}
					<div className="flex flex-col gap-3">
						<button
							type="submit"
							className="w-full py-2.5 px-4 text-sm font-semibold rounded-lg text-gray-700 bg-[#fff8ef] border border-[#fdc57b] hover:bg-[#fff0da] transition"
						>
							Sign in
						</button>

						<p className="text-gray-800 text-sm text-center mt-6">
							Don&#39;t have an account?
							<Link to="/register" className="text-[#394a51] font-semibold hover:underline ml-1 whitespace-nowrap">
								Register here
							</Link>
						</p>
					</div>

					{/* Social Icons */}
					<hr className="my-6 border-[#fdc57b]" />
					<div className="flex justify-center space-x-6">
						<FcGoogle size={28} />
						<FaApple size={28} className="text-black" />
						<FaFacebook size={28} className="text-[#1877F2]" />
					</div>
				</form>
			</div>
		</div>
	);
};

export default Login;