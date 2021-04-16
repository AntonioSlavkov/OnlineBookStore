import React from "react";
import styled from "styled-components";

const HeaderText = styled.h1`
    
    text-align: center;
    border-style: solid;
`

const Container = styled.div`

    padding: 200px;
`
const Home = () => {

    return (
        <Container>

            <HeaderText>Welcome to Antonio's online book store</HeaderText>

        </Container>
    )
}
export default Home