import React, {useEffect, useState} from 'react';
import bookApi from "../utils/api/bookApi";
import {Card, CardDeck, CardGroup, Image} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css'
import Container from 'react-bootstrap/Container';
import {Link} from "react-router-dom";
import Book from "./Book";
import 'bootstrap/dist/css/bootstrap.min.css';
import styled from "styled-components";


const Books = ({match}) => {
    const [books, setBooks] = useState([])
    const id = match.params.id
    console.log(id)

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

    const Container = styled.div`
        padding: 0px
    `

    return (
        books.map((book, index) => {
            const {imageUrl} = book.pictureUrls[0];
            return (
                <Container key={index} className={"container"}>
                    <CardDeck className="row row-cols-1 row-cols-sm-4">
                        <div className="col">
                            <Card className="mb-4 box-shadow">
                                <Link to={`book/${book.id}`}>
                                    <Card.Img className="img-fluid" variant="top" src={imageUrl} thumbnail={true}/>
                                </Link>
                                <Card.Body>
                                    <Card.Title>{book.title}</Card.Title>
                                    <Card.Text>Price: {book.price}$</Card.Text>

                                </Card.Body>
                            </Card>

                        </div>

                    </CardDeck>
                </Container>
            )
        })
    )

}

export default Books;