import './App.css';
import Header from "./components/Header";
import React, { useState, useEffect } from "react";
import UserApi from "./utils/api/UserApi";

function App() {
    const [currentUser, setCurrentUser] = useState(undefined)

    useEffect( () => {

        const user = UserApi.getCurrentUser()

        if (user) {
            setCurrentUser(user)
        }


    }, [])

    const logOut = () => {
        UserApi.logout()
    }

  return (
    <div className="App">

      <Header/>
    </div>
  );
}

export default App;
