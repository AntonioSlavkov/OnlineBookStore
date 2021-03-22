import React from "react"
import SignUpButton from "./buttons/SignUpButton";
import LogInButton from "./buttons/LogInButton";
import Navigation from "./Navigation";
import IndexButton from "./buttons/IndexButton";


const Header = () => {
    return (
        <header>
            <div>
                <SignUpButton/>
                <LogInButton/>
            </div>

            <div>
                <nav>
                    <IndexButton/>
                </nav>
            </div>
        </header>
    )
}
export default Header
