import React from "react";
import {Route, Switch} from "react-router-dom";
import AdminBookOperation from "./AdminBookOperation";
import AdminOrderOperation from "./AdminOrderOperation";

const AdminNavigation = () => {

    return (
        <Switch>
            <Route path="/adminPanel/Books" component={AdminBookOperation}/>
            <Route path="/adminPanel/Orders" component={AdminOrderOperation}/>
        </Switch>
    )
}

export default AdminNavigation