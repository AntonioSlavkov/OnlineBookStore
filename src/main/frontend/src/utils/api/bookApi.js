import axios from "axios";

const API_URL = 'http://localhost:8080/books/'

const getBooks = async () => {
    return await axios.get(API_URL + 'all')
}
//TODO pictureUrls, subCategories are arrays. check how to add arays in js junctions
const addBook = async (title, pages, language, description, price, pictureUrls, author, category, subCategories) => {
    return await axios.post(API_URL + "add", {
        title,
        pages,
        language,
        description,
        price,
        pictureUrls,
        author,
        category,
        subCategories
    })
}
//TODO add id to the function
const deleteBook = async () => {
    return await axios.delete(API_URL + "delete")
}

export default {
    getBooks,
    addBook
}