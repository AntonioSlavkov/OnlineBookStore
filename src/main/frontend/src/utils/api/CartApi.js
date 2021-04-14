import axios from "axios";

const API_URL = "http://localhost:8080/cart/"

const getUserCart = async (username) => {
    return await axios.get(API_URL + "all", {
        params: {
            username: username
        }
    })
}

const addBookToCart = async (id, username) => {
    return await axios.post(API_URL + "add",{
        id,
        username
    })
}

export default {
    getUserCart,
    addBookToCart
}