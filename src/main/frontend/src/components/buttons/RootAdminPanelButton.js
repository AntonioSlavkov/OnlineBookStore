import React from "react";
import {Link} from "react-router-dom";

const RootAdminPanelButton = () => {
    return (
        <div>
            <Link to="/rootAdminPanel">
                <button>RootAdmin</button>
            </Link>
        </div>
    )
}
export default RootAdminPanelButton;