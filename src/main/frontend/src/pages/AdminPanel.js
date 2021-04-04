import React, {useState} from "react"
import AdminNavigation from "../components/AdminNavigation";
import SideNav, { Toggle, Nav, NavItem, NavIcon, NavText } from '@trendmicro/react-sidenav';
import '@trendmicro/react-sidenav/dist/react-sidenav.css';


const AdminPanel = () => {

    return (
        <div>
            <AdminNavigation/>
        </div>
    )
}
export default AdminPanel;