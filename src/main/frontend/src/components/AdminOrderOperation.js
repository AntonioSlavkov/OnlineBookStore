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
    const [orderIdToDelete, setOrderIdToDelete] = useState('')
    const [orderDeleteServerMessage, setOrderDeleteServerMessage] = useState('')
    console.log(getOrderIdServerMessage)

    const updateOrderIdToDelete = e => {
        setOrderIdToDelete(e.target.value)
    }

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

    const deleteOrderById = () => {
            OrdersApi.deleteOrder(orderIdToDelete).then(response => {
                setOrderDeleteServerMessage(response.data.message)
            }).catch(error => {
                setOrderDeleteServerMessage(error.response.data.message)
            })



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

            setGetOrderIdServerMessage(response.data.message)
        }).catch(error => {

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
                        <td>{searchedUserOrder.user && searchedUserOrder.user.email}</td>

                        <td>{searchedUserOrder.user && searchedUserOrder.user.firstName}</td>

                        <td>{searchedUserOrder.user && searchedUserOrder.user.lastName}</td>

                        <td>{searchedUserOrder.user.userContactEntity &&
                        searchedUserOrder.user.userContactEntity.city}</td>

                        <td>{searchedUserOrder.user.userContactEntity &&
                        searchedUserOrder.user.userContactEntity.address}</td>

                        <td>{searchedUserOrder.user.userContactEntity &&
                        searchedUserOrder.user.userContactEntity.phoneNumber}</td>
                        {/*<td>{searchedUserOrder && searchedUserOrder.map(theOrder => theOrder.user.email)}</td>*/}
                        {/*<td>{searchedUserOrder && searchedUserOrder.map(theOrder => theOrder.user.firstName)}</td>*/}
                        {/*<td>{searchedUserOrder && searchedUserOrder.map(theOrder => theOrder.user.lastName)}</td>*/}
                        {/*<td>{searchedUserOrder && searchedUserOrder.map(theOrder => {*/}
                        {/*    return (*/}
                        {/*        <div>{theOrder.user.userContactEntity.city}</div>*/}
                        {/*    )*/}
                        {/*})}</td>*/}
                        {/*<td>{searchedUserOrder && searchedUserOrder.map(theOrder => {*/}
                        {/*    return (*/}
                        {/*        <div>{theOrder.user.userContactEntity.address}</div>*/}
                        {/*    )*/}
                        {/*})}</td>*/}
                        {/*<td>{searchedUserOrder && searchedUserOrder.map(theOrder => {*/}
                        {/*    return (*/}
                        {/*        <div>{theOrder.user.userContactEntity.phoneNumber}</div>*/}
                        {/*    )*/}
                        {/*})}</td>*/}


                    </tr>

                    </tbody>
                </Table>
            </Container>

            {getUserOrderServerMessage && <h2 className={styles["serverMessage-style"]}>{getUserOrderServerMessage}</h2>}


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

            {getOrderIdServerMessage && <h2 className={styles["serverMessage-style"]}>{getOrderIdServerMessage}</h2>}

            <Container>
                <div>
                    <h3 className={styles["header-three-style"]}>{"Delete order by id"}</h3>
                </div>
                <div className="input-group mb-3">
                    <form className={styles["delete-book-form"]}>
                        <input
                            type="text"
                            className="form-control"
                            placeholder={"Enter order id"}
                            value={orderIdToDelete}
                            onChange={updateOrderIdToDelete}
                        />

                        <div className="input-group-append">
                            <Button
                                variant={"primary"}
                                type="button"
                                onClick={deleteOrderById}
                            >
                                Delete order
                            </Button>
                        </div>
                    </form>

                </div>
            </Container>

            {orderDeleteServerMessage && <h2 className={styles["serverMessage-style"]}>{orderDeleteServerMessage}</h2>}

        </div>
    )
}

export default AdminOrderOperation