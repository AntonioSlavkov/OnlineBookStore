import React, {useEffect, useState} from 'react';
import bookApi from "../utils/api/bookApi";
import {Image} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css'
import Container from 'react-bootstrap/Container';


const Books = () => {
    const [books, setBooks] = useState([])

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
            <Container key={index} className={"row"}>
                <div className={"col-md-3"}>
                    <div className={"card mb-4 box-shadow"}>
                        <h4>
                            {book.title}
                        </h4>
                        <Image className="card-img-top" src={imageUrl} thumbnail={true}/>
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