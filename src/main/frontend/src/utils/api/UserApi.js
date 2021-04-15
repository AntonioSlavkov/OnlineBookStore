import axios from "axios";

const API_URL = "http://localhost:8080/users/"

const register = async (username, email, password, firstName, lastName) => {
    console.log(email)
    console.log(username)
    return await axios.post(API_URL + "register", {
        username,
        email,
        password,
        firstName,
        lastName
    })
}

const login = async (username, password) => {
    return await axios.post(API_URL + "login", {
        username,
        password
    })
        .then(response => {
            console.log(response)
            if (response.data.token) {
                localStorage.setItem("username", JSON.stringify(response.data))
                console.log(localStorage.getItem("username"))
            }
            return response.data
        })
        .catch(error => {
            console.log(error.response)
        })
}

const logout = () => {
    localStorage.removeItem("username")
}

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("username"))
}

export default {
    register, login, logout, getCurrentUser
};