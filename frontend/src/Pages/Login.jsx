import React, { useState } from "react";
import { AiFillEye, AiFillEyeInvisible } from "react-icons/ai"; // Import both icons
import { FaApple, FaFacebook, FaUser } from "react-icons/fa";
import { FcGoogle } from "react-icons/fc";
import { Link } from "react-router-dom";

const logInUser = async (userEntity) => {
    const response = await fetch("/api/users/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(userEntity),
    });
    const data = await response.json();
    localStorage.setItem("jwt", data.jwt);
};

const Login = () => {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const [showPassword, setShowPassword] = useState(false);

	const togglePasswordVisibility = () => {
		setShowPassword(!showPassword);
	};

	const onSubmit = async (e) => {
		e.preventDefault();
		await logInUser({ username, password });
		window.location.href = "/home";
	};

	return (
		<>
			<div className="relative">
				<div className="flex justify-center items-center font-[sans-serif] h-full min-h-screen p-4 bg-[url(/green-circles.png)] bg-no-repeat bg-cover bg-center">
					<Link to="/home">
						<h1 className="absolute top-0 left-0 p-7 text-5xl text-[#fdc57b]">eeze</h1>
					</Link>
					<div className="max-w-md w-full mx-auto">
						<form className="bg-white bg-opacity-70 shadow-2xl rounded-lg p-6" onSubmit={onSubmit}>
							<div className="mb-12">
								<h3 className="text-gray-800 text-center text-3xl font-bold">
									<Link to="/home/income">Sign in</Link>
								</h3>
							</div>
							{/* Username Input */}
							<div>
								<div className="relative flex items-center">
									<input
										name="username"
										type="text"
										required
										value={username}
										onChange={(e) => setUsername(e.target.value)}
										className="bg-transparent w-full text-sm text-gray-800 border-b border-gray-400 focus:border-gray-800 pl-2 pr-8 py-3 outline-none placeholder:text-gray-800"
										placeholder="Enter username"
									/>
									<FaUser className="w-5 h-5 text-gray-400 absolute right-2" />
								</div>
							</div>
							{/* Password Input with Toggle */}
							<div className="mt-6">
								<div className="relative flex items-center">
									<input
										name="password"
										type={showPassword ? "text" : "password"}
										required
										value={password}
										onChange={(e) => setPassword(e.target.value)}
										className="bg-transparent w-full text-sm text-gray-800 border-b border-gray-400 focus:border-gray-800 pl-2 pr-8 py-3 outline-none placeholder:text-gray-800"
										placeholder="Enter password"
									/>
									{/* Eye Icon - Click to Toggle */}
									<button type="button" onClick={togglePasswordVisibility} className="absolute right-2">
										{showPassword ? (
											<AiFillEyeInvisible className="w-5 h-5 text-gray-400 cursor-pointer" />
										) : (
											<AiFillEye className="w-5 h-5 text-gray-400 cursor-pointer" />
										)}
									</button>
								</div>
							</div>
							{/* Remember Me & Forgot Password */}
							<div className="flex flex-wrap items-center justify-between gap-4 mt-6">
								<div className="flex items-center">
									<input id="remember-me" name="remember-me" type="checkbox" className="h-4 w-4 shrink-0 border-gray-300 rounded" />
									<label htmlFor="remember-me" className="ml-3 block text-sm text-gray-800">
										Remember me
									</label>
								</div>
								<div>
									<Link to="/forgot-password" className="text-gray-800 text-sm font-semibold hover:underline">
										Forgot Password?
									</Link>
								</div>
							</div>
							{/* Submit Button */}
							<div className="mt-12">
								<button type="submit" className="cursor-pointer w-full py-2.5 px-4 text-sm font-semibold tracking-wider rounded text-white bg-gray-800 hover:bg-gray-500 focus:outline-none">
									Sign in
								</button>
								<p className="text-gray-800 text-sm text-center mt-6">
									Don't have an account?
									<Link to="/register" className="text-[#394a51] font-semibold hover:underline ml-1 whitespace-nowrap">
										Register here
									</Link>
								</p>
							</div>
							{/* Social Login Buttons */}
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
			</div>
		</>
	);
};

export default Login;