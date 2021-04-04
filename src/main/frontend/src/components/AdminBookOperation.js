import React, {useEffect, useState} from "react";
import bookApi from "../utils/api/bookApi";
import {Form, Table} from "react-bootstrap";
import {useFieldArray, useForm} from "react-hook-form";
import {ErrorMessage} from "@hookform/error-message";


const AdminBookOperation = () => {

    const [book, setBook] = useState('')
    const [books, setBooks] = useState([])
    const [bookId, setBookId] = useState('')
    const {register, handleSubmit, errors, control, watch} = useForm(
        {
        defaultValues: {
            pictureUrls: [{link: "picture url"}],
            subCategories: [{subCategory: "sub category"}]
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
        console.log(data)
        bookApi.addBook(data.title, data.pages, data.language, data.description, data.price, data.pictureUrls,
            data.author, data.category, data.subCategories)
            .then(response => {
                console.log(response)
            }).catch(error => {
            console.log(error.response)
        })

    }

    // const addBook = (data) => {
    //
    // }

    const deleteBookById = () => {
        bookApi.deleteBook(bookId)
            .then(response => {
                console.log(response)
            }).catch(error => {
            console.log(error.response)
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
                    <input name="title" type="text" placeholder="Title"
                           {...register("Title", {required: true, maxLength: 255})} />
                    {getErrorMessage(errors, "title")}

                    <label>Number of pages</label>
                    <input name="pages" type="number" placeholder="Pages"
                           {...register("Pages", {required: true, maxLength: 3000})} />
                    {getErrorMessage(errors, "pages")}

                    <label>Language</label>
                    <input name="language" type="text" placeholder="Language"
                           {...register("Language", {required: true})} />
                    {getErrorMessage(errors, "language")}

                    <label>Price</label>
                    <input name="price" type="number" placeholder="Price"
                           {...register("Price", {required: true, min: 0})} />
                    {getErrorMessage(errors, "price")}

                    <label>Main Category</label>
                    <input name="mainCategory" type="text" placeholder="Main Category"
                           {...register("Main Category", {required: true})} />
                    {getErrorMessage(errors, "mainCategory")}

                    <label>Author</label>
                    <input name="author" type="text" placeholder="Author"
                           {...register("Author", {required: true})}/>
                    {getErrorMessage(errors, "author")}

                    <label>Description</label>
                    <textarea name="description" {...register("Description",
                        {required: true})} />
                    {getErrorMessage(errors, "description")}

                    <ul>
                        {pictureUrlFields.map((item, index) => {
                            return (
                                <li key={item.id}>
                                    <input name={`pictureUrls[${index}].link}`}
                                           {...register(`pictureUrls.${index}.link`)}
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

                    <ul>
                        {subCategoryFields.map((secondItem, index) => {
                            return (
                                <li key={secondItem.id}>
                                    <input name={`subCategories[${index}].subCategory}`}
                                           {...register(`subCategories.${index}.subCategory`)}
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

                    <input type="submit"/>
                </form>



        </div>

    )
}

export default AdminBookOperation