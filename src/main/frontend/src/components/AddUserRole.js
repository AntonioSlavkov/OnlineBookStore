import React from "react";

function SearchUserRoles(props) {
    return <div className="col-md-8">
        <div>
            <h3>{props.title}</h3>
        </div>
        <div className="input-group mb-3">
            <form>
                <input
                    type="text"
                    className="form-control"
                    placeholder={props.placeholder}
                    value={props.value}
                    onChange={props.onChange}
                />
                <input/>
                <div className="input-group-append">
                    <button
                        className="btn btn-outline-secondary"
                        type="button"
                        onClick={props.onClick}
                    >
                        Search
                    </button>
                </div>
            </form>

        </div>
    </div>;
}
export default SearchUserRoles