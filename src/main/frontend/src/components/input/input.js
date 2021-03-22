import React from "react";

const Input = ({label, id, value, onChange}) => {
    return (
        <div>
            <label htmlFor={id}>
                {label}:
            </label>
            <input value={value} id={id} onChange={onChange}/>
        </div>
    )
}
export default Input
