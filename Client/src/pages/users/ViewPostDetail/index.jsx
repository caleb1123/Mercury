import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import "./ViewPostDetail.css";
import Header from "../Header";
import Footer from "../Footer";

const ImageContainer = ({ images }) => {
  if (!images || images.length === 0) {
    return null;
  }

  return (
    <div className="image-container">
      <img
        src={images[0].postImageURL}
        className="main-image"
        alt="Main Background"
      />
      <div className="thumbnail-container">
        {images.slice(1).map((image, index) => (
          <img
            key={index}
            loading="lazy"
            src={image.postImageURL}
            className="thumbnail-image"
            alt={`Thumbnail image ${index + 1}`}
          />
        ))}
      </div>
    </div>
  );
};

const ArticleContent = ({ title, content, postDate }) => {
  return (
    <article className="article-content">
      <h1 className="article-title">{title}</h1>
      <div className="article-meta">
        <span className="meta-item">Posted on</span>
        <span className="meta-link">{new Date(postDate).toLocaleDateString()}</span>
      </div>
      <p className="article-paragraph">{content}</p>
    </article>
  );
};

const ElsaPerettiArticle = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [post, setPost] = useState(null);
  const [images, setImages] = useState([]);
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setIsLoggedIn(true);
    }
  }, []);

  useEffect(() => {
    const fetchPost = async () => {
      try {
        const response = await axios.get(`http://localhost:8088/posts/getById/${id}`);
        setPost(response.data);
      } catch (error) {
        console.error("Failed to fetch post:", error);
      }
    };

    const fetchImages = async () => {
      try {
        const response = await axios.get(`http://localhost:8088/postImage/list/${id}`);
        const formattedImages = response.data.map(image => ({
          ...image,
          postImageURL: image.postImageURL.replace("uc?id=", "uc?export=view&id=")
        }));
        setImages(formattedImages);
        console.log(formattedImages);
      } catch (error) {
        console.error("Failed to fetch images:", error);
      }
    };

    fetchPost();
    fetchImages();
  }, [id]);

  const handleProfileClick = () => {
    navigate("/viewProfile");
  };

  if (!post) {
    return <div>Loading...</div>;
  }

  return (
    <>
      <Header isLoggedIn={isLoggedIn} handleProfileClick={handleProfileClick} />
      <div className="article-container">
        <div className="content-wrapper">
          <main className="main-content">
            <ImageContainer images={images} />
            <ArticleContent title={post.title} content={post.content} postDate={post.postDate} />
          </main>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default ElsaPerettiArticle;
