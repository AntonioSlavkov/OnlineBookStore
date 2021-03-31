import axios from "axios";

const API_URL = "http://http://localhost:8080/categories/"

const getCategories = async () => {
    return await axios.get(API_URL + "all")
}

export default {
    getCategories
}