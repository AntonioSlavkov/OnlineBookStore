import axios from "axios";

const API_URL = "http://http://localhost:8080/users/"

const register = async (username, email, password, firstName, lastName) => {
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
        .then((response) => {
            if (response.data.accessToken) {
                localStorage.setItem("user", JSON.stringify(response.data))
            }
            return response.data
        })
}

const logout = () => {
    localStorage.removeItem("user")
}

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"))
}

export default {
    register, login, logout, getCurrentUser
};