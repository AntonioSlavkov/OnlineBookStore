import React from "react";
import {Link} from "react-router-dom";

const IndexButton = () => {
    return (
        <div>
            <Link to="/">
                <button>Начало</button>
            </Link>
        </div>
    )
}
export default IndexButton;