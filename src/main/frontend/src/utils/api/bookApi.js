import axios from "axios";

const API_URL = 'http://localhost:8080/books/'

const getBooks = async () => {
    return await axios.get(API_URL + 'all')
}

const getBookById = async (id) => {
    return await axios.get(API_URL + "book/" + id)
}

//TODO pictureUrls, subCategories are arrays. check how to add arays in js junctions
const addBook = async (title, pages, language, description, price, pictureUrls, author, category, subCategories) => {
    console.log(title)
    console.log(pictureUrls)
    console.log(subCategories)
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
    }).then(response => {
        console.log(response)
    }).catch(error => {
        console.log(error.response)
    })
}
//TODO add id to the function
const deleteBook = async (id) => {
    return await axios.delete(API_URL + "delete/" + id)
}

export default {
    getBooks,
    addBook,
    getBookById,
    deleteBook
}