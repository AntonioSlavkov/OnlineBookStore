import React from "react";
import {Link} from "react-router-dom";

const ProfileButton = () => {
    return (
        <div>
            <Link to="/profile">
                <button>Profile</button>
            </Link>
        </div>
    )
}
export default ProfileButton;