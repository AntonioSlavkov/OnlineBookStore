import axios from "axios";
import AuthHeader from "./AuthHeader";

const API_URL = "http://localhost:8080/contacts/"

const getUserContactInformation = async (username) => {
    return await axios.get(API_URL + "contact", {

        headers: AuthHeader(),

        params: {
            username: username
        }
    })
}

const updateUserContactInformation = async (phoneNumber, city, address, username) => {

    console.log(phoneNumber)
    console.log(city)
    console.log(address)
    console.log(username)

    return await axios.post(API_URL + "update",{
        username,
        phoneNumber,
        city,
        address
    })
}

export default {
    getUserContactInformation,
    updateUserContactInformation
}