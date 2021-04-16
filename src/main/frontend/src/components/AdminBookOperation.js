import React, {useEffect, useState} from "react";
import bookApi from "../utils/api/bookApi";
import {Button, Form, Table} from "react-bootstrap";
import {useFieldArray, useForm} from "react-hook-form";
import {ErrorMessage} from "@hookform/error-message";
import styles from "./AdminBookOperation.module.css"




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

            <h1 className={styles["header-two-style"]}>All books</h1>

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

            <h2 className={styles["header-two-style"]}>Add book</h2>

                <form className={styles["add-book-style"]} onSubmit={handleSubmit(onSubmit)}>

                    <div>
                        <label>Title</label>
                    </div>

                    <div>
                        <input className={styles["input-style"]} type="text" placeholder="Title"
                               {...register("title", {required: "Title field is required", maxLength: 255})} />
                        {getErrorMessage(errors, "title")}
                    </div>

                    <div>
                        <label>Number of pages</label>
                    </div>

                    <div>
                        <input className={styles["input-style"]} type="number" placeholder="Pages"
                               {...register("pages", {required: "Number of pages is required", maxLength: 3000})} />
                        {getErrorMessage(errors, "pages")}
                    </div>



                    <div>
                        <label>Language</label>
                    </div>

                    <div>
                        <input className={styles["input-style"]} type="text" placeholder="Language"
                               {...register("language", {required: "Language is required"})} />
                        {getErrorMessage(errors, "language")}
                    </div>



                    <div>
                        <label>Price</label>
                    </div>

                    <div>
                        <input className={styles["input-style"]} type="number" placeholder="Price"
                               {...register("price", {required: "Price is required", min: 0})} />
                        {getErrorMessage(errors, "price")}
                    </div>



                    <div>
                        <label>Main Category</label>
                    </div>

                    <div>
                        <input className={styles["input-style"]} type="text" placeholder="Main Category"
                               {...register("mainCategory", {required: "Main category is required"})} />
                        {getErrorMessage(errors, "mainCategory")}
                    </div>



                    <div>
                        <label>Author</label>
                    </div>

                    <div>
                        <input className={styles["input-style"]} type="text" placeholder="Author"
                               {...register("author", {required: "Author is required"})}/>
                        {getErrorMessage(errors, "author")}
                    </div>



                    <div>
                        <label>Description</label>
                    </div>

                    <div>
                         <textarea className={styles["input-style"]} {...register("description",
                             {required: "Book description is required"})} />
                        {getErrorMessage(errors, "description")}
                    </div>

                    <div className={styles["picture-array"]}>

                        <label>Picture Urls</label>
                    <ul>
                        {pictureUrlFields.map((item, index) => {
                            return (
                                <li key={item.id}>
                                    <input className={styles["input-style"]} name={`pictureUrls[${index}].imageUrl}`}
                                           {...register(`pictureUrls.${index}.imageUrl`,
                                               {required: "Picture url is required"})}
                                    />
                                    <Button variant={"primary"} type="button" onClick={() => pictureUrlRemove(index)}>
                                        Delete
                                    </Button>
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
                    </div>

                    <div>

                        <label className={styles["sub-category-padding"]}>Sub categories</label>

                    <ul>
                        {subCategoryFields.map((secondItem, index) => {
                            return (
                                <li key={secondItem.id}>
                                    <input className={styles["input-style"]} name={`subCategories[${index}].category}`}
                                           {...register(`subCategories.${index}.category`,
                                               {required: "Sub categories is required"})}
                                    />
                                    <Button variant={"primary"} type="button" onClick={() => subCategoryRemove(index)}>
                                        Delete
                                    </Button>
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

                    </div>

                    <input type="submit"/>
                </form>

            {serverAddBookMessage && <h2 className={styles["header-two-style"]}>{serverAddBookMessage}</h2>}


            <div className="col-md-8">
                <div>
                    <h3 className={styles["header-two-style"]}>{"Delete book by id"}</h3>
                </div>
                <div className="input-group mb-3">
                    <form className={styles["delete-book-form"]}>
                        <input
                            type="text"
                            className="form-control"
                            placeholder={"Enter book id"}
                            value={bookId}
                            onChange={updateBookId}
                        />

                        <div className="input-group-append">
                            <Button
                                variant={"primary"}
                                type="button"
                                onClick={deleteBookById}
                            >
                                Delete book
                            </Button>
                        </div>
                    </form>

                </div>
            </div>

            {serverDeleteBookMessage && <h2 className={styles["header-two-style"]}>{serverDeleteBookMessage}</h2>}




        </div>

    )
}

export default AdminBookOperation