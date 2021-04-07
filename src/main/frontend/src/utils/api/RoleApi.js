import axios from "axios";
import AuthHeader from "./AuthHeader";

const API_URL = "http://localhost:8080/roles/"

const getUserRoles = async (username) => {
    return await axios.get(API_URL + "all", {

        headers: AuthHeader(),

        params: {
            username: username
        }
    })
}

const addRoleToUser = async (username, roleName) => {
    return await axios.post(API_URL + "add", {
        username,
        roleName
    }, {

        headers: AuthHeader()
    })
}

const deleteUserRole = async (username, roleName) => {
    return await axios.delete(API_URL + "delete", {

        headers: AuthHeader(),

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