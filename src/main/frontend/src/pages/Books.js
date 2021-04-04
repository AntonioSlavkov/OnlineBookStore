import React, {useEffect, useState} from 'react';
import bookApi from "../utils/api/bookApi";
import {Image} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css'
import Container from 'react-bootstrap/Container';
import {Link} from "react-router-dom";
import Book from "./Book";


const Books = ({match}) => {
    const [books, setBooks] = useState([])
    const id = match.params.id

    useEffect(() => {
        getBooks()
    }, [])

    const getBooks = () => {
        bookApi.getBooks().then((response) =>{
            console.log(response.data)
            setBooks(response.data)

        })
            .catch((e) => {
                console.log(e)
            })
    }

return (
    books.map((book, index) => {
        const {imageUrl} = book.pictureUrls[0];
        return (
            <Container key={index} className={"container"}>
                <div className={"col-md-3"}>
                    <div className={"card mb-4 box-shadow"}>
                        <h4>
                            {book.title}
                        </h4>

                        <Link to={`book/${book.id}`}>
                            <Image className="card-img-top" src={imageUrl} thumbnail={true}/>
                        </Link>

                        <p>
                            {book.price}$
                        </p>
                    </div>
                </div>
            </Container>
        )
    })
)

}

export default Books;