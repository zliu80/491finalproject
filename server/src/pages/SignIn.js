import React from 'react';
import { useForm } from "react-hook-form";

import PocketBase from 'pocketbase';


const pb = new PocketBase('http://127.0.0.1:8090');

async function auth(username, password) {
  return await pb.collection('users').authWithPassword(username, password);
}

function SignIn() {
    const { register, handleSubmit, formState: { errors }, } = useForm({
        defaultValues: {
            username: '',
            password: '',
        },
        mode: 'onBlur'
    });

    const onSubmit = (data) => {
        const username = data.username;
        const password = data.password;
        auth(username, password).then((data) => {
            console.log(data);
            if (data != null) {
                window.location.replace("/dashboard");
            }
            return data;
        }).catch((error) => {
            if (error.message != null) {
                alert(error.message + " Check your username or password.");
            } else {
                alert("Something went wrong, contact author.")
            }
        });
    }

    return (
        <div className="w-screen h-screen flex justify-center items-center">
            <div className="relative flex flex-col justify-center min-h-screen overflow-hidden">
                <div className="w-full p-6 m-auto bg-white rounded-md shadow-md lg:max-w-xl">
                    <h1 className="text-3xl font-semibold text-center text-purple-700">
                        Sign in

                    </h1>
                    <form id="login_form" className="mt-6" onSubmit={handleSubmit(onSubmit)}>
                        <div className="mb-2">
                            <label

                                className="block text-sm font-semibold text-gray-800"
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

                                className="block text-sm font-semibold text-gray-800"
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
                            <button className="w-full px-4 py-2 tracking-wide text-white transition-colors duration-200 transform bg-purple-700 rounded-md hover:bg-purple-600 focus:outline-none focus:bg-purple-600">
                                Login
                            </button>
                        </div>
                    </form>


                </div>
            </div>
        </div>
    );
}








export default SignIn;
