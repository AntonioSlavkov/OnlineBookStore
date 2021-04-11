import React, {useEffect, useState} from "react"
import bookApi from "../utils/api/bookApi";
import {Button, Table} from "react-bootstrap";
import BookAddToCartContext from "../components/BookAddToCartContext";


const Book = (props) => {

    console.log(props)
    const id = props.match.params.id
    console.log(id)
    const [book, setBook] = useState('')
    const {imageUrl} = book.pictureUrls[0];
    // const {category} = book.mainCategory;


    let bookToAdd = {
        // author: book.author,
        title: book.title,
        // price: book.price,
        // id: book.id
    }
    console.log(bookToAdd)


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

    const addToLocalStorage = (bookToAdd) => {
        localStorage.setItem("cart", JSON.stringify(bookToAdd))
    }

//TODO render the other properties of the book
    return (
        <div className="container">
            <div className="row">
                <div className="col-md8">
                    <div className="row">
                        <div className="col-md-4">
                            <img className="img-fluid" src={imageUrl}/>
                        </div>
                        <div>
                            <h1>{book.title}</h1>
                            <Table>
                                <thead>
                                <tr>
                                    <th>Language</th>
                                    <th>Pages</th>
                                    <th>Main Category</th>
                                    <th>Sub categories</th>
                                    <th>Price</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>{book.language}</td>
                                    <td>{book.pages}</td>
                                    {/*<td>{category}</td>*/}
                                </tr>

                                </tbody>
                            </Table>
                        </div>
                    </div>
                </div>
            </div>


            {/*<p>{book.language}</p>*/}
            {/*<p>{book.description}</p>*/}
            {/*<p>{book.price}</p>*/}
            {/*<p>{book.category}</p>*/}
            <Button onClick={addToLocalStorage}>Add to Cart</Button>


        </div>
    )
}
export default Book;