import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import "./ViewPostDetail.css";

function ViewPostDetail() {
    const [post, setPost] = useState(null);
    const { id } = useParams();

    useEffect(() => {
        axios.get("http://localhost:8088/posts/getById/${id}")
            .then(response => {
                setPost(response.data);
            })
            .catch(error => {
                console.error('Error fetching post:', error);
            });
    }, [id]);

    if (!post) {
        return <div>Loading...</div>;
    }

    return (
        <div className="post-detail-container">
            <h1>{post.title}</h1>
            <p>{post.content}</p>
            <small>Posted on: {post.postDate.toString().slice(0, 10)}</small>
            <div>Status: {post.status ? "Active" : "Inactive"}</div>
            <div>Category: {post.postCategory.categoryName}</div>
            <div>Author: {post.account.name}</div>
        </div>
    );
}

export default ViewPostDetail;