import React, {useEffect, useState} from "react"
import UserApi from "../utils/api/UserApi";
import UserContactApi from "../utils/api/UserContactApi";
import {useForm} from "react-hook-form";
import {ErrorMessage} from "@hookform/error-message";
import styled from "styled-components";


const Profile = () => {
    const currentUser = UserApi.getCurrentUser()
    const [phoneNumber, setPhoneNumber] = useState()
    const [city, setCity] = useState()
    const [address, setAddress] = useState()
    const {register, handleSubmit, formState: { errors } } = useForm()
    const [serverMessage, setServerMessage] = useState('')

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
                setServerMessage(response.data.message)
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

    const BookServerMessageHeaderTwoForProfile = styled.h2`
        padding-top: 10px;
        text-align: center;
        color: green;
    `

    const HeaderOneForProfile = styled.h1`
        padding: 10px;
    `

    const ContactDetailsDivForProfile = styled.div`
        border: 3px solid black;
        padding: 10px;
        flex-wrap: wrap;
        width: 300px;
    
    `

    const ContactDetailsDivWrapperForProfile = styled.div`
          padding: 20px;
    `

    const StyledDivForFormForProfile = styled.div`
         padding: 20px;  
    `

    const StyledBorderForFormForProfile = styled.div`
        border: 3px solid black;
        width: 250px;
        padding: 10px;
    `

    const StyledDivForButtonForProfile = styled.div`
        padding-top: 10px;
    `




    return (
        <div className="container">
            <HeaderOneForProfile>
                <strong>{currentUser.username}</strong>'s Profile
            </HeaderOneForProfile>

            <ContactDetailsDivWrapperForProfile className={"container"}>


                <ContactDetailsDivForProfile>
                    <h5>City: {city}</h5>
                    <h5>Address: {address}</h5>
                    <h5>Phone Number: {phoneNumber}</h5>
                </ContactDetailsDivForProfile>


                {/*<div className={"col-md-3"}>*/}
                {/*    <div className={"card mb-4 box-shadow"}>*/}
                {/*        <strong>City: {city}</strong>*/}
                {/*        <strong>Address: {address}</strong>*/}
                {/*        <strong>Phone Number: {phoneNumber}</strong>*/}
                {/*    </div>*/}
                {/*</div>*/}
            </ContactDetailsDivWrapperForProfile>

            <StyledDivForFormForProfile>


                <StyledBorderForFormForProfile>
                <form onSubmit={handleSubmit(updateUserContactInformation)}>

                    <div>

                        <div>

                            <label className="fs-4">Phone number:</label>

                        </div>


                        <input type="text" placeholder="Phone number"
                               {...register("phoneNumber", {required: "This field is required"})}
                        />
                        {getErrorMessage(errors, "phoneNumber")}

                    </div>

                    <div>

                        <div>

                            <label className="fs-4">City:</label>

                        </div>

                        <input type="text" placeholder="City"
                               {...register("city", {required: "This field is required"})}
                        />
                        {getErrorMessage(errors, "city")}

                    </div>

                    <div>

                        <div>

                            <label className="fs-4">Address:</label>

                        </div>

                        <input type="text" placeholder="Address"
                               {...register("address",{required: "This field is required"})}
                        />
                        {getErrorMessage(errors, "address")}

                    </div>


                    <StyledDivForButtonForProfile>

                        <input type="submit"/>

                    </StyledDivForButtonForProfile>

                </form>

                </StyledBorderForFormForProfile>

            </StyledDivForFormForProfile>

            {serverMessage && <BookServerMessageHeaderTwoForProfile>{serverMessage}</BookServerMessageHeaderTwoForProfile>}

        </div>
    )
}
export default Profile;