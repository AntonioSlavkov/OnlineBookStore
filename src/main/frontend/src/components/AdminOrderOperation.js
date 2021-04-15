import React, {useEffect, useState} from "react";
import OrdersApi from "../utils/api/OrdersApi";
import UserApi from "../utils/api/UserApi";
import {Container, Table} from "react-bootstrap";


const AdminOrderOperation = () => {

    const username = UserApi.getCurrentUser()
    const [allOrders, setAllOrders] = useState([])
    const [searchUserForOrder, setSearchUserForOrder] = useState('')
    const [searchedUserOrder, setSearchedUserOrder] = useState([])
    const [statusName, setStatusName] = useState()
    const [orderId, setOrderId] = useState()

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
        })
    }

    const updateOrder = () => {
        OrdersApi.updateOrder(orderId, statusName).then(response => {
            console.log(response)
        }).catch(error => {
            console.log(error.response)
        })
    }


    return (
        <div>

            <Container>
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
                    <h3>Show user order</h3>
                </div>

                <input type="text"
                       placeholder="Enter username"
                       value={searchUserForOrder}
                       onChange={updateSearchUserForOrder}/>
                <button type="button" onClick={getOrdersByUser}>Search</button>

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


            <Container>
                <div>
                    <h3>Update order status</h3>
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

                <button type="button" onClick={updateOrder}>Update</button>

            </Container>


        </div>
    )
}

export default AdminOrderOperation