import axios from "axios";

const API_URL = 'http://localhost:8080/orders/'

const getOrders = async () => {
    return await axios.get(API_URL + "/all")
}

const getOrdersByUser = async (username) => {
    return await axios.get(API_URL + "/all", {

        params: {
            username: username
        }
    })
}
const addOrder = async (user, books) => {
    return await axios.post(API_URL + "/add", {
        user,
        books
    })
}
const updateOrder = async (id, statusName) => {
    return await axios.post(API_URL + "/add",{
        id,
        statusName

    })
}