import React from 'react';
import { AiFillEye } from 'react-icons/ai';
import { FaApple, FaFacebook, FaUser } from 'react-icons/fa';
import { FcGoogle } from 'react-icons/fc';
import { Link } from 'react-router-dom';

const Login = () => {
	return (
		<>
			<div className="relative">
				<div className="flex justify-center items-center font-[sans-serif] h-full min-h-screen p-4 bg-[url(/green-circles.png)] bg-no-repeat bg-cover bg-center">
					<Link to="/home">
						<h1 className="absolute top-0 left-0 p-7 text-5xl text-[#fdc57b] ">
							eeze
						</h1>
					</Link>
					<div className="max-w-md w-full mx-auto">
						<form className="bg-white bg-opacity-70 shadow-2xl rounded-lg p-6">
							<div className="mb-12">
								<h3 className="text-gray-800 text-center text-3xl font-bold">
									<Link to="/home/income">Sign in</Link>
								</h3>
							</div>
							<div>
								<div className="relative flex items-center">
									<input
										name="username"
										type="text"
										required
										className="bg-transparent w-full text-sm text-gray-800 border-b border-gray-400 focus:border-gray-800 pl-2 pr-8 py-3 outline-none placeholder:text-gray-800"
										placeholder="Enter username"
									/>
									<FaUser className="w-5 h-5 text-gray-400 absolute right-2" />{' '}
								</div>
							</div>
							<div className="mt-6">
								<div className="relative flex items-center">
									<input
										name="password"
										type="password"
										required
										className="bg-transparent w-full text-sm text-gray-800 border-b border-gray-400 focus:border-gray-800 pl-2 pr-8 py-3 outline-none placeholder:text-gray-800"
										placeholder="Enter password"
									/>
									<AiFillEye className="w-5 h-5 text-gray-400 absolute right-2 cursor-pointer" />{' '}
								</div>
							</div>
							<div className="flex flex-wrap items-center justify-between gap-4 mt-6">
								<div className="flex items-center">
									<input
										id="remember-me"
										name="remember-me"
										type="checkbox"
										className="h-4 w-4 shrink-0 border-gray-300 rounded"
									/>
									<label
										htmlFor="remember-me"
										className="ml-3 block text-sm text-gray-800"
									>
										Remember me
									</label>
								</div>
								<div>
									<Link
										to="/forgot-password" // TODO: oldalt megcsinalni !!!!!!!!!!!!!!!!!!!
										className="text-gray-800 text-sm font-semibold hover:underline"
									>
										Forgot Password?
									</Link>
								</div>
							</div>
							<div className="mt-12">
								<button
									type="button"
									className="cursor-pointer w-full py-2.5 px-4 text-sm font-semibold tracking-wider rounded text-white bg-gray-800 hover:bg-gray-500 focus:outline-none"
								>
									Sign in
								</button>
								<p className="text-gray-800 text-sm text-center mt-6">
									Don't have an account
									<Link
										to="/register"
										className="text-[#394a51] font-semibold hover:underline ml-1 whitespace-nowrap"
									>
										Register here
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
			</div>
		</>
	);
};

export default Login;
