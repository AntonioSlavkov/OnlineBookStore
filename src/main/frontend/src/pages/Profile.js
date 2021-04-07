import React, {useEffect, useState} from "react"
import UserApi from "../utils/api/UserApi";
import UserContactApi from "../utils/api/UserContactApi";
import Container from "react-bootstrap/Container";
import {useForm} from "react-hook-form";
import {ErrorMessage} from "@hookform/error-message";

const Profile = () => {
    const currentUser = UserApi.getCurrentUser()
    const [phoneNumber, setPhoneNumber] = useState()
    const [city, setCity] = useState()
    const [address, setAddress] = useState()
    const {register, handleSubmit, formState: { errors } } = useForm()
    console.log(currentUser.username)

    useEffect(() => {
        getUserContactInformation()
    }, [])

    const getUserContactInformation = () => {
        UserContactApi.getUserContactInformation(currentUser.username)
            .then(response => {
                setAddress(response.data.address)
                setCity(response.data.city)
                setPhoneNumber(response.data.phoneNumber)
                console.log(response.data)
            }).catch(error => {
            console.log(error.response)
        })
    }

    const updateUserContactInformation = (data) => {
        console.log(data)
        UserContactApi.updateUserContactInformation(data.phoneNumber, data.city, data.address, currentUser.username)
            .then(response => {
                console.log(response)
            }).catch(error => {
            console.log(error)
        })
    }

    const getErrorMessage = (errors, inputName) => {
        return <ErrorMessage errors={errors} name={inputName}>
            {
                ({messages}) => messages && Object.entries(messages).map(([type, message]) =>
                    (<p key={type}>{message}</p>)
                )
            }
        </ErrorMessage>;
    }

    return (
        <div className="container">
            <h3>
                <strong>{currentUser.username}</strong> Profile
            </h3>

            <p>
                <strong>Token:</strong> {currentUser.token.substring(0, 20)} ...{" "}
                {currentUser.token.substr(currentUser.token.length - 20)}
            </p>
            <Container className={"container"}>
                <div className={"col-md-3"}>
                    <div className={"card mb-4 box-shadow"}>
                        <strong>{address}</strong>
                        <strong>{phoneNumber}</strong>
                        <strong>{city}</strong>
                    </div>
                </div>
            </Container>

            <form onSubmit={handleSubmit(updateUserContactInformation)}>
                <label>Phone number</label>
                <input type="text" placeholder="Phone number"
                       {...register("phoneNumber", {required: "This field is required"})}
                />
                {getErrorMessage(errors, "phoneNumber")}

                <label>City</label>
                <input type="text" placeholder="City"
                       {...register("city", {required: "This field is required"})}
                />
                {getErrorMessage(errors, "city")}

                <label>Address</label>
                <input type="text" placeholder="Address"
                       {...register("address",{required: "This field is required"})}
                />
                {getErrorMessage(errors, "address")}

                <input type="submit"/>
            </form>

        </div>
    )
}
export default Profile;