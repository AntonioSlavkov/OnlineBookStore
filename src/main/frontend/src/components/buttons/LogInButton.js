import React from "react";
import {Link, Route} from "react-router-dom";

const LogInButton = () => {
    return (
        <div>
            <Link to="/login">
                <button>Log In</button>
            </Link>
        </div>

    )
}
export default LogInButton;