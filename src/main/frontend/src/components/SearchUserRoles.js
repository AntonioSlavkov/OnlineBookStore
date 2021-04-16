import React from "react";
import {Button} from "react-bootstrap";

function SearchUserRoles(props) {
    return <div className="col-md-4">
        <div>
            <h3>{props.title}</h3>
        </div>
        <div className="input-group mb-3">
            <input
                type="text"
                className="form-control"
                placeholder={props.placeholder}
                value={props.value}
                onChange={props.onChange}
            />
            <div className="input-group-append">
                <Button
                    variant={"primary"}
                    type="button"
                    onClick={props.onClick}
                >
                    Search
                </Button>
            </div>
        </div>
    </div>;
}
export default SearchUserRoles