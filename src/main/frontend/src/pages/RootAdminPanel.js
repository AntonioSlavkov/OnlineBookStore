import React, {useEffect, useState} from "react"
import RoleApi from "../utils/api/RoleApi";
import styles from "./css/rootAdmin.module.css"
import {Button} from "react-bootstrap";


const RootAdminPanel = () => {

    const [userRoles, setUserRoles] = useState([])
    const [searchUser, setSearchUser] = useState('')
    const [roleName, setRoleName] = useState()
    const [allRoles, setAllRoles] = useState([
        {
            role: "REGULAR"
        },
        {
            role: "ADMIN"
        },
        {
            role: "ROOT_ADMIN"
        }
    ])
    const [serverMessage, setServerMessage] = useState('')

    useEffect(() => {
        getRoles()

    }, [])

    const updateUserSearch = e => {
        setSearchUser(e.target.value)
    }

    const updateUserAddRole = e => {
        setRoleName(e.target.value)
    }

    const updateUserDeleteRole = e => {
        setRoleName(e.target.value)
    }

    const getRoles = () => {

        RoleApi.getUserRoles(searchUser).then(response => {
            setUserRoles(response.data)
            console.log(userRoles)
        }).catch(error => {
            console.log(error.response)
        })
    }

    const deleteRole = () => {
        RoleApi.deleteUserRole(searchUser, roleName)
            .then(response => {
                console.log(response)
                setServerMessage(response.data.message)
            }).catch(error => {
            console.log(error.response)
        })
    }

    const addRole = () => {
        RoleApi.addRoleToUser(searchUser, roleName)
            .then(response => {
                console.log(response)
                setServerMessage(response.data.message)
            }).catch(error => {
            console.log(error.response)
            setServerMessage(error.response.data.message)
        })
    }

    return (
        <div className={styles["mainDiv-style"]}>
            <div className="col-md-4">
                <div>
                    <h3>{"Show user roles"}</h3>
                </div>
                <span>Enter username to see his roles.</span>
                <div className="input-group mb-3">
                    <input
                        type="text"
                        className="form-control"
                        placeholder={"Enter user"}
                        value={searchUser}
                        onChange={updateUserSearch}
                    />
                    <div className="input-group-append">
                        <Button
                            variant={"primary"}
                            type="button"
                            onClick={getRoles}
                        >
                            Search
                        </Button>
                    </div>
                </div>
            </div>
            <div>
                <ul>
                    {userRoles.map((role, index) => {
                            return (
                                <li key={index}>{role.role}</li>
                            )
                        }
                    )}
                </ul>
            </div>


            <div className="col-md-8">
                <div>
                    <h3>{"Add user role"}</h3>
                </div>
                <div className="input-group mb-3">
                    <form>
                        <input
                            type="text"
                            className="form-control"
                            placeholder={"Enter username"}
                            value={searchUser}
                            onChange={updateUserSearch}
                        />
                        <label>
                            <select value={roleName} onChange={updateUserAddRole}>
                                <option value="REGULAR">REGULAR</option>
                                <option value="ADMIN">ADMIN</option>
                                <option value="ROOT_ADMIN">ROOT_ADMIN</option>
                            </select>

                        </label>

                        <div className="input-group-append">
                            <Button
                                variant={"primary"}
                                type="button"
                                onClick={addRole}
                            >
                                Add role
                            </Button>
                        </div>
                    </form>

                </div>

            </div>


            <div className="col-md-8">
                <div>
                    <h3>{"Delete user role"}</h3>
                </div>
                <div className="input-group mb-3">
                    <form>
                        <input
                            type="text"
                            className="form-control"
                            placeholder={"Enter username"}
                            value={searchUser}
                            onChange={updateUserSearch}
                        />
                        <label>
                            <select value={roleName} onChange={updateUserDeleteRole}>
                                <option value="REGULAR">REGULAR</option>
                                <option value="ADMIN">ADMIN</option>
                                <option value="ROOT_ADMIN">ROOT_ADMIN</option>
                            </select>

                        </label>

                        <div className="input-group-append">
                            <Button
                                variant={"primary"}
                                type="button"
                                onClick={deleteRole}
                            >
                                Delete role
                            </Button>
                        </div>
                    </form>

                </div>


            </div>

            {serverMessage && <h2>{serverMessage}</h2>}
        </div>

    )
}
export default RootAdminPanel;