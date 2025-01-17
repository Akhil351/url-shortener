import React, { useContext, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import toast from 'react-hot-toast';
import api from '../api/api';
import {  useStoreContext } from '../contextApi/ContextApi';

const LoginPage = () => {
    const navigate = useNavigate();
    const {token,setToken}=useStoreContext()
    const [loginRequest, setLoginRequest] = useState({
        email: "",
        password: "",
    });
    const [errors, setErrors] = useState({});
    const [loader, setLoader] = useState(false);
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const handleChange = (e) => {
        const { name, value } = e.target;
        setLoginRequest({
            ...loginRequest,
            [name]: value,
        });
    };

    const validateForm = () => {
        const newErrors = {};

        if (!loginRequest.email.trim()) {
            newErrors.email = "*Email is required";
        } else if (!emailRegex.test(loginRequest.email)) {
            newErrors.email = "Invalid email";
        }
        if (!loginRequest.password.trim()) {
            newErrors.password = "*Password is required";
        } else if (loginRequest.password.length < 6) {
            newErrors.password = "Minimum 6 characters are required";
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const loginHandler = async (e) => {
        e.preventDefault();

        if (!validateForm()) {
            return;
        }

        setLoader(true);
        try {
           const response = await api.post(
                "/api/auth/public/login",
                loginRequest
            );
            setToken(response.data.data.token)
            localStorage.setItem("JWT_TOKEN",JSON.stringify(response.data.data.token))
            setLoginRequest({  email: "", password: "" });
            navigate("/dashboard");
            toast.success("Login Successful!");
        } catch (error) {
            navigate("/error")
            console.error(error);
            toast.error("Login Failed!");
        } finally {
            setLoader(false);
        }
    };

    return (
        <div className='min-h-[calc(100vh-64px)] flex justify-center items-center'>
            <form onSubmit={loginHandler}
                className="sm:w-[450px] w-[360px] shadow-custom py-8 sm:px-8 px-4 rounded-md">
                <h1 className="text-center font-serif text-btnColor font-bold lg:text-3xl text-2xl">
                     Login Here
                </h1>

                <hr className='mt-2 mb-5 text-black' />

                <div className="flex flex-col gap-3">
                    

                    <div className="flex flex-col gap-1">
                        <label htmlFor="email" className="font-semibold text-md">Email</label>
                        <input
                            type="email"
                            name="email"
                            id="email"
                            value={loginRequest.email}
                            onChange={handleChange}
                            placeholder="Type your email"
                            className={`px-2 py-2 border outline-none bg-transparent text-slate-700 rounded-md ${errors.email ? "border-red-500" : "border-slate-600"}`}
                        />
                        {errors.email && <p className="text-sm font-semibold text-red-600 mt-0">{errors.email}*</p>}
                    </div>

                    <div className="flex flex-col gap-1">
                        <label htmlFor="password" className="font-semibold text-md">Password</label>
                        <input
                            type="password"
                            name="password"
                            id="password"
                            value={loginRequest.password}
                            onChange={handleChange}
                            placeholder="Type your password"
                            className={`px-2 py-2 border outline-none bg-transparent text-slate-700 rounded-md ${errors.password ? "border-red-500" : "border-slate-600"}`}
                        />
                        {errors.password && <p className="text-sm font-semibold text-red-600 mt-0">{errors.password}*</p>}
                    </div>
                </div>

                <button
                    disabled={loader}
                    type='submit'
                    className='bg-customRed font-semibold text-white bg-custom-gradient w-full py-2 hover:text-slate-400 transition-colors duration-100 rounded-sm my-3'>
                    {loader ? "Loading..." : "Login"}
                </button>

                <p className='text-center text-sm text-slate-700 mt-6'>
                   Don't have an account? 
                    <Link
                        className='font-semibold underline hover:text-black'
                        to="/register">
                        <span className='text-btnColor'> SignUp</span>
                    </Link>
                </p>
            </form>
        </div>
    );
};

export default LoginPage;