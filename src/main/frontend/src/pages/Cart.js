import React, {useEffect, useState} from "react"
import CartApi from "../utils/api/CartApi";
import UserApi from "../utils/api/UserApi";
import bookApi from "../utils/api/bookApi";
import {Container, Table} from "react-bootstrap";

const Cart = () => {

    const username = UserApi.getCurrentUser()
    const [book, setBook] = useState([{}]);

    console.log(book)
    useEffect(() => {
        getUserCart()
    }, [])

    const getUserCart = () => {
        CartApi.getUserCart(username.username).then(response => {
            console.log(response)

            response.data.forEach(e => {
                const {bookId: bookId1} = e;
                getBooksById(bookId1)
            })
        }).catch(error => {
            console.log(error.response)
        })
    }

    const getBooksById = (bookId) => {
        bookApi.getBookById(bookId).then(response => {
            // console.log(response)
            setBook(oldBooks => [...oldBooks, response.data])
        }).catch(error => {
            console.log(error.response)
        })
    }




    return (
        <div>

            <Table striped={false} size="sm">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Title</th>
                    <th>Price</th>
                </tr>
                </thead>
                <tbody>
                {
                    book.map((theBook, index) => {

                        if (index === 0) {
                            return null;
                        }
                        return (
                            <tr>
                                <td>{index}</td>
                                <td>{theBook.title}</td>
                                <td>{theBook.price}</td>
                            </tr>
                        )
                    })
                }
                </tbody>
            </Table>
            <button>Place order</button>

        </div>

    )
}
export default Cart;