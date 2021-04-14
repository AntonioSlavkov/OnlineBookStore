import React, {useEffect, useState} from "react"
import bookApi from "../utils/api/bookApi";
import {Button, Table} from "react-bootstrap";
import BookAddToCartContext from "../components/BookAddToCartContext";
import {useParams} from "react-router";
import CartApi from "../utils/api/CartApi";
import UserApi from "../utils/api/UserApi";


const Book = () => {
    let { id: id } = useParams();
    console.log(id)
    const [book, setBook] = useState([])
    const username = UserApi.getCurrentUser()
    console.log(username.username)

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

    const addBookToUserCart = () => {
        CartApi.addBookToCart(book.id, username.username).then(response => {
            console.log(response)
        }).catch(error => {
            console.log(error.response)
        })
    }

//TODO render the other properties of the book
    return (

        <div className="container">
            <div className="row">
                <div className="col-md8">
                    <div className="row">
                        <div className="col-md-4">
                            <img className="img-fluid" src={book.pictureUrls && book.pictureUrls.map( url =>{
                                return url.imageUrl
                            })}/>
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
                                    <th>Author</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>{book.language}</td>
                                    <td>{book.pages}</td>
                                    <td>{book.mainCategory && book.mainCategory.category}</td>
                                    <td>{book.subCategories && book.subCategories.map( subCat => {
                                        return <li>{subCat.category}</li>
                                    })}</td>
                                    <td>{book.price}</td>
                                    <td>{book.author && book.author.author}</td>
                                </tr>
                                </tbody>
                            </Table>

                        </div>
                    </div>
                </div>
            </div>

            <div>
                <p>{book.description && book.description}</p>
            </div>

            <Button onClick={addBookToUserCart}>Add to Cart</Button>


        </div>
    )
}
export default Book;