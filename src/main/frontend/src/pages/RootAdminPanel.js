import React, {useEffect, useState} from "react"
import RoleApi from "../utils/api/RoleApi";
import SearchUserRoles from "../components/SearchUserRoles";




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
    console.log(allRoles)

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
            console.log(response)
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
        }).catch(error => {
            console.log(error.response)
        })
    }

    const addRole = () => {
        RoleApi.addRoleToUser(searchUser, roleName)
            .then(response => {
            console.log(response)
        }).catch(error => {
            console.log(error.response)
        })
    }

    return (
        <div className="list row">
            <SearchUserRoles
                value={searchUser} onChange={updateUserSearch} onClick={getRoles} placeholder={"Enter user"}
                title={"Show user roles"}
            />
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
                    <h3>{"Add user roleName"}</h3>
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
                            <button
                                className="btn btn-outline-secondary"
                                type="button"
                                onClick={addRole}
                            >
                                Add role
                            </button>
                        </div>
                    </form>

                </div>
            </div>



            <div className="col-md-8">
                <div>
                    <h3>{"Delete user roleName"}</h3>
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
                            <button
                                className="btn btn-outline-secondary"
                                type="button"
                                onClick={deleteRole}
                            >
                                Add role
                            </button>
                        </div>
                    </form>

                </div>
            </div>






        </div>

    )
}
export default RootAdminPanel;