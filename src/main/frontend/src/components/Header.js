import React from "react"
import SignUpButton from "./buttons/SignUpButton";
import LogInButton from "./buttons/LogInButton";
import IndexButton from "./buttons/IndexButton";
import BooksButton from "./buttons/BooksButton";
import CartButton from "./buttons/CartButton";
import ProfileButton from "./buttons/ProfileButton";
import RootAdminPanelButton from "./buttons/RootAdminPanelButton";
import AdminPanelButton from "./buttons/AdminPanelButton";
import {ButtonGroup} from "react-bootstrap";



const Header = () => {
    return (
        <header>
            <div>
                <ButtonGroup>
                    <SignUpButton/>
                    <LogInButton/>
                </ButtonGroup>

            </div>

            <div>
                <nav>
                    <ButtonGroup>
                        <IndexButton/>
                        <BooksButton/>
                        <CartButton/>
                        <ProfileButton/>
                        <AdminPanelButton/>
                        <RootAdminPanelButton/>
                    </ButtonGroup>

                </nav>
            </div>
        </header>
    )
}
export default Header
