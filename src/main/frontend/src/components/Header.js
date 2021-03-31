import React from "react"
import SignUpButton from "./buttons/SignUpButton";
import LogInButton from "./buttons/LogInButton";
import IndexButton from "./buttons/IndexButton";
import BooksButton from "./buttons/BooksButton";
import CartButton from "./buttons/CartButton";
import ProfileButton from "./buttons/ProfileButton";
import RootAdminPanelButton from "./buttons/RootAdminPanelButton";
import AdminPanelButton from "./buttons/AdminPanelButton";


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
                    <AdminPanelButton/>
                    <BooksButton/>
                    <CartButton/>
                    <ProfileButton/>
                    <RootAdminPanelButton/>
                </nav>
            </div>
        </header>
    )
}
export default Header
