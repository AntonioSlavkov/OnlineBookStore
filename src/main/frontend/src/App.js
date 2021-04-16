import './App.css';
import React, {useState, useEffect, useContext} from "react";
import UserApi from "./utils/api/UserApi";
import {Link} from "react-router-dom";
import {Container, DropdownButton, Nav, Navbar, NavDropdown} from "react-bootstrap";
import styled from "styled-components";


function App() {
    const [currentUser, setCurrentUser] = useState(undefined)
    const [showAdminPanel, setAdminPanel] = useState(false)
    const [showRootAdminPanel, setRootAdminPanel] = useState(false)
    const [showProfile, setProfile] = useState(false)
    const [showCart, setCart] = useState(false)


    useEffect(() => {

        const user = UserApi.getCurrentUser()

        if (user) {
            setCurrentUser(user)
            setProfile(user.roles.includes("REGULAR"))
            setCart(user.roles.includes("REGULAR"))
            setAdminPanel(user.roles.includes("ADMIN"))
            setRootAdminPanel(user.roles.includes("ROOT_ADMIN"))
        }


    }, [])

    const logOut = () => {
        UserApi.logout()
    }

    const Container = styled.div`

    padding: 40px;
`

    return (
        <div className="App">
            <Container>
                    <Navbar bg="dark" variant="dark" fixed="top">
                        {/*<Navbar.Toggle aria-controls="responsive-navbar-nav" />*/}
                        {/*<Navbar.Collapse id="responsive-navbar-nav">*/}
                            <Nav className="mr-auto">

                                <Nav.Link href="/">Home</Nav.Link>
                                <Nav.Link href="/books">Books</Nav.Link>

                                {showCart && (
                                    <Nav.Link href="/cart">Cart</Nav.Link>
                                )}

                                {showProfile && (
                                    <Nav.Link href="/profile">Profile</Nav.Link>
                                )}


                                {showAdminPanel && (
                                    <NavDropdown title="Admin" id="collapsible-nav-dropdown">
                                        <NavDropdown.Item href="/adminPanel/Books">Books</NavDropdown.Item>
                                        <NavDropdown.Item href="/adminPanel/Orders">Orders</NavDropdown.Item>
                                    </NavDropdown>
                                )}

                                {showRootAdminPanel && (
                                    <Nav.Link href="/rootAdminPanel">Root Admin</Nav.Link>
                                )}
                            </Nav>
                            <Navbar >
                                <Nav>
                                    {currentUser ? (
                                        <div className="navbar-nav ml-auto">

                                            <li className="nav-item">
                                                <Nav.Link href="/profile">{currentUser.username}</Nav.Link>
                                            </li>
                                            <li className="nav-item">

                                                <a href="/login" className="nav-link" onClick={logOut}>
                                                    LogOut
                                                </a>
                                            </li>
                                        </div>
                                    ) : (
                                        <div className="navbar-nav ml-auto">
                                            <li className="nav-item">

                                                <Link to={"/login"} className="nav-link">
                                                    Login
                                                </Link>
                                            </li>

                                            <li className="navbar-nav ml-auto">
                                                <Link to={"/register"} className="nav-link">
                                                    Register
                                                </Link>
                                            </li>
                                        </div>
                                    )}
                                </Nav>
                            </Navbar>
                        {/*</Navbar.Collapse>*/}
                    </Navbar>
            </Container>
        </div>

    );
}

export default App;
