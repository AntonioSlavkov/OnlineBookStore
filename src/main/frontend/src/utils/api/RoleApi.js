import axios from "axios";

const API_URL = "http://localhost:8080/roles/"

const getUserRoles = async (username) => {
    return await axios.get(API_URL + "all", {
        params: {
            username: username
        }
    })
}

const addRoleToUser = async (username, roleName) => {
    return await axios.post(API_URL + "add", {
        username,
        roleName
    })
}

const deleteUserRole = async (username, roleName) => {
    return await axios.delete(API_URL + "delete", {
        params: {
            username: username,
            roleName: roleName
        }
    })
}

export default {
    getUserRoles,
    addRoleToUser,
    deleteUserRole
}