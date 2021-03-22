import React, {useState} from 'react';
import Input from "../components/input/input";

const Login = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

   const handleSubmit = (event) => {
        event.preventDefault()

       const data = {
            username: this.username,
           password: password
       }
    }

    return (
        <form onSubmit={handleSubmit}>
            <Input
                value={username}
                onChange={e => setUsername(e.target.value)}
                label="Username"
            />

            <Input
                type="password"
                value={password}
                onChange={e => setPassword(e.target.value)}
                label="Password"
            />

            <div>
                <input type="submit"/>
            </div>
        </form>

    )
}
export default Login
