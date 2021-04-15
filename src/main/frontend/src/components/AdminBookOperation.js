import React, {useEffect, useState} from "react";
import bookApi from "../utils/api/bookApi";
import {Form, Table} from "react-bootstrap";
import {useFieldArray, useForm} from "react-hook-form";
import {ErrorMessage} from "@hookform/error-message";




const AdminBookOperation = () => {

    const [books, setBooks] = useState([])
    const [bookId, setBookId] = useState('')
    const {register, handleSubmit, formState: { errors }, control, watch} = useForm(
        {
        defaultValues: {
            pictureUrls: [{imageUrl: "picture url"}],
            subCategories: [{category: "sub category"}]
        }
    }

    );
    const {fields: pictureUrlFields, append: pictureUrlAppend, remove: pictureUrlRemove} = useFieldArray({
        control,
        name: "pictureUrls",
    })
    const {fields: subCategoryFields, append: subCategoryAppend, remove: subCategoryRemove} = useFieldArray({
        control,
        name: "subCategories",
    })

    const [serverAddBookMessage, setServerAddBookMessage] = useState('')
    const [serverDeleteBookMessage, setServerDeleteBookMessage] = useState('')

    const updateBookId = e => {
        setBookId(e.target.value)
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

    useEffect(() => {
        getAllBooks()
    }, [])

    const onSubmit = data => {
        // console.log(data)
        // console.log("title", data.title)
        // console.log("pages", data.pages)
        // console.log("language", data.language)
        // console.log("description", data.description)
        // console.log("price", data.price)
        // console.log("pictureUrls", data.pictureUrls)
        // console.log("author", data.author)
        // console.log("category", data.category)
        // console.log("subCategories", data.subCategories)
        bookApi.addBook(data.title, data.pages, data.language, data.description, data.price, data.pictureUrls,
            data.author, data.mainCategory, data.subCategories)
            .then(response => {
                console.log(response)
                setServerAddBookMessage(response.data.message)
            }).catch(error => {
            console.log(error.response)
            setServerAddBookMessage(error.response.data.message)
        })

    }

    const deleteBookById = () => {
        bookApi.deleteBook(bookId)
            .then(response => {
                console.log(response)
                setServerDeleteBookMessage(response.data.message)
            }).catch(error => {
            console.log(error.response)
            setServerDeleteBookMessage(error.response.data.message)
        })
    }

    const getAllBooks = () => {
        bookApi.getBooks()
            .then(response => {
                console.log(response)
                setBooks(response.data)
            }).catch(error => {
            console.log(error.response)
        })
    }

    return (
        <div>
            <div>
                <Table>
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>Title</th>
                        <th>Price</th>
                    </tr>
                    </thead>
                    {books.map((book, index) => {
                        return (
                            <tbody>
                            <tr key={index}>
                                <td>{book.id}</td>
                                <td>{book.title}</td>
                                <td>{book.price}</td>
                            </tr>
                            </tbody>
                        )
                    })}
                </Table>
            </div>

            <h2>Add book</h2>

                <form onSubmit={handleSubmit(onSubmit)}>

                    <label>Title</label>
                    <input type="text" placeholder="Title"
                           {...register("title", {required: "Title field is required", maxLength: 255})} />
                    {getErrorMessage(errors, "title")}

                    <label>Number of pages</label>
                    <input type="number" placeholder="Pages"
                           {...register("pages", {required: "Number of pages is required", maxLength: 3000})} />
                    {getErrorMessage(errors, "pages")}

                    <label>Language</label>
                    <input type="text" placeholder="Language"
                           {...register("language", {required: "Language is required"})} />
                    {getErrorMessage(errors, "language")}

                    <label>Price</label>
                    <input type="number" placeholder="Price"
                           {...register("price", {required: "Price is required", min: 0})} />
                    {getErrorMessage(errors, "price")}

                    <label>Main Category</label>
                    <input type="text" placeholder="Main Category"
                           {...register("mainCategory", {required: "Main category is required"})} />
                    {getErrorMessage(errors, "mainCategory")}

                    <label>Author</label>
                    <input type="text" placeholder="Author"
                           {...register("author", {required: "Author is required"})}/>
                    {getErrorMessage(errors, "author")}

                    <label>Description</label>
                    <textarea {...register("description",
                        {required: "Book description is required"})} />
                    {getErrorMessage(errors, "description")}

                    <ul>
                        {pictureUrlFields.map((item, index) => {
                            return (
                                <li key={item.id}>
                                    <input name={`pictureUrls[${index}].imageUrl}`}
                                           {...register(`pictureUrls.${index}.imageUrl`,
                                               {required: "Picture url is required"})}
                                    />
                                    <button type="button" onClick={() => pictureUrlRemove(index)}>
                                        Delete
                                    </button>
                                </li>
                            )
                        })}
                    </ul>
                    <button type="button" onClick={() => {
                        pictureUrlAppend({pictureUrls: ""})
                    }}>
                        Append link
                    </button>
                    {getErrorMessage(errors, "pictureUrls")}

                    <ul>
                        {subCategoryFields.map((secondItem, index) => {
                            return (
                                <li key={secondItem.id}>
                                    <input name={`subCategories[${index}].category}`}
                                           {...register(`subCategories.${index}.category`,
                                               {required: "Sub categories is required"})}
                                    />
                                    <button type="button" onClick={() => subCategoryRemove(index)}>
                                        Delete
                                    </button>
                                </li>
                            )
                        })}
                    </ul>
                    <button type="button" onClick={() => {
                        subCategoryAppend({subCategories: ""})
                    }}>
                        Append category
                    </button>

                    {getErrorMessage(errors, "subCategories")}

                    <input type="submit"/>
                </form>

            {serverAddBookMessage && <h2>{serverAddBookMessage}</h2>}


            <div className="col-md-8">
                <div>
                    <h3>{"Delete book by id"}</h3>
                </div>
                <div className="input-group mb-3">
                    <form>
                        <input
                            type="text"
                            className="form-control"
                            placeholder={"Enter book id"}
                            value={bookId}
                            onChange={updateBookId}
                        />

                        <div className="input-group-append">
                            <button
                                className="btn btn-outline-secondary"
                                type="button"
                                onClick={deleteBookById}
                            >
                                Delete book
                            </button>
                        </div>
                    </form>

                </div>
            </div>

            {serverDeleteBookMessage && <h2>{serverDeleteBookMessage}</h2>}




        </div>

    )
}

export default AdminBookOperation