import React from "react";
import { FcGoogle } from "react-icons/fc";
import { FaApple, FaFacebook } from "react-icons/fa";


export const GoogleIcon = ({ size = 30 }) => <FcGoogle size={size} />;
export const AppleIcon = ({ size = 30 }) => <FaApple size={size} className="text-black" />;
export const FacebookIcon = ({ size = 30 }) => <FaFacebook size={size} className="text-[#1877F2]" />;
