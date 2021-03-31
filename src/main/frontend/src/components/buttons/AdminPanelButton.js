import React from "react";
import {Link} from "react-router-dom";

const AdminPanelButton = () => {
    return (
        <div>
            <Link to="/adminPanel">
                <button>Admin</button>
            </Link>
        </div>
    )
}
export default AdminPanelButton;