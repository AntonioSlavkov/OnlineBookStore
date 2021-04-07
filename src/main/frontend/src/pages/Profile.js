import React from "react"
import UserApi from "../utils/api/UserApi";

const Profile = () => {
    const currentUser = UserApi.getCurrentUser()

    return (
        <div className="container">
            <h3>
                <strong>{currentUser.username}</strong> Profile
            </h3>

            <p>
                <strong>Token:</strong> {currentUser.token.substring(0, 20)} ...{" "}
                {currentUser.token.substr(currentUser.token.length - 20)}
            </p>

        </div>
    )
}
export default Profile;