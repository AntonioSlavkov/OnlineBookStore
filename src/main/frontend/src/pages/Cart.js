import React, {useEffect, useState} from "react"
import CartApi from "../utils/api/CartApi";
import UserApi from "../utils/api/UserApi";
import bookApi from "../utils/api/bookApi";
import {Button, Container, Table} from "react-bootstrap";
import OrdersApi from "../utils/api/OrdersApi";
import styled from "styled-components";

const Cart = () => {

    const username = UserApi.getCurrentUser()
    const [book, setBook] = useState([]);



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
            console.log(response.data)
            setBook(oldBooks => [...oldBooks, response.data])

        }).catch(error => {
            console.log(error.response)
        })
    }

    const addOrder = () => {
        // setBook(book => book.filter( (_,i) => i !== 0))
        console.log(book)
        OrdersApi.addOrder(username.username, book)
            .then(response => {
            console.log(response)
        }).catch(error => {
            console.log(error.response)
        })
    }

    const MainDiv = styled.div`
        padding-top: 40px;
    `

    const HeaderTwo = styled.h2`
        text-align: center;
        padding-bottom: 20px;
    `

    const HeaderOneForCart = styled.h1`
        padding-left: 600px
    `


    return (
        <MainDiv>
            <HeaderTwo>Cart</HeaderTwo>
            {book.length ? (
                <>
                    <Table responsive={true} striped={false} size="lg" bordered={true}>
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
                    <div>
                        <Button variant={"primary"} size={"lg"} onClick={addOrder}>Place order</Button>
                    </div>



                </>


            ) : (<><HeaderOneForCart>Your cart is empty</HeaderOneForCart> </>) }



        </MainDiv>

    )
}
export default Cart;