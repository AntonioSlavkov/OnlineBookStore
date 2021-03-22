import React from "react";
import {Link, Route} from "react-router-dom";

const SignUpButton = () => {
    return (
        <div>
            <Link to="/register">
                <button>Sign Up</button>
            </Link>
        </div>
    )
}
export default SignUpButton;