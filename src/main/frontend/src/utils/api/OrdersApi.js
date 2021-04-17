import axios from "axios";

const API_URL = 'http://localhost:8080/orders/'

const getOrders = async () => {
    return await axios.get(API_URL + "all")
}

const getOrdersByUser = async (username) => {
    return await axios.get(API_URL + "user", {

        params: {
            username: username
        }
    })
}
const addOrder = async (username, books) => {
    return await axios.post(API_URL + "add", {
        username,
        books
    })
}
const updateOrder = async (id, statusName) => {
    return await axios.post(API_URL + "update",{
        id,
        statusName

    })
}

const deleteOrder = async (id) => {
    return await axios.delete(API_URL + "delete/" + id)
}

export default {
    getOrders,
    getOrdersByUser,
    addOrder,
    updateOrder,
    deleteOrder
}