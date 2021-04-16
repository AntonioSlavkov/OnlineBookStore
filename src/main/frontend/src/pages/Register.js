import React, {useRef, useState} from 'react';
import {useForm} from 'react-hook-form';
import {ErrorMessage} from '@hookform/error-message';
import UserApi from "../utils/api/UserApi";
import styled from "styled-components";
import styles from "./css/register.module.css"


const Register = () => {
    const {register, handleSubmit, formState: {errors}, watch} = useForm()
    const [serverMessage, setServerMessage] = useState('');
    const password = useRef()
    password.current = watch("password")


    const onSubmit = (data) => {

        UserApi.register(data.username, data.email, data.password, data.firstName, data.lastName)
            .then(response => {
                setServerMessage(response.data.message)
            }).catch(error => {

            setServerMessage(error.response.data.message)
        })
    }


    const getErrorMessage = (errors, inputName) => {
        return <ErrorMessage errors={errors} name={inputName}>
            {
                ({messages}) => messages && Object.entries(messages).map(([type, message]) =>
                    (<p className={styles["error-message"]} key={type}>{message}</p>)
                )
            }
        </ErrorMessage>;
    }


    return (

        <div>

            <form className={styles["register-styling"]} onSubmit={handleSubmit(onSubmit)}>

                    <div id={""}>
                        <label>Username</label>
                    </div>

                <div>
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
                    </div>


                <div>
                    <input type="password" placeholder="Enter password"
                           {...register("password", {
                               required: "Password input is required",
                               minLength: {
                                   value: 10,
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
                </div>


                <div>
                    <input type="password" placeholder="Confirm password"
                           {...register("confirmPassword",
                               {validate: value => value === password.current || "The passwords do not match"})}/>
                </div>
                {getErrorMessage(errors, "confirmPassword")}

                <div>
                    <label>Email address</label>
                    <input type="email" placeholder="Enter email address" {...register("email", {
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
                    </div>

                <div>
                    <input type="text" placeholder="Enter first name" {...register("firstName", {
                        required: "Your first name is required"
                    })}/>
                </div>

                {getErrorMessage(errors, "firstName")}

                    <div>
                        <label>Last name</label>
                    </div>

                <div>
                    <input type="text" placeholder="Enter last name" {...register("lastName", {
                        required: "Your last name is required"
                    })}/>

                </div>

                {getErrorMessage(errors, "lastName")}

                <div>
                    <input type="submit"/>
                </div>
            </form>


            {serverMessage && <h2 className={styles["register-error-styling"]}>{serverMessage}</h2>}



        </div>
    )
}
export default Register
