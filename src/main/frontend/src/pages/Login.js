import React, {useRef, useState} from 'react';
import Input from "react-validation/build/input";
import UserApi from "../utils/api/UserApi";
import CheckButton from "react-validation/build/button";
import Form from "react-validation/build/form";
import styles from "./css/login.module.css";


const required = (value) => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

const Login = (props) => {
    const form = useRef();
    const checkBtn = useRef();

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [loading, setLoading] = useState(false)
    const [message, setMessage] = useState("")

    const onChangeUsername = e => {
        setUsername(e.target.value)
    }

    const onChangePassword = e => {
        setPassword(e.target.value)
    }


    const handleLogin = (event) => {
        event.preventDefault()

        setMessage("")
        setLoading(true)

        form.current.validateAll()
        // form.validateAll()

        if (checkBtn.current.context._errors.length === 0) {
            UserApi.login(username, password)
                .then( () => {

                        props.history.push("/profile")
                        window.location.reload()
                    }, (error) => {
                        const resMessage = (
                            error.response
                            && error.response.data
                            && error.response.data.message) || error.message || error.toString()

                        setLoading(false)
                        setMessage(resMessage)

                    }
                ).catch(error => {
                console.log(error.response)
            })
        } else {
            setLoading(false)
        }
    }


    return (
        <div>

            <Form className={styles["login-styling"]} onSubmit={handleLogin} ref={form}>

                <div className="form-group">
                    <label htmlFor="username">Username</label>
                    <Input
                        type="text"
                        className="form-control"
                        name="username"
                        value={username}
                        onChange={onChangeUsername}
                        validations={[required]}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <Input
                        type="password"
                        className="form-control"
                        name="password"
                        value={password}
                        onChange={onChangePassword}
                        validations={[required]}
                    />
                </div>

                <div className="form-group">

                    <button className="btn btn-primary btn-block" disabled={loading}>
                        {loading && (
                            <span className="spinner-border spinner-border-sm"/>
                        )}
                        <span>Login</span>
                    </button>

                </div>

                {message && (
                    <div className="form-group">
                        <div className="alert alert-danger" role="alert">
                            {message}
                        </div>
                    </div>
                )}
                    <CheckButton style={{ display: "none" }} ref={checkBtn}/>

            </Form>

        </div>


    )
}
export default Login
