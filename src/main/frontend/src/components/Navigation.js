import React from 'react';
import {Route, Switch} from "react-router-dom";
import App from "../App";
import Login from "../pages/Login";
import Register from "../pages/Register";
import Books from "../pages/Books";
import Profile from "../pages/Profile";
import RootAdminPanel from "../pages/RootAdminPanel";
import AdminPanel from "../pages/AdminPanel";
import Cart from "../pages/Cart";
import Book from "../pages/Book";
import AdminBookOperation from "./AdminBookOperation";
import Home from "../pages/Home";

const Navigation = () => {


    return (
        <Switch>
            <Route path="/" exact component={Home}/>
            <Route path="/login" component={Login}/>
            <Route path="/register" component={Register}/>
            <Route path="/books" component={Books}/>
            <Route path="/profile" component={Profile}/>
            <Route path="/rootAdminPanel" component={RootAdminPanel}/>
            <Route path="/adminPanel" component={AdminPanel}/>
            <Route path="/cart" component={Cart}/>
            <Route path="/book/:id" component={Book}/>
        </Switch>
    )
}
export default Navigation;