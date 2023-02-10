import React from 'react'
import PocketBase from 'pocketbase';
import {useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import { Link } from 'react-router-dom';

const pb = new PocketBase("http://127.0.0.1:8090");

  async function auth(username,password) {
    
    return await pb.collection('users').authWithPassword(username, password);
    
    
  }

function Login() {
    const {
        register, handleSubmit, formState: { errors },
    } = useForm({
        defaultValues: {
            username: '',
            password: '',

        },
        mode: 'onBlur'
    });
    const onSubmit = (data) => {
        const USERNAME = data.username;
        const PASSWORD = data.password;
        auth(USERNAME, PASSWORD).then((data) => {
            console.log(data);

            if (data != null) {
                window.location.replace("/dashboard")
            }
            return data;
        }).catch((error) => {
            if (error.message != null) {
                alert(error.message);
            } else {
                alert("Something went wrong. Contact the author.");
            }
            console.log(error);
        });


    }

    return (
        <div className="w-full h-[calc(100vh-76px)] overflow-auto flex justify-center items-center bg-gray-100 dark:bg-transparent">
            <div className="relative flex flex-col justify-center overflow-hidden">
                <div className="w-96 p-6 m-auto bg-white dark:bg-gray-900 rounded-md shadow-md lg:max-w-xl">
                    <h1 className="text-3xl font-semibold text-center text-blue-500">
                        Admin Login

                    </h1>
                    <form id="login_form" className="mt-6" onSubmit={handleSubmit(onSubmit)}>
                        <div className="mb-2">
                            <label

                                className="block text-sm font-semibold text-gray-800 dark:text-white"
                            >
                                Username
                            </label>
                            <input
                                type="text" name="uname" placeholder="Please enter your username" {...register("username", { required: true })}
                                className="block w-full px-4 py-2 mt-2 text-purple-700 bg-white border rounded-md focus:border-purple-400 focus:ring-purple-300 focus:outline-none focus:ring focus:ring-opacity-40"
                            />
                            {errors.username && <p className="text-red-500 mt-2">The username cannot be null</p>}

                        </div>
                        <div className="mb-2">
                            <label

                                className="block text-sm font-semibold text-gray-800 dark:text-white"
                            >
                                Password
                            </label>
                            <input
                                type="password" name="pass" placeholder="Please enter your password" {...register("password", { required: true })}
                                className="block w-full px-4 py-2 mt-2 text-purple-700 bg-white border rounded-md focus:border-purple-400 focus:ring-purple-300 focus:outline-none focus:ring focus:ring-opacity-40"
                            />
                            {errors.password && <p className="text-red-500 mt-2">The password cannot be null</p>}
                        </div>
                        <a
                            href="#"
                            className="text-xs text-purple-600 hover:underline"
                        >
                            Forget Password?
                        </a>
                        <div className="mt-6">
                            <button className="w-full px-4 py-2 tracking-wide text-white transition-colors duration-200 transform bg-blue-500 rounded-md hover:bg-blue-600 focus:outline-none focus:bg-blue-600">
                                Login
                            </button>
                        </div>
                    </form>

                   
                </div>
            </div>
        </div>
    );
}


export default Login
