import React from "react";
import {Link} from "react-router-dom";
import {DropdownButton} from "react-bootstrap";
import DropdownItem from "react-bootstrap/DropdownItem";

const AdminPanelButton = () => {
    return (
        <div>
                <DropdownButton title="Admin">

                    <DropdownItem as={Link} to="/adminPanel/Books">Books</DropdownItem>
                    <DropdownItem as={Link} to="/adminPanel/Orders">Orders</DropdownItem>
                </DropdownButton>
        </div>
    )
}
export default AdminPanelButton;