import './App.css';
import Header from "./components/Header";
import React, {useState, useEffect, useContext} from "react";
import UserApi from "./utils/api/UserApi";
import {Link} from "react-router-dom";
import {DropdownButton} from "react-bootstrap";
import DropdownItem from "react-bootstrap/DropdownItem";

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

    return (
        <div className="App">
            <nav className="navbar navbar-expand navbar-dark bg-dark">

                <Link to={"/"} className="navbar-brand">
                    Index
                </Link>

                <Link to={"/books"} className="navbar-brand">
                    Books
                </Link>

                {showCart && (
                    <Link to={"/cart"} className="navbar-brand">
                        Cart
                    </Link>

                )}

                {showProfile && (
                    <Link to={"/profile"} className="navbar-brand">
                        Profile
                    </Link>
                )}


                {showAdminPanel && (
                    <DropdownButton title="Admin" className="navbar-brand">

                        <DropdownItem as={Link} to="/adminPanel/Books">Books</DropdownItem>
                        <DropdownItem as={Link} to="/adminPanel/Orders">Orders</DropdownItem>
                    </DropdownButton>
                )}

                {showRootAdminPanel && (
                    <Link to={"/rootAdminPanel"} className="navbar-brand">
                        Root Admin
                    </Link>
                )}


                {currentUser ? (
                    <div className="navbar-nav ml-auto">

                        <li className="nav-item">
                            <Link to={"/profile"} className="nav-link">
                                {currentUser.username}
                            </Link>
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

                        <li className="nav-item">
                            <Link to={"/register"} className="navbar-brand">
                                Register
                            </Link>
                        </li>
                    </div>
                )}

            </nav>
        </div>
    );
}

export default App;
