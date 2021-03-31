import axios from "axios";

const API_URL = "http://http://localhost:8080/roles/"

const getUserRoles = async () => {
    return await axios.get(API_URL + "all")
}

const addRoleToUser = async () => {
    return await axios.post(API_URL + "add")
}

const deleteUserRole = async () => {
    return await axios.delete(API_URL + "delete")
}

export default {
    getUserRoles,
    addRoleToUser,
    deleteUserRole
}