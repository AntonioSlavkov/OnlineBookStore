import React, {useEffect, useState} from "react";
import OrdersApi from "../utils/api/OrdersApi";
import UserApi from "../utils/api/UserApi";
import {Button, Container, Table} from "react-bootstrap";
import styles from "./AdminOrderOperation.module.css"


const AdminOrderOperation = () => {

    const username = UserApi.getCurrentUser()
    const [allOrders, setAllOrders] = useState([])
    const [searchUserForOrder, setSearchUserForOrder] = useState('')
    const [searchedUserOrder, setSearchedUserOrder] = useState([])
    const [statusName, setStatusName] = useState()
    const [orderId, setOrderId] = useState()
    const [getUserOrderServerMessage, setGetUserOrderServerMessage] = useState('')
    const [getOrderIdServerMessage, setGetOrderIdServerMessage] = useState('')
    console.log(getOrderIdServerMessage)

    const updateStatusName = e => {
        setStatusName(e.target.value)
    }

    const updateOrderId = e => {
        setOrderId(e.target.value)
    }

    useEffect(() => {
        getAllOrders()
    }, [])

    const updateSearchUserForOrder = e => {
        setSearchUserForOrder(e.target.value)
    }

    const getAllOrders = () => {
        OrdersApi.getOrders().then(response => {
            console.log(response)
            setAllOrders(response.data)
        }).catch(error => {
            console.log(error.response)
        })
    }

    const getOrdersByUser = () => {
        OrdersApi.getOrdersByUser(searchUserForOrder).then(response => {
            console.log(response)
            setSearchedUserOrder(response.data)
        }).catch(error => {
            console.log(error.response)
            setGetUserOrderServerMessage(error.response.data.message)
        })
    }

    const updateOrder = () => {
        OrdersApi.updateOrder(orderId, statusName).then(response => {
            console.log(response)
        }).catch(error => {
            console.log(error.response)
            setGetOrderIdServerMessage(error.response.data.message)
        })
    }


    return (
        <div>

            <Container>

                <h1>All orders</h1>
                <Table>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Book Title</th>
                        <th>Price</th>
                        <th>Username</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    {allOrders.map((order, index) => {

                        return (
                            order.books.map(theBook => {


                                return (

                                    <tr>
                                        <td>{order.id}</td>
                                        <td>{theBook.title}</td>
                                        <td>{theBook.price}</td>
                                        <td>{order.user.username}</td>
                                        <td>{order.status}</td>
                                    </tr>
                                )

                            })

                        )

                    })}
                    </tbody>
                </Table>
            </Container>


            <Container>
                <div>
                    <h3 className={styles["header-three-style"]}>Show user contact details</h3>
                </div>

                <input type="text"
                       placeholder="Enter username"
                       value={searchUserForOrder}
                       onChange={updateSearchUserForOrder}/>
                <Button variant={"primary"} type="button" onClick={getOrdersByUser}>Search</Button>

                <Table>
                    <thead>
                    <tr>
                        <th>Email address</th>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>City</th>
                        <th>Address</th>
                        <th>Phone number</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>{searchedUserOrder && searchedUserOrder.map(theOrder => theOrder.user.email)}</td>
                        <td>{searchedUserOrder && searchedUserOrder.map(theOrder => theOrder.user.firstName)}</td>
                        <td>{searchedUserOrder && searchedUserOrder.map(theOrder => theOrder.user.lastName)}</td>
                        <td>{searchedUserOrder && searchedUserOrder.map(theOrder => {
                            return (
                                <div>{theOrder.user.userContactEntity.city}</div>
                            )
                        })}</td>
                        <td>{searchedUserOrder && searchedUserOrder.map(theOrder => {
                            return (
                                <div>{theOrder.user.userContactEntity.address}</div>
                            )
                        })}</td>
                        <td>{searchedUserOrder && searchedUserOrder.map(theOrder => {
                            return (
                                <div>{theOrder.user.userContactEntity.phoneNumber}</div>
                            )
                        })}</td>


                    </tr>

                    </tbody>
                </Table>
            </Container>

            {getUserOrderServerMessage && <h2>{getUserOrderServerMessage}</h2>}


            <Container>
                <div>
                    <h3 className={styles["header-three-style"]}>Update order status</h3>
                </div>

                <input type="text"
                       placeholder="Enter Order Id"
                       value={orderId}
                       onChange={updateOrderId}/>

                <label>
                    <select value={statusName} onChange={updateStatusName}>
                        <option value="APPROVED">APPROVED</option>
                        <option value="PROCESSING">PROCESSING</option>
                        <option value="COMPLETE">COMPLETE</option>
                    </select>

                </label>

                <Button variant={"primary"} type="button" onClick={updateOrder}>Update</Button>

            </Container>

            {getOrderIdServerMessage && <h2>{getOrderIdServerMessage}</h2>}

        </div>
    )
}

export default AdminOrderOperation