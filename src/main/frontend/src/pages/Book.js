import React, {useEffect, useState} from "react"
import bookApi from "../utils/api/bookApi";


const Book = (props) => {
    console.log(props)
    const id = props.match.params.id
    console.log(id)
    const [book, setBook] = useState('');

    useEffect(() => {
        getBookById()
    }, [])

    const getBookById = () => {
        bookApi.getBookById(id)
            .then(response => {
                setBook(response.data)
                console.log(response)
            }).catch(error => {
            console.log(error.response)
        })
    }
//TODO render the other properties of the book
    return (
        <div>
            <h1>{book.title}</h1>
            <p>{book.pages}</p>
            <p>{book.language}</p>
            <p>{book.description}</p>
            <p>{book.price}</p>
            <p>{book.category}</p>


        </div>
    )
}
export default Book;