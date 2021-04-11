import React, { useRef } from 'react';
import {useForm} from 'react-hook-form';
import {ErrorMessage} from '@hookform/error-message';
import UserApi from "../utils/api/UserApi";



const Register = () => {
    const {register, handleSubmit, formState: { errors }, watch} = useForm()

    const password = useRef()
    password.current = watch("password")



    const onSubmit = (data) => {
        console.log(data)
        UserApi.register(data.username, data.email, data.password, data.firstName, data.lastName)
            .then(response => {
                // console.log(response.headers.message)
            })
    }




    const getErrorMessage = (errors, inputName) => {
        return <ErrorMessage errors={errors} name={inputName}>
            {
                ({messages}) => messages && Object.entries(messages).map(([type, message]) =>
                    (<p key={type}>{message}</p>)
                )
            }
        </ErrorMessage>;
    }


    return (
        <form onSubmit={handleSubmit(onSubmit)}>

            <div>
                <label>Username</label>

                <input type="text" placeholder="Enter username"
                      {...register("username", {
                           required: "Username input is required", minLength: {
                               value: 5,
                               message: "Username must be longer than 5 characters"
                           }, maxLength: {
                               value: 15,
                               message: "Username cannot exceed 15 characters"
                           }
                       })}/>
            </div>

            {getErrorMessage(errors, "username")}

            <div>
                <label>Password</label>
                <input type="password" placeholder="Enter password"
                       {...register("password", {
                           required: "Password input is required",
                           minLength: {
                               value: 5,
                               message: "Password must be at least 10 characters long"
                           },
                           maxLength: {
                               value: 20,
                               message: "Password cannot exceed 20 characters"
                           },
                           pattern: {
                               value: /^[A-Za-z]+\d+\W+$/i,
                               message: "Password must contain at least 1 special character, 1 number and 1 uppercase letter"
                           }
                       })}/>
            </div>

            {getErrorMessage(errors, "password")}

            <div>
                <label>Confirm password</label>
                <input type="password" placeholder="Confirm password"
                       {...register("confirmPassword",
                           {validate: value => value === password.current || "The passwords do not match"})}/>
            </div>

            <div>
                <label>Email address</label>
                <input type="email" placeholder="Enter email address" {...register("email",{
                    required: "Your email address is required",
                    pattern: {
                        value: /\S+@\S+\.\S+/i,
                        message: "You must enter a valid email address"
                    }
                })}/>
            </div>

            {getErrorMessage(errors, "emailAddress")}

            <div>
                <label>First name</label>
                <input type="text" placeholder="Enter first name" {...register("firstName",{
                    required: "Your first name is required"
                })}/>
            </div>

            {getErrorMessage(errors, "firstName")}

            <div>
                <label>Last name</label>
                <input type="text" placeholder="Enter last name" {...register("lastName",{
                    required: "Your last name is required"
                })}/>

            </div>

            {getErrorMessage(errors, "lastName")}

            <div>
                <input type="submit" />
            </div>
        </form>
    )
}
export default Register
