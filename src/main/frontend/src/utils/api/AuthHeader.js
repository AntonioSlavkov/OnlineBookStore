const AuthHeader = () => {
    const user = JSON.parse(localStorage.getItem('username'))

    if (user && user.token) {
        return {
            Authorization: 'Bearer ' + user.token
        };
    } else {
        return {};
    }
};
export default AuthHeader;