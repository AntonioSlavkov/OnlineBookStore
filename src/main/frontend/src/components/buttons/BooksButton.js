import React from "react";
import {Link} from "react-router-dom";

const Books = () => {
    return (
        <div>
            <Link to="/books">
                <button>Books</button>
            </Link>
        </div>
    )
}
export default Books;